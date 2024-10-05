package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormModel {

    public boolean verifyUser(String username, String password) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE user_name=? AND pass_word=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                while(rs.getString("user_name").equals(username) && rs.getString("pass_word").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
