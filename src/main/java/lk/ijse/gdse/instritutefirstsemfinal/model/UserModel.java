package lk.ijse.gdse.instritutefirstsemfinal.model;

import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {


    public boolean verifyUser(String username, String password) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM user WHERE user_name=? AND pass_word=?",username,password);

            if (rs.next()) {
                if (rs.getString("user_name").equals(username) && rs.getString("pass_word").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean verifyUser(String username, String password) {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE user_name=? AND pass_word=?");
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                if (rs.getString("user_name").equals(username) && rs.getString("pass_word").equals(password)) {
//                    return true;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public Pair<Boolean,String> checkGmailDB(String email){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE email=?",email);
            if (resultSet.next()){
                String result = resultSet.getString("email");
                return new Pair<>(true,result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Pair<>(false,null);
    }


//    public Pair<Boolean,String> checkGmailDB(String email){
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            String sql = "select * from user where email=?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1,email);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                String result = resultSet.getString("email");
//                return new Pair<>(true,result);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return new Pair<>(false,null);
//    }


}
