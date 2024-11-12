package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherModel {
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "T001";
    }


    public ArrayList<TeacherDto> getAllTeachers() {
        ArrayList<TeacherDto> teachers = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("select * from teacher");
            while (resultSet.next()) {
                TeacherDto teacherDto = new TeacherDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                teachers.add(teacherDto);
            }
            return teachers;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
