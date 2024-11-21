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

    public boolean saveExam(ExamDto examDto) {
        System.out.println(examDto.getExamDate());
        System.out.println(examDto.getGrade());
        System.out.println(examDto.getExamId());
        System.out.println(examDto.getSubject());
        System.out.println(examDto.getExamType());
        System.out.println(examDto.getExamId());
        try {
            return CrudUtil.execute("insert into exam values(?,?,?,?,?,?)",
                    examDto.getExamId(),
                    examDto.getSubject(),
                    examDto.getExamType(),
                    examDto.getExamDate(),
                    examDto.getExamDescription(),
                    examDto.getGrade()
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateExam(ExamDto examDto) {
        try {
            return CrudUtil.execute("UPDATE exam SET subject_id = ?, exam_type = ?, date = ?, description = ?, grade = ? WHERE exam_id = ?",
                    examDto.getSubject(),
                    examDto.getExamType(),
                    examDto.getExamDate(),
                    examDto.getExamDescription(),
                    examDto.getGrade(),
                    examDto.getExamId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ExamDto> isExitingExam(String examID) {
        ArrayList<ExamDto> exams = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("select * from exam where exam_id = ?", examID);
            if (resultSet.next()) {
                ExamDto exam = new ExamDto(
                        resultSet.getString(1),
                        resultSet.getString(6),
                        resultSet.getString(2),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getString(3),
                        resultSet.getString(5)
                );
                exams.add(exam);
            }
            return exams;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteExam(String id) {
        try {
            return CrudUtil.execute("delete from exam where exam_id = ?",id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
