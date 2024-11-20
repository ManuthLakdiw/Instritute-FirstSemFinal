package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
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

    public ArrayList<String> getSubjectsByGradeId(String gradeId) {
        ArrayList<String> subjects = new ArrayList<>();
        String query = "SELECT subject.sub_name FROM subject JOIN subject_grade ON subject.sub_id = subject_grade.subject_id WHERE subject_grade.grade_id = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, gradeId)) {
            while (resultSet.next()) { // The `while` block should be here.
                subjects.add(resultSet.getString("sub_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public boolean isGradeExists(String gradeId) {
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT COUNT(*) FROM grade WHERE g_id = ?",
                    gradeId
            );

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
