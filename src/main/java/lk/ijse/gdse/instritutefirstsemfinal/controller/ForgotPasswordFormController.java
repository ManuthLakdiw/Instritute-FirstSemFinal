package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.instritutefirstsemfinal.model.ForgotPasswordFormModel;

public class ForgotPasswordFormController {
    ForgotPasswordFormModel model = new ForgotPasswordFormModel();

    @FXML
    private Button btnGetdigit;

    @FXML
    private ImageView forgotImage;

    @FXML
    private TextField txtEmail;

    @FXML
    void btnGetDigitOnAction(ActionEvent event) {
        String email = txtEmail.getText();

        boolean isCheck = model.isCheckGmail(email);

        if (isCheck) {
            alert("Eligible","youare ok");
        }else {
            alert("Eligible","youare not ok");
        }



    }

    public void alert(String setTitle, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(setTitle);
        alert.setHeaderText(null);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        alert.showAndWait();
    }

}
