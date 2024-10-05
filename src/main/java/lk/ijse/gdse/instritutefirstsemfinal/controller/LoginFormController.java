package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.model.LoginFormModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    LoginFormModel loginFormModel = new LoginFormModel();

    String passWord;

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
    void btnLoginClicked(ActionEvent event) {
        String uName = txtUserName.getText();
        String pWord = txtShowPassWord.getText();

        if(uName.isEmpty() || pWord.isEmpty()){
            if(uName.isEmpty() && pWord.isEmpty()){
                alert("Logging Error!","Please fill all the fields");
            }else if (uName.isEmpty()) {
                alert("Logging Error!","Please fill the username field.");
            }else {
                alert("Logging Error!","Please fill the password field.");
            }
        }else{
            boolean logging =loginFormModel.verifyUser(uName, pWord);
            if (!logging) {
                alert("logging Error!","Your username and password doesn't match.");
            }else{
                alert("logging Success!","Welcome back.");
                try {
                    contentPane.getChildren().clear();
                    Pane load = FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml"));
                    contentPane.getChildren().add(load);
                    Stage stage = (Stage) contentPane.getScene().getWindow();
                    stage.setTitle("Dashboard");
                }catch (IOException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Fail to load DashBoard!");

                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void closeEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(true);
        openEye.setVisible(true);
        hideEye.setVisible(false);
        txtHidePassWord.setVisible(false);
    }

    @FXML
    void hidePassWordOnAction(KeyEvent event) {
        passWord = txtHidePassWord.getText();
        txtShowPassWord.setText(passWord);

    }

    @FXML
    void openEyeOnClickedAction(MouseEvent event) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
        hideEye.setVisible(true);
        txtHidePassWord.setVisible(true);
    }

    @FXML
    void showPassWordOnAction(KeyEvent event) {
        passWord = txtShowPassWord.getText();
        txtHidePassWord.setText(passWord);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
    }

    public void setButtonClickedSound(String fileName, double speed) {
        try {
            // Load the sound file
            Media sound = new Media(getClass().getResource("/sound/" + fileName).toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setRate(speed); // Set the playback speed
            mediaPlayer.play(); // Play the sound
        } catch (NullPointerException e) {
            System.out.println("Sound file not found: " + fileName);
        }
    }

    public void alert(String setTitle , String content ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(setTitle);
        alert.setHeaderText(null);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        alert.showAndWait();


    }

    @FXML
    public void txtUserNameOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            txtHidePassWord.requestFocus();
        }
    }


}
