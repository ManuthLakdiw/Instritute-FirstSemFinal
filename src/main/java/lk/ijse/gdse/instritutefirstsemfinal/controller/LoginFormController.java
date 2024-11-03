package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    UserModel userModel = new UserModel();

    @FXML
    private FontIcon hideEye;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Pane contentPane;

    @FXML
    private Pane loginPageBackgroundPane;

    @FXML
    private FontIcon openEye;

    @FXML
    private PasswordField txtHidePassWord;

    @FXML
    private TextField txtShowPassWord;

    @FXML
    private TextField txtUserName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
    }



    @FXML
    private void closeEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(true);
        openEye.setVisible(true);
        hideEye.setVisible(false);
        txtHidePassWord.setVisible(false);
        txtShowPassWord.requestFocus();
        txtShowPassWord.positionCaret(txtShowPassWord.getText().length());

        if (txtShowPassWord.getText().isEmpty()) {
            RegexUtil.resetStyle(txtHidePassWord);
//            txtShowPassWord.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");

        }
//        txtShowPassWord.setText(txtHidePassWord.getText());

    }




    @FXML
    private void openEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
        hideEye.setVisible(true);
        txtHidePassWord.setVisible(true);
        txtHidePassWord.requestFocus();
        txtHidePassWord.positionCaret(txtHidePassWord.getText().length());

        if (txtHidePassWord.getText().isEmpty()) {
            RegexUtil.resetStyle(txtHidePassWord);
//            txtHidePassWord.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");

        }

//        txtHidePassWord.setText(txtShowPassWord.getText());


    }




    @FXML
    private void txtUserNameOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (txtHidePassWord.isVisible()) {
                txtHidePassWord.requestFocus();
            }else {
                txtShowPassWord.requestFocus();
            }
        }
    }



    @FXML
    private void forgotPassWordOnClicked(MouseEvent mouseEvent) {
                NavigationUtil.loadPane(LoginFormController.class,contentPane,"Forgot Password","/view/forgotPasswordForm.fxml");
    }



    @FXML
    private void passwordFieldOnKeyPressed(KeyEvent keyEvent) {
        if (txtHidePassWord.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }



    @FXML
    private void passwordVisibleFieldOnAction(KeyEvent keyEvent) {
        if(txtShowPassWord.getText().isEmpty()){
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }



    @FXML
    private void btnLoginClicked(ActionEvent actionEvent) {
        String uName = txtUserName.getText();
        String pWord = txtHidePassWord.isVisible() ? txtHidePassWord.getText() : txtShowPassWord.getText();

        RegexUtil.resetStyle(txtHidePassWord, txtShowPassWord,txtHidePassWord);
//        resetStyle(txtShowPassWord,txtHidePassWord,txtUserName);

        if(uName.isEmpty() || pWord.isEmpty()){
            if(uName.isEmpty() && pWord.isEmpty()){
                txtUserName.requestFocus();
                RegexUtil.setErrorStyle(txtShowPassWord,txtHidePassWord,txtUserName);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill all the fields");

            }else if (uName.isEmpty()) {
                txtUserName.requestFocus();
                RegexUtil.setErrorStyle(txtUserName);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill the username field.");
            }else {
                txtHidePassWord.requestFocus();
                RegexUtil.setErrorStyle(txtHidePassWord,txtShowPassWord);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill the password field.");
            }
        } else {
            boolean logging = userModel.verifyUser(uName, pWord);
            if (!logging) {
                RegexUtil.setErrorStyle(txtUserName,txtHidePassWord,txtShowPassWord);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"username and password doesn't match.");
            } else {
                NavigationUtil.loadPane(LoginFormController.class,contentPane,"Dashboard","/view/dashBoardForm.fxml");
            }
        }
    }



    @FXML
    private void txtUserNameOnkeyType(KeyEvent keyEvent) {
        String checkisEmpty = keyEvent.getCharacter();
        if (!checkisEmpty.isEmpty()) {
            RegexUtil.resetStyle(txtUserName);
//            resetStyle(txtUserName);
        }
    }



    @FXML
    private void txtShowPassWordOnKeyType(KeyEvent keyEvent) {
        txtHidePassWord.setText(txtShowPassWord.getText());
        String checkisEmpty = keyEvent.getCharacter();
        if(!checkisEmpty.isEmpty()){
            RegexUtil.resetStyle(txtShowPassWord,txtHidePassWord);
//            resetStyle(txtShowPassWord,txtHidePassWord);
        }
    }



    @FXML
    private void txtHidePassWordOnKeyType(KeyEvent keyEvent) {
        txtShowPassWord.setText(txtHidePassWord.getText());
        String checkisEmpty = keyEvent.getCharacter();
        if(!checkisEmpty.isEmpty()){
            RegexUtil.resetStyle(txtShowPassWord,txtHidePassWord);
//            resetStyle(txtHidePassWord,txtShowPassWord);
        }
    }


}
