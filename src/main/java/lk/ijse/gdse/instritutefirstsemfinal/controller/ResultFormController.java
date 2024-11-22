package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.ResultModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ResultFormController implements Initializable {

    ExamModel examModel = new ExamModel();
    ResultModel resultModel = new ResultModel();
    SubjectModel subjectModel = new SubjectModel();
    GradeModel gradeModel = new GradeModel();

    String marksRegex = "^(100|[1-9]?[0-9])$";

    String resultId;
    int marks;
    String grade;
    String subject;
    String student;
    String examId;


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
    private ComboBox<String> cmbGrade;



    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private JFXRadioButton radioBtnNotPArticipant;

    @FXML
    private ComboBox<String> cmbExamID;


    @FXML
    private Label lblResultID;

    @FXML
    private Label lblExamIdDesc;

    @FXML
    private ComboBox<String> cmbStudent;

    @FXML
    private Pane resultPane;

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
    void radioBtnNotPArticipantOnAction(ActionEvent event) {

        if (radioBtnNotPArticipant.isSelected()) {

            txtMarks.setDisable(true);
        } else {
            txtMarks.setDisable(false);
        }
    }


    @FXML
    void cmbGradeOnKeyPresssed(KeyEvent event) {

    }



    @FXML
    void cmbSubjectTypeOnKeyPressed(KeyEvent event) {

    }

    @FXML
    private void cmbGradeOnAction(ActionEvent event) {
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbSubject.getItems().clear();
        cmbExamID.getItems().clear();
        cmbStudent.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedGrade != null) {
            String gradeId = gradeModel.getGradeIdFromName(selectedGrade);

            String[] subjectIDs = resultModel.getExamSubjectsByGrade(gradeId);
            Set<String> subjectNamesSet = new HashSet<>();

            for (String subjectID : subjectIDs) {
                String subjectName = subjectModel.getSubjectNameFromId(subjectID);
                if (subjectName != null) {
                    subjectNamesSet.add(subjectName);
                }
            }

            cmbSubject.getItems().addAll(subjectNamesSet);

            if (!subjectNamesSet.isEmpty()) {
                String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
                if (selectedSubject != null) {
                    String subjectId = subjectModel.getSubjectIdFromName(selectedSubject);
                    ArrayList<String> studentNames = resultModel.getStudentsByGradeAndSubject(gradeId, subjectId);
                    if (studentNames != null && !studentNames.isEmpty()) {
                        cmbStudent.getItems().addAll(studentNames);
                    }
                }
            }
        }
    }


    @FXML
    void cmbSubjectOnAction(ActionEvent event) {
        String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbStudent.getItems().clear();
        cmbExamID.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedSubject != null && selectedGrade != null) {
            String subjectId = subjectModel.getSubjectIdFromName(selectedSubject);
            String gradeId = gradeModel.getGradeIdFromName(selectedGrade);

            ArrayList<String> studentNames = resultModel.getStudentsByGradeAndSubject(gradeId, subjectId);
            if (studentNames != null && !studentNames.isEmpty()) {
                cmbStudent.getItems().addAll(studentNames);
            }

            String[] examIDs = examModel.getExamIDsfromSubject(subjectId);
            if (examIDs != null && examIDs.length > 0) {
                cmbExamID.getItems().addAll(examIDs);
            } else {
                cmbExamID.setPromptText("No exams available");
            }
        } else {
            System.out.println("Selected Subject or Grade is null");
        }
    }


    public void cmbExamIDOnAction(ActionEvent actionEvent) {
        String selectedExamID = cmbExamID.getSelectionModel().getSelectedItem();

        if (selectedExamID != null) {
            ArrayList<ExamDto> examDtos = examModel.getAllExams();

            for (ExamDto examDto : examDtos) {
                if (examDto.getExamId().equals(selectedExamID)) {
                    lblExamIdDesc.setText(examDto.getExamDate() + "    " + examDto.getExamDescription());
                    break;
                }
            }
        }
    }


    public void cmbExamIDOnKeyPressed(KeyEvent keyEvent) {

    }


    @FXML
    void txtMarksOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtMarksOnKeyTyped(KeyEvent event) {


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ExamDto> examDtos = examModel.getAllExams();
        HashSet<String> uniqueGrades = new HashSet<>();

        for (ExamDto examDto : examDtos) {
            uniqueGrades.add(examDto.getGrade());
        }
        cmbGrade.getItems().addAll(uniqueGrades);
    }


    public void cmbStudentOnAction(ActionEvent actionEvent) {
    }

    public void cmbStudentOnActionOnKeyPressed(KeyEvent keyEvent) {
    }
}
