//package lk.ijse.gdse.instritutefirstsemfinal.controller;
//
//import javafx.animation.PauseTransition;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
//import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
//import org.kordamp.ikonli.javafx.FontIcon;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class CreateNewPasswordController implements Initializable {
//
//    private final UserModel user = new UserModel();
//
//    private String newPassword;
//    private String confirmPassword;
//
//    @FXML
//    private Label lblPasswordStatus;
//
//    @FXML
//    private Pane createNewPasswordFormPane;
//
//
//    @FXML
//    private Button btnResetPassword;
//
//
//
//    @FXML
//    private Label lblsendEmail;
//
//    @FXML
//    private Label lblsendEmail1;
//
//    @FXML
//    private PasswordField txtConfirmHidePassWord;
//
//
//
//    @FXML
//    private PasswordField txtNewHidePassWord;
//
//
//    @FXML
//    private Label lblpasswordConfirm;
//
//
//
//
//
//    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";
//
//    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";
//
//    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";
//
//
//
//
//
//    @FXML
//    void btnResetPasswordOnClicked(ActionEvent event) {
//        boolean newPasswordEmpty = txtNewShowPassWord.getText().isEmpty() && txtNewHidePassWord.getText().isEmpty();
//        boolean confirmPasswordEmpty = txtConfirmShowPassWord.getText().isEmpty() && txtConfirmHidePassWord.getText().isEmpty();
//
//        resetStyle(txtNewHidePassWord,txtConfirmHidePassWord,txtConfirmShowPassWord,txtNewShowPassWord);
//
//
//        if (newPasswordEmpty && confirmPasswordEmpty) {
//            alert("Reset Password", "Please fill in both New Password and Confirm Password fields!");
//            errorStyle(txtNewShowPassWord, txtNewHidePassWord, txtConfirmShowPassWord, txtConfirmHidePassWord);
//
//        }
//        else if (newPasswordEmpty) {
//            alert("Reset Password", "Please fill in the New Password field!");
//            errorStyle(txtNewShowPassWord, txtNewHidePassWord);
//
//        }
//
//        else if (confirmPasswordEmpty) {
//            alert("Reset Password", "Please fill in the Confirm Password field!");
//            errorStyle(txtConfirmShowPassWord, txtConfirmHidePassWord);
//
//        }
//
//        else {
//            newPassword = txtNewShowPassWord.isVisible() ? txtNewShowPassWord.getText() : txtNewHidePassWord.getText();
//            confirmPassword = txtConfirmShowPassWord.isVisible() ? txtConfirmShowPassWord.getText() : txtConfirmHidePassWord.getText();
//
//
//            if (newPassword.equals(confirmPassword)) {
//                boolean isUpdate = user.updatePasswordUser(newPassword, ForgotPasswordFormController.gmail);
//
//                if (isUpdate) {
//                    try {
//                        createNewPasswordFormPane.getChildren().clear();
//                        Pane pane = FXMLLoader.load(getClass().getResource("/view/pwResetSuccessForm.fxml"));
//                        createNewPasswordFormPane.getChildren().add(pane);
//                        Stage stage = (Stage) createNewPasswordFormPane.getScene().getWindow();
//                        stage.setTitle("Changed Password");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        showErrorAlert("Error", "Failed to load SuccessForm!");
//                    }
//                }
//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Password Reset!");
//                alert.setHeaderText(null);
//                alert.setContentText("Passwords doesn't match!");
//                DialogPane dialogPane = alert.getDialogPane();
//                dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
//                alert.show();
//            }
//        }
//    }
//
//
//
//
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//
//
//
//    public void errorStyle(TextField... fields){
//        String errorStyle = "-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
//        for (TextField field : fields) {
//            field.setStyle(errorStyle);
//        }
//
//    }
//
//    public void alert(String setTitle, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(setTitle);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
//        PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
//        delay.setOnFinished(event -> alert.close());
//        delay.play();
//        alert.show();
//
//    }
//
//    private void showErrorAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//
//    private void resetStyle(TextField... fields) {
//        String defaultStyle = "-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
//        for (TextField field : fields) {
//            field.setStyle(defaultStyle);
//        }
//    }
//
//
//
//
//
//    @FXML
//    public void txtNewHidePassWordOnKeyType(KeyEvent keyEvent) {
//        newPassword = txtNewHidePassWord.getText();
//        txtNewShowPassWord.setText(newPassword);
////        updatePasswordStatus(newPassword);
//
//    }
//
//
//
//
//    @FXML
//    public void txtConfirmHidePassWordOnKeyType(KeyEvent keyEvent) {
//        txtConfirmShowPassWord.setText(txtConfirmHidePassWord.getText());
////        checkPasswordMatch();
//
//    }
//
//
////    private void updatePasswordStatus(String newPassword) {
////        if (newPassword.isEmpty()) {
////            lblPasswordStatus.setText("");
////            return;
////        }
////
////        if (newPassword.matches(passwordStrongRegex)) {
////            lblPasswordStatus.setText("Strong ✔︎");
////            lblPasswordStatus.setTextFill(Color.GREEN);
////        } else if (newPassword.matches(passwordMediumRegex)) {
////            lblPasswordStatus.setText("Medium ⚠︎");
////            lblPasswordStatus.setTextFill(Color.BROWN);
////        } else if (newPassword.matches(passwordWeakRegex)) {
////            lblPasswordStatus.setText("Weak ⛔︎");
////            lblPasswordStatus.setTextFill(Color.RED);
////        }
////        if (newPassword.matches(passwordWeakRegex)) {
////            txtConfirmHidePassWord.setDisable(true);
////            txtConfirmShowPassWord.setDisable(true);
////            openEye2.setDisable(true);
////            hideEye2.setDisable(true);
////            lblpasswordConfirm.setText("For enable this you should enter Medium or Strong password");
////            lblpasswordConfirm.setTextFill(Color.RED);
////        }else{
////            txtConfirmHidePassWord.setDisable(false);
////            txtConfirmShowPassWord.setDisable(false);
////            openEye2.setDisable(false);
////            hideEye2.setDisable(false);
////            lblpasswordConfirm.setText("");
////        }
////    }
////
////    private void checkPasswordMatch() {
////        confirmPassword = txtConfirmShowPassWord.getText();
////        if (newPassword.isEmpty()){
////            txtConfirmHidePassWord.clear();
////            txtConfirmShowPassWord.clear();
////        }        if (!confirmPassword.equals(newPassword)) {
////            RegexUtil.resetStyle(txtConfirmHidePassWord, txtConfirmShowPassWord);
////            if (!newPassword.isEmpty()) {
////                lblpasswordConfirm.setText("Doesn't match password!");
////                lblpasswordConfirm.setTextFill(Color.RED);
////                btnResetPassword.setDisable(true);
////            } else if (txtConfirmHidePassWord.getText().isEmpty() || txtConfirmShowPassWord.getText().isEmpty()) {
////                lblpasswordConfirm.setText("");  // Clear the error message if they match
////                btnResetPassword.setDisable(false);
////            }
////        } else {
////            lblpasswordConfirm.setText("");  // Clear the error message if they match
////            btnResetPassword.setDisable(false);
////        }
////    }
//
//
//}
//

