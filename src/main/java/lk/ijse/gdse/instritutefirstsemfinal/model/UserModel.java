package lk.ijse.gdse.instritutefirstsemfinal.model;

import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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

    public List<Object> checkGmailDB(String email) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE email=?", email);
            if (resultSet.next()) {
                String resultEmail = resultSet.getString("email");
                String adminName = resultSet.getString("user_name");
                return Arrays.asList(true, resultEmail, adminName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList(false, null, null);
    }

    public boolean updatePasswordUser(String newPassword, String gmail) {
        System.out.println("Attempting to update password for email: " + gmail);
        try {

            Boolean isUpdated = CrudUtil.execute("UPDATE user SET pass_word = ? WHERE email = ?", newPassword, gmail);

            return isUpdated != null && isUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
