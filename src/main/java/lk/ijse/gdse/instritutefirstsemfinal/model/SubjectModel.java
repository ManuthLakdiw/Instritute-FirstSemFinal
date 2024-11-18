package lk.ijse.gdse.instritutefirstsemfinal.model;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SubjectModel {

    public String getNextSubjectID() {
        try {
            ResultSet resultSet = CrudUtil.execute("select sub_id from subject order by sub_id desc limit 1");
            if (resultSet.next()) {
                String lastID = resultSet.getString(1);
                String substring = lastID.substring(3);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("SUB%03d", newId);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "SUB001";
    }


    public ArrayList<SubjectDto> getAllSubjects() {
        ArrayList<SubjectDto> subjects = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT s.sub_id, s.sub_name, GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grade_names, s.description FROM subject AS s LEFT JOIN subject_grade AS sg ON s.sub_id = sg.subject_id LEFT JOIN grade AS g ON sg.grade_id = g.g_id GROUP BY s.sub_id, s.sub_name, s.description ORDER BY s.sub_id");
            while (resultSet.next()) {
                String[] gradesArray = resultSet.getString(3) != null
                        ? resultSet.getString(3).split(",")
                        : new String[0];

                SubjectDto subjectDto = new SubjectDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        gradesArray,
                        resultSet.getString(4)
                );
                subjects.add(subjectDto);
            }
            return subjects;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveSubjectWithGrades(SubjectDto subjectDto, List<String> gradeIds) {
        Connection connection = null;
        try {
            // Step 1: Establish a database connection
            connection = DBConnection.getInstance().getConnection();

            // Step 2: Start transaction
            connection.setAutoCommit(false);

            // Step 3: Insert subject into the `subject` table
            boolean isSubjectSaved = CrudUtil.execute(
                    "INSERT INTO subject (sub_id, sub_name, description) VALUES (?, ?, ?)",
                    subjectDto.getSubjectId(),
                    subjectDto.getSubjectName(),
                    subjectDto.getSubjectDescription()
            );

            if (!isSubjectSaved) {
                connection.rollback();
                System.out.println("Subject insertion failed");
                return false;  // Return early if subject insert fails
            }

            // Step 4: Validate grade relationships before inserting into the `subject_grade` table
            for (String gradeId : gradeIds) {
                // Check if the grade_id exists in the `grade` table
                boolean isGradeValid = isGradeExists(gradeId);
                if (!isGradeValid) {
                    System.out.println("Invalid grade ID: " + gradeId);
                    connection.rollback();  // Rollback transaction if grade does not exist
                    return false;  // Return early if invalid grade_id is found
                }

                // If the grade exists, insert the grade relation into the `subject_grade` table
                boolean isGradeRelationSaved = CrudUtil.execute(
                        "INSERT INTO subject_grade (subject_id, grade_id) VALUES (?, ?)",
                        subjectDto.getSubjectId(),
                        gradeId
                );

                if (!isGradeRelationSaved) {
                    connection.rollback();
                    System.out.println("Failed to save grade relation for grade ID: " + gradeId);
                    return false;  // Return early if grade relation insert fails
                }
            }

            // Step 5: Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();  // Rollback failure
            }
            return false;
        } finally {
            // Step 6: Restore auto commit and close connection
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to check if the grade exists in the `grade` table
    private boolean isGradeExists(String gradeId) {
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT COUNT(*) FROM grade WHERE g_id = ?",
                    gradeId
            );

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;  // Return true if the grade exists, otherwise false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getGradeIdsFromNames(List<String> gradeNames) {
        List<String> gradeIds = new ArrayList<>();

        try {
            // Prepare a dynamic query with placeholders for each grade name
            String query = "SELECT g_id FROM grade WHERE grade IN (" +
                    String.join(",", Collections.nCopies(gradeNames.size(), "?")) + ")";

            // Execute the query with the provided grade names
            ResultSet resultSet = CrudUtil.execute(query, gradeNames.toArray());

            // Extract the grade IDs from the result set
            while (resultSet.next()) {
                gradeIds.add(resultSet.getString("g_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gradeIds;
    }






    public boolean updateSubjectWithGrades(SubjectDto subjectDto, List<String> gradeIds) {
        Connection connection = null;
        try {
            // Step 1: Establish a database connection
            connection = DBConnection.getInstance().getConnection();

            // Step 2: Start transaction
            connection.setAutoCommit(false);

            // Step 3: Update the subject in the `subject` table
            boolean isSubjectUpdated = CrudUtil.execute(
                    "UPDATE subject SET sub_name = ?, description = ? WHERE sub_id = ?",
                    subjectDto.getSubjectName(),
                    subjectDto.getSubjectDescription(),
                    subjectDto.getSubjectId()
            );
            System.out.println(isSubjectUpdated);
            if (!isSubjectUpdated) {
                connection.rollback();
                return false;
            }

            // Step 4: Delete existing grade relationships in `subject_grade`
            boolean isGradesDeleted = CrudUtil.execute(
                    "DELETE FROM subject_grade WHERE subject_id = ?",
                    subjectDto.getSubjectId()
            );
            System.out.println(isGradesDeleted);
            if (!isGradesDeleted) {
                connection.rollback();
                return false;
            }

            // Step 5: Insert new grade relationships into `subject_grade`
            for (String gradeId : gradeIds) {
                boolean isGradeRelationSaved = CrudUtil.execute(
                        "INSERT INTO subject_grade (subject_id, grade_id) VALUES (?, ?)",
                        subjectDto.getSubjectId(),
                        gradeId
                );
                System.out.println(isGradeRelationSaved );
                if (!isGradeRelationSaved) {
                    connection.rollback();
                    return false;
                }
            }

            // Step 6: Commit the transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }




    public boolean deleteSubject(String subjectId) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Step 1: Delete from subject_grade table based on subject_id
            boolean isGradesDeleted = CrudUtil.execute(
                    "DELETE FROM subject_grade WHERE subject_id = ?",
                    subjectId
            );

            if (!isGradesDeleted) {
                connection.rollback();
                return false;
            }

            // Step 2: Delete from subject table based on subject_id
            boolean isSubjectDeleted = CrudUtil.execute(
                    "DELETE FROM subject WHERE sub_id = ?",
                    subjectId
            );

            if (!isSubjectDeleted) {
                connection.rollback();
                return false;
            }

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public SubjectDto searchExitingSubjectBySubjectID(String subjectId) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT s.sub_id, s.sub_name, GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grade_names, s.description FROM subject AS s LEFT JOIN subject_grade AS sg ON s.sub_id = sg.subject_id LEFT JOIN grade AS g ON sg.grade_id = g.g_id WHERE s.sub_id = ? GROUP BY s.sub_id, s.sub_name, s.description ORDER BY s.sub_id;", subjectId);

            // Only process the first row of ResultSet (there should only be one row for a given subjectId)
            if (resultSet.next()) {
                String[] gradesArray = resultSet.getString(3) != null ? resultSet.getString(3).split(",") : new String[0];

                // Create the SubjectDto object with data retrieved from the result set
                SubjectDto subjectDto = new SubjectDto(
                        resultSet.getString(1), // subjectId
                        resultSet.getString(2), // subjectName
                        gradesArray,            // gradesArray
                        resultSet.getString(4)  // subjectDescription
                );
                return subjectDto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean checkExitingSubject(String subjectName) {
        try {
            ResultSet resultSet = CrudUtil.execute("select sub_name from subject where sub_name = ?", subjectName);
            if (resultSet.next()) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getSubjectIdsFromNames(List<String> subjectNames) {
        List<String> subjectIds = new ArrayList<>();

        try {
            // Convert List<String> to String[] to ensure proper parameter passing
            String[] subjectNamesArray = subjectNames.toArray(new String[0]);

            // Prepare a dynamic query with placeholders for each subject name
            String query = "SELECT sub_id FROM subject WHERE sub_name IN (" +
                    String.join(",", Collections.nCopies(subjectNames.size(), "?")) + ")";

            // Execute the query with the provided subject names
            ResultSet resultSet = CrudUtil.execute(query, (Object[]) subjectNamesArray);  // Cast to Object[] to pass to execute

            // Extract the subject IDs from the result set
            while (resultSet.next()) {
                subjectIds.add(resultSet.getString("sub_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any potential SQL exceptions
        }

        return subjectIds;
    }


    public List<String> getGradeIdsForSubject(String subjectName, List<String> gradeNames) {
        List<String> gradeIds = new ArrayList<>();
        // Get all subjects with their grades
        ArrayList<SubjectDto> subjects = getAllSubjects(); // Assume this method fetches all subjects

        // Loop through the subjects to find the one matching the subjectName
        for (SubjectDto subject : subjects) {
            if (subject.getSubjectName().equals(subjectName)) {
                // Match grades for the subject and add their IDs
                for (String grade : subject.getSubjectGrades()) {
                    if (gradeNames.contains(grade)) {
                        List<String> gradeId = getGradeIdsFromNames(Collections.singletonList(grade)); // Assuming this returns a list of IDs
                        if (!gradeId.isEmpty()) {
                            gradeIds.add(gradeId.get(0)); // Add the first ID from the returned list
                        }
                    }
                }
                break; // Exit loop once the subject is found
            }
        }
        return gradeIds;
    }


    public String getSubjectIdFromName(String subjectName) {
        String subjectId = null;

        try {
            // Prepare a query to retrieve the subject ID for a single subject name
            String query = "SELECT sub_id FROM subject WHERE sub_name = ?";

            // Execute the query with the provided subject name
            ResultSet resultSet = CrudUtil.execute(query, subjectName);

            // Extract the subject ID from the result set
            if (resultSet.next()) {
                subjectId = resultSet.getString("sub_id");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any potential SQL exceptions
        }

        return subjectId;
    }

}
