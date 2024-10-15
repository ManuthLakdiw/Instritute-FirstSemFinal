package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordConfirmFormController implements Initializable {

    @FXML
    private Button btnContinue;

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


    @FXML
    void btnContinueOnAction(ActionEvent event) {
        try {
            resetPasswordFormPane.getChildren().clear();
            Pane load = FXMLLoader.load(getClass().getResource("/view/createNewPasswordForm.fxml"));
            resetPasswordFormPane.getChildren().add(load);
            Stage stage = (Stage) resetPasswordFormPane.getScene().getWindow();
            stage.setTitle("Create new Password");

        }catch (IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load Create new Password!");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
            alert.showAndWait();
        }




    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblsendEmail.setText("we sent a code to "+ForgotPasswordFormController.gmail);
    }
}
