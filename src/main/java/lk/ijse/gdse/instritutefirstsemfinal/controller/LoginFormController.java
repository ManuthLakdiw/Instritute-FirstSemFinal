package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    @FXML
    void closeEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(true);
        openEye.setVisible(true);
        hideEye.setVisible(false);
        txtHidePassWord.setVisible(false);
        txtShowPassWord.setText(txtHidePassWord.getText()); // Set the visible password field
    }



    @FXML
    void openEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
        hideEye.setVisible(true);
        txtHidePassWord.setVisible(true);
        txtHidePassWord.setText(txtShowPassWord.getText());
    }


    public void alert(String setTitle, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(setTitle);
        alert.setHeaderText(null);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
        delay.setOnFinished(event -> alert.close());
        delay.play();
        alert.show();

    }



    @FXML
    public void txtUserNameOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (txtHidePassWord.isVisible()) {
                txtHidePassWord.requestFocus();
            }else {
                txtShowPassWord.requestFocus();
            }
        }
    }



    public void forgotPassWordOnClicked(MouseEvent mouseEvent) {

            try {
                contentPane.getChildren().clear();
                Pane load = FXMLLoader.load(getClass().getResource("/view/forgotPasswordForm.fxml"));
                contentPane.getChildren().add(load);
                Stage stage = (Stage) contentPane.getScene().getWindow();
                stage.setTitle("Forgot Password");
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to load ForgotPasswordForm!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                alert.showAndWait();
            }

    }



    public void passwordFieldOnKeyPressed(KeyEvent keyEvent) {
        if (txtHidePassWord.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
    }


    public void passwordVisibleFieldOnAction(KeyEvent keyEvent) {
        if(txtHidePassWord.getText().isEmpty()){
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }



    public void btbnLoginClicked(ActionEvent actionEvent) {
        String uName = txtUserName.getText();
        String pWord = txtHidePassWord.isVisible() ? txtHidePassWord.getText() : txtShowPassWord.getText();

        if(uName.isEmpty() || pWord.isEmpty()){
            if(uName.isEmpty() && pWord.isEmpty()){
                alert("Logging Error!","Please fill all the fields");
            }else if (uName.isEmpty()) {
                alert("Logging Error!","Please fill the username field.");
            }else {
                alert("Logging Error!","Please fill the password field.");
            }
        } else {
            boolean logging = userModel.verifyUser(uName, pWord);
            if (!logging) {
                alert("Logging Error!", "Your username and password don't match.");
            } else {
                try {
                    contentPane.getChildren().clear();
                    Pane load = FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml"));
                    contentPane.getChildren().add(load);
                    Stage stage = (Stage) contentPane.getScene().getWindow();
                    stage.setTitle("Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load DashBoard!");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    alert.showAndWait();
                }
            }
        }
    }
}
