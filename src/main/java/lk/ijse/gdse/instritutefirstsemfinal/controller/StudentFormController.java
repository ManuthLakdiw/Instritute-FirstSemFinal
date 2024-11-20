package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

public class StudentFormController {

    private StudentTableFormController studentTableFormController;

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
    private CheckComboBox<?> checkCBoxSubject;

    @FXML
    private JFXComboBox<?> cmbGrade;

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

}
