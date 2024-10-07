package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.model.ForgotPasswordFormModel;

import java.io.IOException;

public class ForgotPasswordFormController {
    static String gmail;

    ForgotPasswordFormModel model = new ForgotPasswordFormModel();

    @FXML
    private Button btnGetdigit;

    @FXML
    private ImageView forgotImage;

    @FXML
    private TextField txtEmail;

    @FXML
    private Pane forgotPasswordFormPane;


    @FXML
    void btnGetDigitOnAction(ActionEvent eve) {
        String email = txtEmail.getText();

        Pair <Boolean,String> check = model.checkGmail(email);
        gmail = check.getValue();


        if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Reset Error!");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a valid email");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
            PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
            delay.setOnFinished(event -> alert.close());
            delay.play();
            alert.show();
        }else {
            if (check.getKey()) {
                try {
                    forgotPasswordFormPane.getChildren().clear();
                    Pane pane = FXMLLoader.load(getClass().getResource("/view/resetPasswordConfirmForm.fxml"));
                    forgotPasswordFormPane.getChildren().add(pane);
                    Stage stage = (Stage) forgotPasswordFormPane.getScene().getWindow();
                    stage.setTitle("Reset Password");
                }catch (IOException e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load ResetPasswordForm!");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    alert.showAndWait();
                }


            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Forgot Password");
                alert.setHeaderText(null);
                alert.setContentText(email+" is not in the dataBase. Try again!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
                delay.setOnFinished(event -> alert.close());
                delay.play();
                alert.show();
            }
        }
    }
}