package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswordController implements Initializable {

    private final UserModel user = new UserModel();

    private String newPassword = "";
    private String confirmPassword = "";

    @FXML
    private Button btnResetPassword;

    @FXML
    private Pane createNewPasswordFormPane;

    @FXML
    private Label lblPasswordStatus;

    @FXML
    private Label lblpasswordConfirm;

    @FXML
    private Label lblsendEmail;

    @FXML
    private Label lblsendEmail1;

    @FXML
    private PasswordField txtConfirmHidePassWord;

    @FXML
    private PasswordField txtNewHidePassWord;

    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";
    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";
    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";



    @FXML
    void btnResetPasswordOnClicked(ActionEvent event) {
        if (newPassword.isEmpty()){
            AlertUtil.informationAlert(CreateNewPasswordController.class,null,false,"Please enter your new password.");
            return;
        }else if (confirmPassword.isEmpty()){
            AlertUtil.informationAlert(CreateNewPasswordController.class,null,false,"Please confirm your new password.");
            return;
        }else if (newPassword.equals(confirmPassword)){
            boolean isUpdate =user.updatePasswordUser(newPassword, ForgotPasswordFormController.gmail);

            if (isUpdate){
                NavigationUtil.loadPane(CreateNewPasswordController.class,createNewPasswordFormPane,"Forgot Password[Reset Success!!!]","/view/pwResetSuccessForm.fxml");
            }

        }



    }

    @FXML
    void txtConfirmHidePassWordOnKeyType(KeyEvent event) {
        confirmPassword = txtConfirmHidePassWord.getText();

        if (txtConfirmHidePassWord.getText().isEmpty() || txtNewHidePassWord.getText().isEmpty()) {

            lblpasswordConfirm.setText("You should enter a medium or strong password ⇪");
            lblpasswordConfirm.setTextFill(Color.web("#03045e"));
            RegexUtil.resetStyle(txtConfirmHidePassWord);
        } else if (confirmPassword.equals(newPassword)) {

            RegexUtil.resetStyle(txtConfirmHidePassWord);
            lblpasswordConfirm.setText("Password is confirmed ✔︎");
            lblpasswordConfirm.setTextFill(Color.GREEN);
        } else {

            RegexUtil.setErrorStyle(false, txtConfirmHidePassWord);
            lblpasswordConfirm.setText("Passwords don't match");
            lblpasswordConfirm.setTextFill(Color.RED);
        }
    }



    @FXML
    void txtNewHidePassWordOnKeyType(KeyEvent event) {
        newPassword = txtNewHidePassWord.getText();

        RegexUtil.resetStyle(txtNewHidePassWord);

        if (newPassword.isEmpty()) {
            lblPasswordStatus.setText(" ");
            lblpasswordConfirm.setText(" ");
            txtConfirmHidePassWord.clear();
            txtConfirmHidePassWord.setDisable(true);
        } else if (newPassword.length() > 13) {

            RegexUtil.setErrorStyle(false, txtNewHidePassWord);
            lblPasswordStatus.setText("Password too long!!!");
            lblPasswordStatus.setTextFill(Color.RED);
            txtConfirmHidePassWord.setDisable(true);
        } else if (newPassword.matches(passwordStrongRegex)) {

            lblPasswordStatus.setText("Strong ✔︎");
            lblPasswordStatus.setTextFill(Color.GREEN);
            txtConfirmHidePassWord.setDisable(false);
        } else if (newPassword.matches(passwordMediumRegex)) {

            lblPasswordStatus.setText("Medium ⚠︎");
            lblPasswordStatus.setTextFill(Color.BROWN);
            txtConfirmHidePassWord.setDisable(false);
        } else if (newPassword.matches(passwordWeakRegex)) {

            lblPasswordStatus.setText("Weak ⛔︎");
            lblPasswordStatus.setTextFill(Color.RED);
            txtConfirmHidePassWord.setDisable(true);
            lblpasswordConfirm.setText("You should enter a medium or strong password ⇪");
            lblpasswordConfirm.setStyle("-fx-text-fill: #03045e");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNewHidePassWord.requestFocus();
        txtConfirmHidePassWord.setDisable(true);
    }
}

