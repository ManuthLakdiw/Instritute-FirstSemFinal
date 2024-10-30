package lk.ijse.gdse.instritutefirstsemfinal.model;

import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

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

    public boolean updatePasswordUser(String newPassword, String gmail) {
        System.out.println("Attempting to update password for email: " + gmail);
        try {
            // Execute the update query, which will return a Boolean value.
            Boolean isUpdated = CrudUtil.execute("UPDATE user SET pass_word = ? WHERE email = ?", newPassword, gmail);

            // Check if the update was successful
            return isUpdated != null && isUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean updatePasswordUser(String newPassword) {
//        try {
//            ResultSet rs = CrudUtil.execute("Update user set pass_word=? where user_name=?");
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }


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
