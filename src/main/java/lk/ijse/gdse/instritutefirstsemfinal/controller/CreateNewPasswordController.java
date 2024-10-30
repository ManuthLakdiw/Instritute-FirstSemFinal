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

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswordController implements Initializable {

    UserModel user = new UserModel();

    String newPassword;
    String confirmPassword;


    @FXML
    private Pane createNewPasswordFormPane;

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
        if (txtNewShowPassWord.isVisible()) {
            newPassword = txtNewShowPassWord.getText();
        } else {
            newPassword = txtNewHidePassWord.getText();
        }

        if (txtConfirmShowPassWord.isVisible()) {
            confirmPassword = txtConfirmShowPassWord.getText();
        } else {
            confirmPassword = txtConfirmHidePassWord.getText();
        }


        if (newPassword.equals(confirmPassword)) {
            boolean isUpdate = user.updatePasswordUser(newPassword,ForgotPasswordFormController.gmail);
            System.out.println(isUpdate);

            if (isUpdate) {
                try {
                    createNewPasswordFormPane.getChildren().clear();
                    Pane pane = FXMLLoader.load(getClass().getResource("/view/pwResetSuccessForm.fxml"));
                    createNewPasswordFormPane.getChildren().add(pane);
                    Stage stage = (Stage) createNewPasswordFormPane.getScene().getWindow();
                    stage.setTitle("Changed Password");
                }catch (IOException e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load SuccessForm!");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    alert.showAndWait();
                }


            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("password reset!");
            alert.setHeaderText(null);
            alert.setContentText("Password doesn't match!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
            PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
            delay.setOnFinished(ev -> alert.close());
            delay.play();
            alert.show();
        }


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

