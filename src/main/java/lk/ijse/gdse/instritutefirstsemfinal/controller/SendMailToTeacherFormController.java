package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.Setter;

public class SendMailToTeacherFormController {

    @FXML
    private Button btnSend;

    @FXML
    private Label lblClear;

    @FXML
    private JFXTextArea tareaBody;

    @FXML
    private Pane teacherMailPane;

    @FXML
    private TextField txtSubject;

    @Setter
    private String teacherEmail;

    @FXML
    void btnSendOnClicked(ActionEvent event) {

    }

    @FXML
    void lblClearOnClicked(MouseEvent event) {

    }

}
