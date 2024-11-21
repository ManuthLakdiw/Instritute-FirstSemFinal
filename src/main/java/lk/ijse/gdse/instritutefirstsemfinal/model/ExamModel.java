package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamModel {

    public String getNextExamID(){
        try {
            ResultSet resultSet = CrudUtil.execute("select exam_id from exam order by exam_id desc limit 1");
            if (resultSet.next()) {
                String lastID = resultSet.getString(1);
                String substring = lastID.substring(1);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("E%03d", newId);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "E001";

    }

    public ArrayList<ExamDto> getAllExams(){
        ArrayList<ExamDto> exams = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT e.exam_id, g.grade, s.sub_name AS subject, e.date, e.exam_type, e.description FROM exam AS e LEFT JOIN subject AS s ON e.subject_id = s.sub_id LEFT JOIN grade AS g ON e.grade = g.g_id");
            while (resultSet.next()) {
                ExamDto exam = new ExamDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                exams.add(exam);
            }
            return exams;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
