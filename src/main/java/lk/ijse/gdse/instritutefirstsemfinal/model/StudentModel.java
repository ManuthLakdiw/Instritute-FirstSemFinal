package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentModel {

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

                // Add the student to the list
                students.add(studentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
