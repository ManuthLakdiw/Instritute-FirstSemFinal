package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeacherModel {
    SubjectModel subjectModel = new SubjectModel();


    public String getNextTeacherID() {
        try {
            ResultSet resultSet = CrudUtil.execute("select t_id from teacher order by t_id desc limit 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                String substring = lastId.substring(1);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("T%03d", newId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "T001";
    }


    public ArrayList<TeacherDto> getAllTeachers() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT t.t_id, t.name, t.phone_number, t.email, s.sub_name AS subject, GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grades FROM teacher AS t LEFT JOIN teacher_subject AS ts ON t.t_id = ts.teacher_id LEFT JOIN subject AS s ON ts.subject_id = s.sub_id LEFT JOIN teacher_grade AS tg ON t.t_id = tg.teacher_id LEFT JOIN grade AS g ON tg.grade_id = g.g_id GROUP BY t.t_id, t.name, t.phone_number, t.email, s.sub_name ORDER BY t.t_id, s.sub_name");

            ArrayList<TeacherDto> teachers = new ArrayList<>();
            while (resultSet.next()) {
                String[] subjectArray = resultSet.getString("subject") != null
                        ? resultSet.getString("subject").split(",")
                        : new String[0]; // Fixed the column name to "subject"

                String[] gradeArray = resultSet.getString("grades") != null
                        ? resultSet.getString("grades").split(",")
                        : new String[0];

                TeacherDto teacherDto = new TeacherDto(
                        resultSet.getString("t_id"),
                        resultSet.getString("name"), // Removed title
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        subjectArray,
                        gradeArray
                );
                teachers.add(teacherDto);
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> getSubjectIdsFromName(List<String> subjects) {
        List<String> subjectIds = new ArrayList<>();

        try {
            // Prepare a dynamic query with placeholders for each subject name
            String query = "SELECT sub_id FROM subject WHERE sub_name IN (" +
                    String.join(",", Collections.nCopies(subjects.size(), "?")) + ")";

            // Execute the query with the provided subject names
            ResultSet resultSet = CrudUtil.execute(query, subjects.toArray());

            // Extract the subject IDs from the result set
            while (resultSet.next()) {
                subjectIds.add(resultSet.getString("sub_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectIds;
    }


//    public boolean saveTeacher(TeacherDto teacherDto) {
//        try {
//            return CrudUtil.execute("insert into teacher values(?,?,?,?,?)",
//                    teacherDto.getTeacherId(),
//                    teacherDto.getTitle(),
//                    teacherDto.getName(),
//                    teacherDto.getPhoneNumber(),
//                    teacherDto.getEmail()
//            );
//
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    public TeacherDto getTeacherByID(String id) {
//        try {
//            ResultSet resultSet = CrudUtil.execute("select * from teacher where t_id=?", id);
//            if (resultSet.next()) {
//                TeacherDto teacherDto = new TeacherDto(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5)
//                );
//                return teacherDto;
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public boolean updateTeacher(TeacherDto teacherDto) {
//        try {
//            return CrudUtil.execute("update teacher set title=?, name=?, phone_number=?, email=? where t_id=?",
//                    teacherDto.getTitle(),
//                    teacherDto.getName(),
//                    teacherDto.getPhoneNumber(),
//                    teacherDto.getEmail(),
//                    teacherDto.getTeacherId()
//
//            );
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }


    public boolean deleteTeacher(String id) {
        try {
            return CrudUtil.execute("delete from teacher where t_id = ?", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public List<SubjectDto> getAllSubjectsWithGrades() {
//        List<SubjectDto> subjects = new ArrayList<>();
//        try {
//            ResultSet resultSet = CrudUtil.execute(
//                    "SELECT s.sub_id, s.sub_name, g.grade FROM subject s " +
//                            "JOIN subject_grade sg ON s.sub_id = sg.subject_id " +
//                            "JOIN grade g ON sg.grade_id = g.g_id;"
//            );
//
//            // Map to store subjects and their associated grades
//            Map<String, List<String>> subjectGradesMap = new    HashMap<>();
//
//            while (resultSet.next()) {
//                String subjectId = resultSet.getString("sub_id");
//                String subjectName = resultSet.getString("sub_name");
//                String grade = resultSet.getString("grade");
//
//                while (resultSet.next()) {
//                    // Here we are assuming that the grade is a single value per subject, but if multiple grades exist, you may need to handle it differently
//                    SubjectDto subject = new SubjectDto(
//                            resultSet.getString("sub_id"),
//                            resultSet.getString("sub_name"),
//                            new String[]{resultSet.getString("grade")}  // Convert the grade to an array of String
//                    );
//                    subjects.add(subject);
//                }
//            }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return subjects;
//    }



    public boolean saveTeacher(TeacherDto teacherDto, List<String> subjectNames, List<String> gradeNames) {
        Connection connection = null;
        try {
            // Get database connection and start transaction
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // 1. Insert teacher info into teacher table
            String teacherSQL = "INSERT INTO teacher (t_id, name, phone_number, email) VALUES (?, ?, ?, ?)";
            try {
                CrudUtil.execute(teacherSQL, teacherDto.getTeacherId(), teacherDto.getName(), teacherDto.getPhoneNumber(), teacherDto.getEmail());
            } catch (SQLException e) {
                System.out.println("Error executing teacher insert: " + e.getMessage());
                e.printStackTrace();
                return false; // Fail fast if the teacher insert fails
            }

            // 2. Process each subject and insert corresponding grades
            for (String subjectName : subjectNames) {
                // 2.1 Get grade IDs associated with the current subject
                List<String> gradeIds = subjectModel.getGradeIdsForSubject(subjectName, gradeNames);

                // 2.2 Insert the subject for the teacher into teacher_subject table
                String subjectId = String.valueOf(subjectModel.getSubjectIdFromName(subjectName)); // Get the subject ID for the name
                String teacherSubjectSQL = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
                try {
                    CrudUtil.execute(teacherSubjectSQL, teacherDto.getTeacherId(), subjectId);
                } catch (SQLException e) {
                    System.out.println("Error inserting teacher-subject relation: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }

                // 2.3 Insert each grade for the teacher into teacher_grade table
                String teacherGradeSQL = "INSERT INTO teacher_grade (teacher_id, grade_id) VALUES (?, ?)";
                for (String gradeId : gradeIds) {
                    try {
                        CrudUtil.execute(teacherGradeSQL, teacherDto.getTeacherId(), gradeId);
                    } catch (SQLException e) {
                        System.out.println("Error inserting teacher-grade relation: " + e.getMessage());
                        e.printStackTrace();
                        return false;
                    }
                }
            }

            // Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            // If error occurs, rollback the transaction
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback in case of an error
                    System.out.println("Transaction rolled back due to an error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Ensure auto-commit behavior is restored
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Restore auto-commit behavior
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false; // Return false if there is any failure
    }





}
