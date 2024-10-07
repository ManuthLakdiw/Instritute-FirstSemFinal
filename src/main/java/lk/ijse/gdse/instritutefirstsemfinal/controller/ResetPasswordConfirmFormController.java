package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse.instritutefirstsemfinal.model.ForgotPasswordFormModel;
import  lk.ijse.gdse.instritutefirstsemfinal.controller.ForgotPasswordFormController;

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
    void btnContinueOnAction(ActionEvent event) {




    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblsendEmail.setText("we sent a code to "+ForgotPasswordFormController.gmail);
    }
}
