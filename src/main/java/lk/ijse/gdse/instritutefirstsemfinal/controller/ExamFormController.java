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
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;

import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ExamFormController implements Initializable {

    private ExamTableFormController examTableFormController;

    ExamModel examModel = new ExamModel();
    GradeModel gradeModel = new GradeModel();
    SubjectModel subjectModel = new SubjectModel();

    public void setExamTableFormController(ExamTableFormController examTableFormController) {
        this.examTableFormController = examTableFormController;
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
    private ComboBox<String> cmbExamType;

    @FXML
    private ComboBox<String> cmbGrade;

    @FXML
    private ComboBox<String> cmbSubject;

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


    String id;
    String subject;
    String type;
    String grade;
    LocalDate date;
    String description;


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        id = lblExamID.getText();
        subject = cmbSubject.getValue();
        type = cmbExamType.getValue();
        grade = cmbGrade.getValue();
        date = dPickerDate.getValue();
        description = tareaBody.getText();



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
        cmbSubject.getItems().clear();

        grade = cmbGrade.getSelectionModel().getSelectedItem();

        String gradeID = gradeModel.getGradeIdFromName(grade);

        ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

        cmbSubject.getItems().addAll(subjects);
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
        refreshPage();

        cmbGrade.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
        });

        cmbSubject.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
        });

        cmbExamType.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
        });

        dPickerDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
        });
    }

    @FXML
    private void tareaBodyOnKeyTyped(KeyEvent keyEvent) {
        description = tareaBody.getText();

        idSaveEnable();
    }


    public void refreshPage(){
        String nextExamID = examModel.getNextExamID();
        lblExamID.setText(nextExamID);
        tareaBody.clear();

        cmbGrade.getSelectionModel().clearSelection();
        cmbSubject.getSelectionModel().clearSelection();
        cmbExamType.getSelectionModel().clearSelection();
        dPickerDate.setValue(null);

        ArrayList<GradeDto> grades = gradeModel.getGrades();
        ArrayList<String> examTypes = new ArrayList<>(Arrays.asList(
                "Midterm Exam",
                "Final Exam",
                "Practical Exam",
                "Monthly Test",
                "Term Test",
                "Unit Test",
                "Surprise Quiz",
                "End of Year Exam",
                "Revision Exam",
                "Project Presentation",
                "Online Assessment",
                "Extra-Curricular Test",
                "Supplementary Exam",
                "Mock Exam",
                "None"
        ));
        for (GradeDto grade : grades) {
            cmbGrade.getItems().add(grade.getGradeName());
        }

        for (String examType : examTypes) {
            cmbExamType.getItems().add(examType);
        }

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnReset.setDisable(false);
        btnUpdate.setDisable(true);




    }

    public void idSaveEnable(){
        boolean checkGrade = cmbGrade.getValue() == null;
        boolean checkSubject = cmbSubject.getValue() == null;
        boolean checkExamType = cmbExamType.getValue() == null;
        boolean checkDate = dPickerDate.getValue() == null;
        boolean checkDescription =  tareaBody != null &&tareaBody.getText().isEmpty();

        btnSave.setDisable(checkGrade || checkSubject || checkExamType || checkDate || checkDescription);
    }


}
