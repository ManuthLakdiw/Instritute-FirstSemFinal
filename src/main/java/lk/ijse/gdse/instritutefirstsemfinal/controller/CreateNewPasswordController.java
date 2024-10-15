//package lk.ijse.gdse.instritutefirstsemfinal.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import org.kordamp.ikonli.javafx.FontIcon;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class CreateNewPasswordController implements Initializable {
//
//    @FXML
//    private Button btnLogin;
//
//    @FXML
//    private FontIcon hideEye;
//
//    @FXML
//    private FontIcon hideEye1;
//
//    @FXML
//    private Label lblsendEmail;
//
//    @FXML
//    private Label lblsendEmail1;
//
//    @FXML
//    private FontIcon openEye;
//
//    @FXML
//    private FontIcon openEye1;
//
//    @FXML
//    private PasswordField txtConfirmHidePassWord;
//
//    @FXML
//    private TextField txtConfirmShowPassWord;
//
//    @FXML
//    private PasswordField txtNewHidePassWord;
//
//    @FXML
//    private TextField txtNewShowPassWord;
//
//    @FXML
//    void btnResetPasswordOnClicked(ActionEvent event) {
//
//    }
//
//    @FXML
//    void confirmPasswordHideEyeOnClicked(MouseEvent event) {
//
//    }
//
//    @FXML
//    void newPasswordHideEyeOnClicked(MouseEvent event) {
//        txtNewHidePassWord.setVisible(false);
//        hideEye.setVisible(false);
//        txtConfirmHidePassWord.setVisible(true);
//        hideEye.setVisible(true);
//        txtConfirmShowPassWord.setText(txtNewHidePassWord.getText());
//
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtNewShowPassWord.setVisible(false);
//        txtConfirmShowPassWord.setVisible(false);
//        openEye.setVisible(false);
//        openEye1.setVisible(false);
//    }
//}


package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswordController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private FontIcon hideEye;

    @FXML
    private FontIcon hideEye1;

    @FXML
    private Label lblsendEmail;

    @FXML
    private Label lblsendEmail1;

    @FXML
    private FontIcon openEye;

    @FXML
    private FontIcon openEye1;

    @FXML
    private PasswordField txtConfirmHidePassWord;

    @FXML
    private TextField txtConfirmShowPassWord;

    @FXML
    private PasswordField txtNewHidePassWord;

    @FXML
    private TextField txtNewShowPassWord;

    @FXML
    void btnResetPasswordOnClicked(ActionEvent event) {

    }

    @FXML
    public void confirmPasswordHideEye1nClicked(MouseEvent mouseEvent) {
        txtConfirmHidePassWord.setVisible(false);
        hideEye1.setVisible(false);
        openEye1.setVisible(true);
        txtConfirmShowPassWord.setVisible(true);
        txtConfirmShowPassWord.setText(txtConfirmHidePassWord.getText());

    }


    @FXML
    void newPasswordHideEyeOnClicked(MouseEvent event) {
        txtNewHidePassWord.setVisible(false);
        hideEye.setVisible(false);
        openEye.setVisible(true);
        txtNewShowPassWord.setVisible(true);
        txtNewShowPassWord.setText(txtNewHidePassWord.getText());

    }

    @FXML
    void newPasswordOpenEyeOnClicked(MouseEvent event) {
        txtNewHidePassWord.setVisible(true);
        hideEye.setVisible(true);
        txtNewShowPassWord.setVisible(false);
        openEye.setVisible(false);
        txtNewHidePassWord.setText(txtNewShowPassWord.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNewShowPassWord.setVisible(false);
        txtConfirmShowPassWord.setVisible(false);
        openEye.setVisible(false);
        openEye1.setVisible(false);
    }

    @FXML
    public void confirmPasswordOpenEye1nClicked(MouseEvent mouseEvent) {
        txtConfirmHidePassWord.setVisible(true);
        hideEye1.setVisible(true);
        openEye1.setVisible(false);
        txtConfirmShowPassWord.setVisible(false);
        txtConfirmHidePassWord.setText(txtConfirmShowPassWord.getText());
    }
}

