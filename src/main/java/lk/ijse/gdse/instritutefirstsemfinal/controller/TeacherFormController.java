package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.TeacherTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherFormController implements Initializable {

    TeacherModel teacherModel = new TeacherModel();

    String id;
    String title;
    String name;
    String contactNo;
    String email;

    private String nameRegex = "^[A-Za-z]+(\\.[A-Za-z]+)*(\\s[A-Za-z]+)*$";
    private String phoneNumberRegex = "^0\\d{9}$";
    private String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" ;;



    @FXML
    private TableColumn<TeacherTm, String> colEmailAddrees;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSendMailToTeacher;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<TeacherTm, String> colContactNumber;

    @FXML
    private TableColumn<TeacherTm, String> colID;

    @FXML
    private TableColumn<TeacherTm, String> colName;

    @FXML
    private TableColumn<TeacherTm, String> colTItle;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label lblTeacherID;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<TeacherTm> tblTeacher;

    @FXML
    private Pane teacherPane;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnClicked(ActionEvent event) {

    }

    @FXML
    void btnSendMailToTeacherOnClicked(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnClicked(ActionEvent event) {

    }

    @FXML
    void cmbTitleOnMouseEntered(MouseEvent event) {
        if (cmbTitle.getValue() == null) {
            lblTitle.setText("Choose a title");
        }

    }

    public void cmbTitleOnMouseExited(MouseEvent mouseEvent) {
        lblTitle.setText(" ");
    }


    public void cmbTitleOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            txtName.requestFocus();
            txtName.positionCaret(txtName.getText().length());

        }
    }

    @FXML
    void txtContactNumberOnKeyPressed(KeyEvent event) {
        if (txtContactNumber.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                txtName.requestFocus();
                txtName.positionCaret(txtName.getText().length());
            }
        }else {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmailAddress.requestFocus();
                txtEmailAddress.positionCaret(txtEmailAddress.getText().length());
            }
        }
    }

    @FXML
    void txtContactNumberOnKeyTyped(KeyEvent event) {
        contactNo = txtContactNumber.getText();
        btnReset.setDisable(false);
        lblPhoneNumber.setStyle("-fx-text-fill: #4a4848;");
        ArrayList<TeacherDto> teacherDtos = teacherModel.getAllTeachers();
        ArrayList<String> teacherContactNumbers = new ArrayList<>();

        for (TeacherDto teacherDto : teacherDtos) {
            teacherContactNumbers.add(teacherDto.getPhoneNumber());
        }

        if (contactNo.isEmpty()) {
            btnReset.setDisable(true);
            lblPhoneNumber.setText("");
            RegexUtil.resetStyle(txtContactNumber);
            checkFieldsEmpty();
            return;
        }

        boolean contactExists = false;
        for (String existingContactNumber : teacherContactNumbers) {
            if (existingContactNumber.equals(contactNo)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber already exists!");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        } else if (contactNo.matches(phoneNumberRegex)) {
            lblPhoneNumber.setText("");
            RegexUtil.resetStyle(txtContactNumber);
        } else if (!contactNo.matches(phoneNumberRegex )){
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber must be 10 numbers and start with \"0\"");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        }
        isSaveEnable();

    }

    @FXML
    void txtEmailAddressOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtEmailAddressOnKeyTyped(KeyEvent event) {
        email = txtEmailAddress.getText();
        btnReset.setDisable(false);
        ArrayList<TeacherDto> users = teacherModel.getAllTeachers();
        ArrayList<String> teacherEmails = new ArrayList<>();

        for (TeacherDto teacherDto : users) {
            teacherEmails.add(teacherDto.getEmail());
        }


        if (email.isEmpty()) {
            btnReset.setDisable(true);
            lblEmail.setText("");
            RegexUtil.resetStyle(txtEmailAddress);
            checkFieldsEmpty();
            return;
        }

        boolean contactExists = false;
        for (String existingEmailAddress : teacherEmails) {
            if (existingEmailAddress.equals(email)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email Address already exists!");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        } else if (email.matches(emailRegex)) {
            lblEmail.setText("");
            RegexUtil.resetStyle(txtEmailAddress);
        } else if (!email.matches(emailRegex)){
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email must start with letters, numbers, or underscores follow '@' ");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        }
        isSaveEnable();


    }

    @FXML
    void txtNameOnKeyPressed(KeyEvent event) {
        if (!txtName.getText().isEmpty()) {
            if (event.getCode() == KeyCode.ENTER) {
                txtContactNumber.requestFocus();
                txtContactNumber.positionCaret(txtContactNumber.getText().length());
            }
        }
    }

    @FXML
    private void txtNameOnKeyTyped(KeyEvent event) {
        name = txtName.getText().trim();
        btnReset.setDisable(false);
        lblName.setStyle("-fx-text-fill: #4a4848");
        RegexUtil.resetStyle(txtName);

        if (name.isEmpty()) {
            btnReset.setDisable(true);
            btnReset.setDisable(true);
            lblName.setText(" ");
            RegexUtil.resetStyle(txtName);
            checkFieldsEmpty();
        }else {
            btnReset.setDisable(false);
            if (!name.matches(nameRegex)) {
                lblName.setStyle("-fx-text-fill: red");
                RegexUtil.setErrorStyle(false,txtName);
                lblName.setText("Enter a valid name: use letters only, with optional dots or spaces");
            }else {
                lblName.setText(" ");
                RegexUtil.resetStyle(txtName);
            }
        }
        isSaveEnable();
    }

    private void refreshPage(){
        RegexUtil.resetStyle(txtContactNumber,txtEmailAddress,txtName);

        String nextTeacherID = teacherModel.getNextTeacherID();
        lblTeacherID.setText(nextTeacherID);
        cmbTitle.setValue(null);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
        btnDelete.setDisable(true);
        btnSendMailToTeacher.setDisable(true);
        btnUpdate.setDisable(true);


        txtName.clear();
        txtContactNumber.clear();
        txtEmailAddress.clear();

        lblEmail.setText("");
        lblName.setText("");
        lblPhoneNumber.setText("");
        lblTitle.setText("");

        loadTeacherTable();



    }

    private void loadTeacherTable(){
        ArrayList<TeacherDto> teacherDtos = teacherModel.getAllTeachers();
        ObservableList<TeacherTm> teacherTms = FXCollections.observableArrayList();

        for (TeacherDto teacherDto : teacherDtos) {
            TeacherTm teacherTm = new TeacherTm(
                    teacherDto.getTeacherId(),
                    teacherDto.getTitle(),
                    teacherDto.getName(),
                    teacherDto.getPhoneNumber(),
                    teacherDto.getEmail()
            );
            teacherTms.add(teacherTm);
        }

        tblTeacher.setItems(teacherTms);
    }

    private void isSaveEnable() {
        boolean isTitleValid = cmbTitle.getValue() != null && !cmbTitle.getValue().isEmpty();
        boolean isNameValid = name != null && !name.isEmpty() && name.matches(nameRegex);
        boolean isContactNumberValid = contactNo != null && !contactNo.isEmpty() && contactNo.matches(phoneNumberRegex);
        boolean isEmailValid = email != null && !email.isEmpty() && email.matches(emailRegex);

        btnSave.setDisable(!(isTitleValid && isNameValid && isContactNumberValid && isEmailValid));

    }

    private void checkFieldsEmpty() {
        boolean isFilled = (cmbTitle.getValue() != null && !cmbTitle.getValue().isEmpty()) ||
                (txtName.getText() != null && !txtName.getText().isEmpty()) ||
                (txtContactNumber.getText() != null && !txtContactNumber.getText().isEmpty()) ||
                (txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty());

        btnReset.setDisable(!isFilled);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.requestFocus();
        cmbTitle.setItems(FXCollections.observableArrayList("Mr","Miss","Mrs"));

        colID.setCellValueFactory(new PropertyValueFactory<>("teacherId")); // මෙහි වෙන්නෙ TeacherTm එකෙන් teacherId අදාල value එක column එකට පාස් කිරීමක්.
        colTItle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmailAddrees.setCellValueFactory(new PropertyValueFactory<>("email"));

        cmbTitle.setOnAction(event -> {
            if (cmbTitle.getValue() != null) {
                btnReset.setDisable(false);
                isSaveEnable();
            }else {
                btnReset.setDisable(true);
            }
        });

        refreshPage();
    }

    @FXML
    private void tblTeacherOnAction(MouseEvent mouseEvent) {
        TeacherTm isSelected = tblTeacher.getSelectionModel().getSelectedItem();
        if (isSelected != null) {
            lblTeacherID.setText(isSelected.getTeacherId());
            cmbTitle.setValue(isSelected.getTitle());
            txtName.setText(isSelected.getName());
            txtContactNumber.setText(isSelected.getPhoneNumber());
            txtEmailAddress.setText(isSelected.getEmail());

            btnSave.setDisable(false);
            btnReset.setDisable(false);
            btnDelete.setDisable(false);
            btnSendMailToTeacher.setDisable(false);
            btnUpdate.setDisable(false);

            RegexUtil.resetStyle(txtName,txtContactNumber,txtEmailAddress);


        }

    }

    public void btnSaveOnClicked(ActionEvent actionEvent) {
        id = lblTeacherID.getText();
        title = cmbTitle.getValue();

        if (!btnSave.isDisable()) {
            TeacherDto teacherDto = new TeacherDto(id,title,name,contactNo,email);

            boolean isSaved = teacherModel.saveTeacher(teacherDto);

            if (isSaved) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher Saved Successfully");
                refreshPage();
            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher Saved Failed!");
            }
        }else {
            System.out.println("save button is disabled");
        }

    }

    public void btnResetOnClicked(ActionEvent actionEvent) {
        refreshPage();
    }
}




