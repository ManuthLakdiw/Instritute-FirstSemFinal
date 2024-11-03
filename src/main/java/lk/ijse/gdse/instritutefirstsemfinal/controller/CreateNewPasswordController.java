package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswordController implements Initializable {

    private final UserModel user = new UserModel();

    String newPassword;
    String confirmPassword;

    @FXML
    private Label lblPasswordStatus;

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


    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";

    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";

    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";





    @FXML
    void btnResetPasswordOnClicked(ActionEvent event) {
        boolean newPasswordEmpty = txtNewShowPassWord.getText().isEmpty() && txtNewHidePassWord.getText().isEmpty();
        boolean confirmPasswordEmpty = txtConfirmShowPassWord.getText().isEmpty() && txtConfirmHidePassWord.getText().isEmpty();

        resetStyle(txtNewHidePassWord,txtConfirmHidePassWord,txtConfirmShowPassWord,txtNewShowPassWord);


        if (newPasswordEmpty && confirmPasswordEmpty) {
            alert("Reset Password", "Please fill in both New Password and Confirm Password fields!");
            errorStyle(txtNewShowPassWord, txtNewHidePassWord, txtConfirmShowPassWord, txtConfirmHidePassWord);

        }
        else if (newPasswordEmpty) {
            alert("Reset Password", "Please fill in the New Password field!");
            errorStyle(txtNewShowPassWord, txtNewHidePassWord);

        }

        else if (confirmPasswordEmpty) {
            alert("Reset Password", "Please fill in the Confirm Password field!");
            errorStyle(txtConfirmShowPassWord, txtConfirmHidePassWord);

        }

        else {
            newPassword = txtNewShowPassWord.isVisible() ? txtNewShowPassWord.getText() : txtNewHidePassWord.getText();
            confirmPassword = txtConfirmShowPassWord.isVisible() ? txtConfirmShowPassWord.getText() : txtConfirmHidePassWord.getText();


            if (newPassword.equals(confirmPassword)) {
                boolean isUpdate = user.updatePasswordUser(newPassword, ForgotPasswordFormController.gmail);

                if (isUpdate) {
                    try {
                        createNewPasswordFormPane.getChildren().clear();
                        Pane pane = FXMLLoader.load(getClass().getResource("/view/pwResetSuccessForm.fxml"));
                        createNewPasswordFormPane.getChildren().add(pane);
                        Stage stage = (Stage) createNewPasswordFormPane.getScene().getWindow();
                        stage.setTitle("Changed Password");
                    } catch (IOException e) {
                        e.printStackTrace();
                        showErrorAlert("Error", "Failed to load SuccessForm!");
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Reset!");
                alert.setHeaderText(null);
                alert.setContentText("Passwords doesn't match!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                alert.show();
            }
        }
    }


    @FXML
    public void confirmPasswordHideEye1nClicked(MouseEvent mouseEvent) {
        txtConfirmHidePassWord.setVisible(false);
        hideEye1.setVisible(false);
        openEye1.setVisible(true);
        txtConfirmShowPassWord.setVisible(true);
        txtConfirmShowPassWord.requestFocus();
        txtConfirmShowPassWord.positionCaret(txtConfirmShowPassWord.getText().length());

//        txtConfirmShowPassWord.setText(txtConfirmHidePassWord.getText());


    }


    @FXML
    void newPasswordHideEyeOnClicked(MouseEvent event) {
        txtNewHidePassWord.setVisible(false);
        hideEye.setVisible(false);
        openEye.setVisible(true);
        txtNewShowPassWord.setVisible(true);
        txtNewShowPassWord.requestFocus();
        txtNewShowPassWord.positionCaret(txtNewShowPassWord.getText().length());

//        txtNewShowPassWord.setText(txtNewHidePassWord.getText());

    }

    @FXML
    void newPasswordOpenEyeOnClicked(MouseEvent event) {
        txtNewHidePassWord.setVisible(true);
        hideEye.setVisible(true);
        txtNewShowPassWord.setVisible(false);
        openEye.setVisible(false);
        txtNewHidePassWord.requestFocus();
        // Move the cursor to the end of the text
        txtNewHidePassWord.positionCaret(txtNewHidePassWord.getText().length());

//        Move the cursor to the second position (index 1)
//        txtNewHidePassWord.positionCaret(1);

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
        txtConfirmHidePassWord.requestFocus();
        txtConfirmHidePassWord.positionCaret(txtConfirmHidePassWord.getText().length());

//        txtConfirmHidePassWord.setText(txtConfirmShowPassWord.getText());
    }

    public void errorStyle(TextField... fields){
        String errorStyle = "-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (TextField field : fields) {
            field.setStyle(errorStyle);
        }

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

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void resetStyle(TextField... fields) {
        String defaultStyle = "-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (TextField field : fields) {
            field.setStyle(defaultStyle);
        }
    }


    @FXML
    public void txtNewShowPassWordOnKeyType(KeyEvent keyEvent) {
        String password = txtNewShowPassWord.getText();
        txtNewHidePassWord.setText(password);


        if (password.matches(passwordStrongRegex)) {
            lblPasswordStatus.setText("Strong ✔︎");
            lblPasswordStatus.setTextFill(Color.GREEN);
        } else if (password.matches(passwordMediumRegex)) {
            lblPasswordStatus.setText("Medium ⚠︎");
            lblPasswordStatus.setTextFill(Color.BROWN);
        } else if (password.matches(passwordWeakRegex)) {
            lblPasswordStatus.setText("Weak ⛔︎");
            lblPasswordStatus.setTextFill(Color.RED);
        } else if (txtNewShowPassWord.getText().isEmpty()) {
            lblPasswordStatus.setText("");

        }
    }


    @FXML
    public void txtNewHidePassWordOnKeyType(KeyEvent keyEvent) {
        txtNewShowPassWord.setText(txtNewHidePassWord.getText());
    }

    @FXML
    public void txtConfirmShowPassWordOnKeyType(KeyEvent keyEvent) {
        txtConfirmHidePassWord.setText(txtConfirmShowPassWord.getText());
    }


    @FXML
    public void txtConfirmHidePassWordOnKeyType(KeyEvent keyEvent) {
        txtConfirmShowPassWord.setText(txtConfirmHidePassWord.getText());
    }


}

