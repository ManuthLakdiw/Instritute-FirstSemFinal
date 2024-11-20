package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    private StudentTableFormController studentTableFormController;
    StudentModel studentModel = new StudentModel();
    GradeModel gradeModel = new GradeModel();

    public void setStudentTableFormController(StudentTableFormController studentTableFormController) {
        this.studentTableFormController = studentTableFormController;
    }

    @FXML
    private Pane StudentPane;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckComboBox<String> checkCBoxSubject;

    @FXML
    private JFXComboBox<String> cmbGrade;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDOB;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFee;

    @FXML
    private Label lblName;

    @FXML
    private Label lblParentName;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label lblStudentID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtParentName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void checkCBoxSubjectOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void cmbGradeOnAction(ActionEvent event) {
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();
        if (selectedGrade != null && !selectedGrade.isEmpty()) {
            // Enable the reset button
            btnReset.setDisable(false);

            // Clear previous subjects
            checkCBoxSubject.getItems().clear();
            String gradeID = gradeModel.getGradeIdFromName(selectedGrade);
            ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

            if (subjects != null) {
                checkCBoxSubject.getItems().addAll(subjects);
            }
        } else {
            // Disable the reset button if no grade is selected
            btnReset.setDisable(true);
        }
    }


    @FXML
    void dpDOBOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void dpDOBOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtAddressOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtAddressOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtEmailOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtEmailOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtFeeOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtFeeOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtParentNameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtParentNameOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtPhoneNumberOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtPhoneNumberOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtUserNameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtUserNameOnkeyTyped(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
    }

    public void refreshPage(){

        String studentID = studentModel.getNextStudentID();
        lblStudentID.setText(studentID);
        checkCBoxSubject.getItems().clear();

        ArrayList<GradeDto> grades = gradeModel.getGrades();
        cmbGrade.getItems().clear();
        if (grades != null) {
            for (GradeDto grade : grades) {
                String gradeName = grade.getGradeName();
                if (gradeName != null && !gradeName.isEmpty()) {
                    cmbGrade.getItems().add(gradeName);
                }
            }
        }


        ArrayList<Label> labels =   new ArrayList<>(Arrays.asList(lblAddress,lblName,lblDOB,lblFee,lblParentName,lblPhoneNumber,lblEmail));

        for (Label label : labels) {
            label.setText("");
        }

        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
