package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
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
    public void btnContinueOnAction(ActionEvent event) {
        String code1 = txtCode1.getText().trim();
        String code2 = txtCode2.getText().trim();
        String code3 = txtCode3.getText().trim();
        String code4 = txtCode4.getText().trim();

        resetStyles();

        if (code1.matches("^[0-9]$") && code2.matches("^[0-9]$") &&
                code3.matches("^[0-9]$") && code4.matches("^[0-9]$")) {
            String checkOtp = code1 + code2 + code3 + code4;

            if (ForgotPasswordFormController.otp.equals(checkOtp)) {
                try {
                    resetPasswordFormPane.getChildren().clear();
                    Pane load = FXMLLoader.load(getClass().getResource("/view/createNewPasswordForm.fxml"));
                    resetPasswordFormPane.getChildren().add(load);
                    Stage stage = (Stage) resetPasswordFormPane.getScene().getWindow();
                    stage.setTitle("Create New Password");
                } catch (IOException e) {
                    e.printStackTrace();
                    showErrorMessage("Failed to load Create New Password!");
                }
            } else {
                showInfoMessage("OTP code doesn't match!");
                setTextFieldError(txtCode1,txtCode2,txtCode3,txtCode4);
                clearTextFields();
                txtCode1.requestFocus();
            }
        } else {
            showInfoMessage("Please enter a valid code!");
            if (!code1.matches("^[0-9]$")) {
                setTextFieldError(txtCode1);
            }
            if (!code2.matches("^[0-9]$")) {
                setTextFieldError(txtCode2);
            }
            if (!code3.matches("^[0-9]$")) {
                setTextFieldError(txtCode3);
            }
            if (!code4.matches("^[0-9]$")) {
                setTextFieldError(txtCode4);
            }
        }
    }



    @FXML
    public void txtCode1OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode1, txtCode2);
    }



    @FXML
    public void txtCode2OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode2, txtCode3);
    }



    @FXML
    public void txtCode3OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode3, txtCode4);
    }



    @FXML
    public void txtCode4OnKeyType(KeyEvent keyEvent) {
        handleTextFieldInput(keyEvent, txtCode4, null);
    }


    @FXML
    public void txtCode1OnkeyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode2.requestFocus();
        }
    }



    @FXML
    public void txtCode2OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode3.requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode1.requestFocus();
        }
    }



    @FXML
    public void txtCode3OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT){
            txtCode4.requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode2.requestFocus();
        }
    }



    @FXML
    public void txtCode4OnkeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT){
            txtCode3.requestFocus();
        }
    }






    private void handleTextFieldInput(KeyEvent keyEvent, TextField currentField, TextField nextField) {
        String input = keyEvent.getCharacter();

        if (input.matches("\\d")) {
            currentField.setText(input);
            currentField.setStyle("-fx-border-color: #03045E; -fx-border-width: 2px; -fx-border-radius: 5; -fx-background-color: transparent;");

            if (nextField != null) {
                nextField.requestFocus();
            }
        } else {
            currentField.clear();
            setTextFieldError(currentField);
        }


        if (currentField.getText().isEmpty()) {
            currentField.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");
        }
    }


    private void clearTextFields() {
        txtCode1.clear();
        txtCode2.clear();
        txtCode3.clear();
        txtCode4.clear();
    }


    private void resetStyles() {
        String defaultStyle = "-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        txtCode1.setStyle(defaultStyle);
        txtCode2.setStyle(defaultStyle);
        txtCode3.setStyle(defaultStyle);
        txtCode4.setStyle(defaultStyle);
    }


    private void setTextFieldError(TextField... fields) {
        String errorStyle  = "-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (TextField field : fields) {
            field.setStyle(errorStyle);
        }

    }


    private void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText(message);
        styleAlert(alert);
        PauseTransition delay = new PauseTransition(Duration.seconds(1.9));
        delay.setOnFinished(eve -> alert.close());
        delay.play();
        alert.show();
    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        styleAlert(alert);
        alert.showAndWait();
    }


    private void styleAlert(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
    }


}
