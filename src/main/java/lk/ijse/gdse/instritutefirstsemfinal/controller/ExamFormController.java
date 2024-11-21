package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamFormController implements Initializable {

    private ExamTableFormController examTableFormController;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbExamType;

    @FXML
    private ComboBox<?> cmbGrade;

    @FXML
    private ComboBox<?> cmbSubject;

    @FXML
    private DatePicker dPickerDate;

    @FXML
    private Label lblExamID;

    @FXML
    private Label lblGrades;

    @FXML
    private JFXTextArea tareaBody;

    @FXML
    private Pane teacherPane;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbExamTypeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbExamTypeOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void cmbGradeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbGradeOnKeyPresssed(KeyEvent event) {

    }

    @FXML
    void cmbSubjectOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSubjectOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void dPickerDateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setExamTableFormController(ExamTableFormController examTableFormController) {
        this.examTableFormController = examTableFormController;
    }
}
