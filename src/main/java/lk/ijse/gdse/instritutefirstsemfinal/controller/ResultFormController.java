package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.SearchableComboBox;

public class ResultFormController {

    private ResultTableFormController resultTableFormController;
    public void setResultTableFormController(ResultTableFormController resultTableFormController) {
        this.resultTableFormController = resultTableFormController;
    }

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXCheckBox cComboBoxExamAttendance;

    @FXML
    private ComboBox<?> cmbExam;

    @FXML
    private ComboBox<?> cmbSubject;

    @FXML
    private Label lblResultID;

    @FXML
    private Pane resultPane;

    @FXML
    private SearchableComboBox<?> searchableStudentComboBox;

    @FXML
    private TextField txtMarks;

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
    void cComboBoxExamAttendanceOnAction(ActionEvent event) {

    }

    @FXML
    void cmbExamOnKeyPresssed(KeyEvent event) {

    }

    @FXML
    void cmbSubjectOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSubjectTypeOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void cmbcmbExamOnAction(ActionEvent event) {

    }

    @FXML
    void searchableStudentComboBoxOnAction(ActionEvent event) {

    }

    @FXML
    void txtMarksOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtMarksOnKeyTyped(KeyEvent event) {

    }


}
