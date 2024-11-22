package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class ResultModel {

    SubjectModel subjectModel = new SubjectModel();
    StudentModel studentModel = new StudentModel();




    public ArrayList<ResultDto> getAllResults() {
        ArrayList<ResultDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT r.result_id,r.grade , e.subject_id, r.exam_id, r.student_id, r.marks, r.exam_grade, r.status FROM result r LEFT JOIN exam e ON r.exam_id = e.exam_id");
            while (resultSet.next()) {
                String studentName = studentModel.getOneStudentById(resultSet.getString("student_id"));
                String subjectName = subjectModel.getSubjectNameFromId(resultSet.getString("subject_id"));
                ResultDto resultDto = new ResultDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        subjectName,
                        resultSet.getString(4),
                        studentName,
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)

                );
                results.add(resultDto);
            }
            return results;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
