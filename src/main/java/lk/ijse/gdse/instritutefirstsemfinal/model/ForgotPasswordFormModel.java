package lk.ijse.gdse.instritutefirstsemfinal.model;

import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordFormModel {

    public Pair<Boolean,String> checkGmail(String email){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "select * from user where email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String result = resultSet.getString("email");
                return new Pair<>(true,result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Pair<>(false,null);
    }
}
