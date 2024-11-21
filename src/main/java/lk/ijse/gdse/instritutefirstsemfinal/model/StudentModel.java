package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    public String getNextStudentID() {
        try {
            ResultSet resultSet = CrudUtil.execute("select s_id from student order by s_id desc limit 1");
            if (resultSet.next()) {
                String lastID = resultSet.getString(1);
                String substring = lastID.substring(1);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("S%04d", newId);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "S0001";
    }
    public ArrayList<StudentDto> getAllStudents() {
        ArrayList<StudentDto> students = new ArrayList<>();
        try {
            // Execute updated query
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT s.s_id AS 'Student ID', " +
                            "s.name AS 'Student Name', " +
                            "s.birthday AS 'Date Of Birth', " +
                            "s.admission_fee AS 'Admission Fee', " +
                            "s.parent_name AS 'Parent Name', " +
                            "s.email AS 'Email', " +
                            "s.phone_number AS 'Phone Number', " +
                            "s.address AS 'Address', " +
                            "g.grade AS 'Grade', " +
                            "GROUP_CONCAT(sb.sub_name SEPARATOR ', ') AS 'Subjects', " +
                            "s.added_by AS 'Added By' " +
                            "FROM student s " +
                            "LEFT JOIN grade g ON g.g_id = s.grade " +
                            "LEFT JOIN student_subject ss ON ss.student_id = s.s_id " +
                            "LEFT JOIN subject sb ON sb.sub_id = ss.subject_id " +
                            "GROUP BY s.s_id;"
            );

            while (resultSet.next()) {
                // Map each row to a StudentDto object
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                LocalDate birthday = resultSet.getDate(3) != null
                        ? resultSet.getDate("Date Of Birth").toLocalDate()
                        : null;
                double admissionFee = resultSet.getDouble(4);
                String parentName = resultSet.getString(5);
                String email = resultSet.getString(6);
                String phoneNumber = resultSet.getString(7);
                String address = resultSet.getString(8);
                String grade = resultSet.getString(9);
                String addedBy = resultSet.getString(11);

                // Split the subjects string into an array
                String subjectsString = resultSet.getString(10);
                String[] subjects = subjectsString != null ? subjectsString.split(", ") : new String[0];

                // Create a StudentDto object
                StudentDto studentDto = new StudentDto(
                        id,
                        birthday,
                        name,
                        admissionFee,
                        parentName,
                        email,
                        phoneNumber,
                        address,
                        addedBy,
                        grade,
                        subjects
                );

                students.add(studentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }



    public boolean saveStudent(StudentDto studentDto, List<String> subjectIds) {
        System.out.println(studentDto.getId());
        System.out.println(studentDto.getName());
        System.out.println(studentDto.getBirthday());
        System.out.println(studentDto.getAdmissionFee());
        System.out.println(studentDto.getParentName());
        System.out.println(studentDto.getEmail());
        System.out.println(studentDto.getPhoneNumber());
        System.out.println(studentDto.getAddress());
        System.out.println(studentDto.getGrade());
        System.out.println(studentDto.getAddedBy());
        System.out.println(studentDto.getSubjects());

        Connection connection = null;

        try {
            // Start a database transaction
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Insert student details into the student table
            String studentInsertQuery = "INSERT INTO student (s_id, birthday, name, admission_fee, parent_name, email, phone_number, address, added_by, grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            boolean isStudentSaved = CrudUtil.execute(
                    studentInsertQuery,
                    studentDto.getId(),
                    studentDto.getBirthday(),
                    studentDto.getName(),
                    studentDto.getAdmissionFee(),
                    studentDto.getParentName(),
                    studentDto.getEmail(),
                    studentDto.getPhoneNumber(),
                    studentDto.getAddress(),
                    studentDto.getAddedBy(),
                    studentDto.getGrade()

            );

            if (!isStudentSaved) {
                connection.rollback();
                return false;
            }

            // Insert subject details into the student_subject table
            String studentSubjectInsertQuery = "INSERT INTO student_subject (student_id, subject_id) VALUES (?, ?)";

            for (String subjectId : subjectIds) {
                boolean isSubjectLinked = CrudUtil.execute(studentSubjectInsertQuery, studentDto.getId(), subjectId);

                if (!isSubjectLinked) {
                    connection.rollback();
                    return false;
                }
            }

            // Commit the transaction if everything succeeds
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Restore default auto-commit behavior
                }
            } catch (SQLException finalEx) {
                finalEx.printStackTrace();
            }
        }
    }

    public ArrayList<StudentDto> getStudentsById(String studentId) {
        ArrayList<StudentDto> students = new ArrayList<>();
        try {
            // Execute the query to get students by student ID
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT s.s_id AS 'Student ID', " +
                            "s.name AS 'Student Name', " +
                            "s.birthday AS 'Date Of Birth', " +
                            "s.admission_fee AS 'Admission Fee', " +
                            "s.parent_name AS 'Parent Name', " +
                            "s.email AS 'Email', " +
                            "s.phone_number AS 'Phone Number', " +
                            "s.address AS 'Address', " +
                            "g.grade AS 'Grade', " +
                            "GROUP_CONCAT(sb.sub_name SEPARATOR ', ') AS 'Subjects', " +
                            "s.added_by AS 'Added By' " +
                            "FROM student s " +
                            "LEFT JOIN grade g ON g.g_id = s.grade " +
                            "LEFT JOIN student_subject ss ON ss.student_id = s.s_id " +
                            "LEFT JOIN subject sb ON sb.sub_id = ss.subject_id " +
                            "WHERE s.s_id = ? " +  // Add filter condition by student ID
                            "GROUP BY s.s_id;",studentId
            );

            // Process the result set
            while (resultSet.next()) {
                // Map each row to a StudentDto object
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                LocalDate birthday = resultSet.getDate(3) != null
                        ? resultSet.getDate("Date Of Birth").toLocalDate()
                        : null;
                double admissionFee = resultSet.getDouble(4);
                String parentName = resultSet.getString(5);
                String email = resultSet.getString(6);
                String phoneNumber = resultSet.getString(7);
                String address = resultSet.getString(8);
                String grade = resultSet.getString(9);
                String addedBy = resultSet.getString(11);

                // Split the subjects string into an array
                String subjectsString = resultSet.getString(10);
                String[] subjects = subjectsString != null ? subjectsString.split(", ") : new String[0];

                // Create a StudentDto object
                StudentDto studentDto = new StudentDto(
                        id,
                        birthday,
                        name,
                        admissionFee,
                        parentName,
                        email,
                        phoneNumber,
                        address,
                        addedBy,
                        grade,
                        subjects
                );

                // Add the student to the list
                students.add(studentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    public boolean updateStudent(StudentDto studentDto, List<String> subjectIds) {

        Connection connection = null;

        try {
            // Start a database transaction
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Update student details in the student table
            String studentUpdateQuery = "UPDATE student SET birthday = ?, name = ?, admission_fee = ?, parent_name = ?, email = ?, phone_number = ?, address = ?, added_by = ?, grade = ? WHERE s_id = ?";
            boolean isStudentUpdated = CrudUtil.execute(
                    studentUpdateQuery,
                    studentDto.getBirthday(),
                    studentDto.getName(),
                    studentDto.getAdmissionFee(),
                    studentDto.getParentName(),
                    studentDto.getEmail(),
                    studentDto.getPhoneNumber(),
                    studentDto.getAddress(),
                    studentDto.getAddedBy(),
                    studentDto.getGrade(),
                    studentDto.getId()
            );

            if (!isStudentUpdated) {
                connection.rollback();
                return false;
            }

            // Remove existing subjects from student_subject table
            String deleteSubjectQuery = "DELETE FROM student_subject WHERE student_id = ?";
            boolean isSubjectsDeleted = CrudUtil.execute(deleteSubjectQuery, studentDto.getId());

            if (!isSubjectsDeleted) {
                connection.rollback();
                return false;
            }

            // Insert updated subjects into the student_subject table
            String studentSubjectInsertQuery = "INSERT INTO student_subject (student_id, subject_id) VALUES (?, ?)";
            for (String subjectId : subjectIds) {
                boolean isSubjectLinked = CrudUtil.execute(studentSubjectInsertQuery, studentDto.getId(), subjectId);
                if (!isSubjectLinked) {
                    connection.rollback();
                    return false;
                }
            }

            // Commit the transaction if everything succeeds
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Restore default auto-commit behavior
                }
            } catch (SQLException finalEx) {
                finalEx.printStackTrace();
            }
        }
    }

    public boolean deleteStudent(String studentId) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                System.out.println("Failed to establish database connection.");
                return false;
            }
            connection.setAutoCommit(false);

            // Step 1: Delete from student_subject table based on student_id
            boolean isGradesDeleted = CrudUtil.execute("DELETE FROM student_subject WHERE student_id = ?", studentId);
            System.out.println("Grades delete status: " + isGradesDeleted);

            if (!isGradesDeleted) {
                connection.rollback();
                return false;
            }

            // Step 2: Delete the student record from student table
            boolean isStudentDeleted = CrudUtil.execute("DELETE FROM student WHERE s_id = ?", studentId);
            System.out.println("Student delete status: " + isStudentDeleted);

            if (!isStudentDeleted) {
                connection.rollback();
                return false;
            }

            // Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting student: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);  // Reset auto-commit mode
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }




}
