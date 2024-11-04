package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordConfirmFormController implements Initializable {

    @FXML
    private Label lblsendEmail;
    @FXML
    private TextField txtCode1;
    @FXML
    private TextField txtCode2;
    @FXML
    private TextField txtCode3;
    @FXML
    private TextField txtCode4;
    @FXML
    private Pane resetPasswordFormPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblsendEmail.setText("We sent a code to " + ForgotPasswordFormController.gmail);
    }



    @FXML
    private void btnContinueOnAction(ActionEvent event) {
        String code1 = txtCode1.getText().trim();
        String code2 = txtCode2.getText().trim();
        String code3 = txtCode3.getText().trim();
        String code4 = txtCode4.getText().trim();

        RegexUtil.resetStyle(txtCode1, txtCode2, txtCode3, txtCode4);

        if (!code1.isEmpty() && !code2.isEmpty() &&
                !code3.isEmpty() && !code4.isEmpty()) {
            String checkOtp = code1 + code2 + code3 + code4;

            if (ForgotPasswordFormController.otp.equals(checkOtp)) {
                    NavigationUtil.loadPane(ResetPasswordConfirmFormController.class,resetPasswordFormPane,"Create New Password","/view/createNewPasswordForm.fxml");

            } else {
                RegexUtil.setErrorStyle(true,txtCode1, txtCode2, txtCode3, txtCode4);
                AlertUtil.informationAlert(ResetPasswordConfirmFormController.class,null,false,"OTP code doesn't match!");
                clearTextFields();
                txtCode1.requestFocus();
            }
        } else {
            AlertUtil.informationAlert(ResetPasswordConfirmFormController.class,null,true,"You should fill all fields!!!");
//            showInfoMessage("Please enter a valid code!");
            if (!code1.matches("^[0-9]$")) {
                RegexUtil.setErrorStyle(true,txtCode1);
//                setTextFieldError(txtCode1);
            }
            if (!code2.matches("^[0-9]$")) {
                RegexUtil.setErrorStyle(true,txtCode2);
//                setTextFieldError(txtCode2);
            }
            if (!code3.matches("^[0-9]$")) {
                RegexUtil.setErrorStyle(true,txtCode3);
//                setTextFieldError(txtCode3);
            }
            if (!code4.matches("^[0-9]$")) {
                RegexUtil.setErrorStyle(true,txtCode4);
//                setTextFieldError(txtCode4);
            }
            txtCode1.requestFocus();
        }
    }



    @FXML
    private void txtCode1OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode1, txtCode2);
    }



    @FXML
    private void txtCode2OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode2, txtCode3);
    }



    @FXML
    private void txtCode3OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode3, txtCode4);
    }



    @FXML
    private void txtCode4OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode4, null);
    }


    @FXML
    private void txtCode1OnkeyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode2.requestFocus();
        }
    }



    @FXML
    private void txtCode2OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode3.requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode1.requestFocus();
        }
    }



    @FXML
    private void txtCode3OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode4.requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode2.requestFocus();
        }
    }



    @FXML
    private void txtCode4OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode3.requestFocus();
        }
    }






    private void handleTextFieldInput(KeyEvent keyEvent, TextField currentField, TextField nextField) {
        String input = keyEvent.getCharacter();

        if (input.matches("\\d")) {
            currentField.setText(input);
            RegexUtil.resetStyle(currentField);

            if (nextField != null) {
                nextField.requestFocus();
            }
        } else {
            currentField.clear();
            RegexUtil.setErrorStyle(true,currentField);
        }


        if (currentField.getText().isEmpty()) {
            RegexUtil.resetStyle(currentField);
        }
    }


    private void clearTextFields() {
        txtCode1.clear();
        txtCode2.clear();
        txtCode3.clear();
        txtCode4.clear();
    }

}
