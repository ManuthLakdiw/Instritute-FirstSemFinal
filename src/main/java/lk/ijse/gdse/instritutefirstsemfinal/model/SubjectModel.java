package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectModel
{
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
            ResultSet resultSet = CrudUtil.execute("select * from subject");
            while (resultSet.next()) {
                SubjectDto subjectDto = new SubjectDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                subjects.add(subjectDto);
            }
            return subjects;
        }catch (SQLException e){
            e.printStackTrace();

        }
        return null;
    }

    public boolean saveSubject(SubjectDto subjectDto) {
        try {
            return CrudUtil.execute("insert into subject values(?,?,?)",
                    subjectDto.getSubjectId(),
                    subjectDto.getSubjectName(),
                    subjectDto.getSubjectDescription()
            );

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public SubjectDto getSubjectByID(String subjectId) {

        try {
            ResultSet resultSet = CrudUtil.execute("select * from subject where sub_id=?", subjectId);
            if (resultSet.next()) {
                SubjectDto subjectDto = new SubjectDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                return subjectDto;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSubject(SubjectDto subjectDto) {
        try {
            return CrudUtil.execute("update subject set sub_name=?, description=? where sub_id=?",
                        subjectDto.getSubjectName(),
                        subjectDto.getSubjectDescription(),
                        subjectDto.getSubjectId()
                    );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubject(String id)   {
        try {
            return CrudUtil.execute("delete from subject where sub_id=?", id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
