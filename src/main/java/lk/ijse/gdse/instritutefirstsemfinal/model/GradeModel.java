package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GradeModel {

    public ArrayList<GradeDto> getGrades() {
        ArrayList<GradeDto> gradeList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("select * from grade");
            while (resultSet.next()) {
                GradeDto gradeDto = new GradeDto(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );
                gradeList.add(gradeDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeList;
    }



    public List<String> getAllSubjects(String subject) {
        List<String> gradesList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grade_names FROM subject AS s LEFT JOIN subject_grade AS sg ON s.sub_id = sg.subject_id LEFT JOIN grade AS g ON sg.grade_id = g.g_id where sub_name = ? GROUP BY s.sub_id, s.sub_name, s.description ORDER BY s.sub_id", subject);

            if (resultSet.next()) {
                String grades = resultSet.getString(1);
                if (grades != null) {
                    // Convert the comma-separated string into a List
                    gradesList = Arrays.asList(grades.split(","));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradesList;
    }



        // Fetch grades based on the subject name
        public List<String> getGradesForSubject(String subjectName)  {
            try {
                List<String> grades = new ArrayList<>();

                    String sql = "SELECT s.sub_name, g.grade FROM grade g JOIN subject_grade sg ON g.g_id = sg.grade_id JOIN subject s ON sg.subject_id = s.sub_id WHERE s.sub_name = ?";
                ResultSet resultSet = CrudUtil.execute(sql, subjectName);

                while (resultSet.next()) {
                    grades.add(resultSet.getString(2));
                }

                return grades;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }



    public String getGradeIdFromName(String grade) {
        String gradeID = null;

        try {
            // Prepare a query to retrieve the subject ID for a single subject name
            String query = "SELECT g_id FROM grade WHERE grade = ?";

            // Execute the query with the provided subject name
            ResultSet resultSet = CrudUtil.execute(query, grade);

            // Extract the subject ID from the result set
            if (resultSet.next()) {
                gradeID = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gradeID;
    }

}
