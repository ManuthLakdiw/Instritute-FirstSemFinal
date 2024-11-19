package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeacherModel {
    SubjectModel subjectModel = new SubjectModel();
    GradeModel gradeModel = new GradeModel();


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
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT t.t_id, t.name, t.phone_number, t.email, s.sub_name AS subject, " +
                            "GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grades " +
                            "FROM teacher AS t " +
                            "LEFT JOIN subject AS s ON t.subject_id = s.sub_id " +
                            "LEFT JOIN teacher_grade AS tg ON t.t_id = tg.teacher_id " +
                            "LEFT JOIN grade AS g ON tg.grade_id = g.g_id " +
                            "GROUP BY t.t_id, t.name, t.phone_number, t.email, s.sub_name " +
                            "ORDER BY t.t_id"
            );

            ArrayList<TeacherDto> teachers = new ArrayList<>();
            while (resultSet.next()) {
                String subject = resultSet.getString("subject");

                String[] gradeArray = resultSet.getString("grades") != null
                        ? resultSet.getString("grades").split(",")
                        : new String[0];

                TeacherDto teacherDto = new TeacherDto(
                        resultSet.getString("t_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        subject,
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



    public boolean saveTeacher(TeacherDto teacherDto, List<String> grades) {
        Connection connection = null;

        try {
            // Get a connection to the database
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start a transaction

            // Insert teacher details into the teacher table
            String teacherSQL = "INSERT INTO teacher (t_id, name, phone_number, email, subject_id) VALUES (?, ?, ?, ?, ?)";
            String subjectId = subjectModel.getSubjectIdFromName(teacherDto.getSubject()); // Fetch subject ID by name

            // Ensure that the subjectId is valid
            if (subjectId == null) {
                connection.rollback();
                return false; // Invalid subject
            }

            // Execute the query using the CrudUtil (return value should be boolean or void)
            boolean teacherInserted = CrudUtil.execute(
                    teacherSQL,
                    teacherDto.getTeacherId(),
                    teacherDto.getName(),
                    teacherDto.getPhoneNumber(),
                    teacherDto.getEmail(),
                    subjectId
            );

            // Check if the insert was successful
            if (!teacherInserted) {
                connection.rollback();
                return false; // Teacher insert failed
            }

            // Insert teacher-grade relationships
            for (String gradeName : grades) {
                String gradeId = gradeModel.getGradeIdFromName(gradeName); // Fetch grade ID by name

                if (gradeId != null) {
                    String teacherGradeSQL = "INSERT INTO teacher_grade (teacher_id, grade_id) VALUES (?, ?)";
                    boolean gradeInserted = CrudUtil.execute(teacherGradeSQL, teacherDto.getTeacherId(), gradeId);

                    if (!gradeInserted) {
                        connection.rollback();
                        return false; // Grade insert failed
                    }
                } else {
                    connection.rollback();
                    return false; // Invalid grade
                }
            }

            connection.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Restore auto-commit
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }




}