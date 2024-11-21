package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

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

        String gradeID = gradeModel.getGradeIdFromName(grade);
        String subjectID = subjectModel.getSubjectIdFromName(subject);

        if (description.isEmpty()){
            description = "Not Specified Description";
        }

        if (type.equalsIgnoreCase("None")){
            type = "Not Specified Type";
        }
        if (!btnSave.isDisable()){
            ExamDto examDto = new ExamDto(
                    id,
                    gradeID,
                    subjectID,
                    date,
                    type,
                    description
            );


            boolean isSaved = examModel.saveExam(examDto);


            if (isSaved){
                AlertUtil.informationAlert(this.getClass(),null,true,"Exam saved successfully");
                refreshPage();
                examTableFormController.loadExamTable();

            }else {
                AlertUtil.informationAlert(this.getClass(),null,false,"Error while saving exam");
            }
        }



    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbExamTypeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbExamTypeOnKeyPressed(KeyEvent event) {
        if (cmbExamType.getSelectionModel().getSelectedItem() != null) {
            if (event.getCode() == KeyCode.ENTER) {
                if (cmbSubject.getSelectionModel().getSelectedItem() == null) {
                    cmbSubject.show();
                }
            }
        }

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
        if (cmbGrade.getSelectionModel().getSelectedItem() != null) {
            if (event.getCode() == KeyCode.ENTER) {
                if (cmbExamType.getSelectionModel().getSelectedItem() == null) {
                    cmbExamType.show();
                }
            }
        }

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
            isResetEnable();
        });

        cmbSubject.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
            isResetEnable();

        });

        cmbExamType.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
            isResetEnable();

        });

        dPickerDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            idSaveEnable();
            isResetEnable();

        });
    }

    @FXML
    private void tareaBodyOnKeyTyped(KeyEvent keyEvent) {
        description = tareaBody.getText();
        idSaveEnable();
        isResetEnable();
    }


    public void refreshPage(){
        btnSave.setVisible(true);
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
        btnReset.setDisable(true);
        btnUpdate.setDisable(true);




    }

    public void idSaveEnable(){
        boolean checkGrade = cmbGrade.getValue() == null;
        boolean checkSubject = cmbSubject.getValue() == null;
        boolean checkExamType = cmbExamType.getValue() == null;
        boolean checkDate = dPickerDate.getValue() == null;


        btnSave.setDisable(checkGrade || checkSubject || checkExamType || checkDate);
    }

    public void isResetEnable(){
        boolean isCheckDescription = tareaBody != null && !tareaBody.getText().isEmpty();
        boolean isCheckGrade = cmbGrade != null && cmbGrade.getValue() != null;
        boolean isCheckSubject = cmbSubject != null && cmbSubject.getValue() != null;
        boolean isCheckExamType = cmbExamType != null && cmbExamType.getValue() != null;
        boolean isCheckDate = dPickerDate != null &&dPickerDate.getValue() != null;

        btnReset.setDisable(!(isCheckGrade || isCheckSubject || isCheckExamType || isCheckDescription || isCheckDate));

    }


    public void setDto(ExamDto examDto) {
        lblExamID.setText(examDto.getExamId());
        tareaBody.setText(examDto.getExamDescription());
        dPickerDate.setValue(examDto.getExamDate());
        cmbExamType.getSelectionModel().select(examDto.getExamType().equals("Not Specified Type") ? "None" : examDto.getExamType());
        cmbGrade.getSelectionModel().select(examDto.getGrade());
        String gradeID = gradeModel.getGradeIdFromName(examDto.getGrade());
        cmbSubject.getItems().clear();
        ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

        cmbSubject.getItems().addAll(subjects);
        cmbSubject.getSelectionModel().select(examDto.getSubject());

    }

    public void buttonAction(){
        btnSave.setVisible(false);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

    }
}
