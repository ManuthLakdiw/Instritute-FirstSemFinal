package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.TeacherTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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


    TeacherTm isSelected;




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
    void btnDeleteOnClicked(ActionEvent event) {
        RegexUtil.resetStyle(txtName,txtContactNumber,txtEmailAddress);
        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure delete this Teacher?", ButtonType.NO, ButtonType.YES);
        successAlert.setTitle("Confirmation");
        successAlert.setHeaderText(null);
        successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        Optional<ButtonType> buttonType = successAlert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
//            btnDelete.setDisable(false);
//            btnReset.setDisable(false);
//            btnSave.setVisible(true);
            boolean isDeleted = teacherModel.deleteTeacher(lblTeacherID.getText());

            if (isDeleted) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher deleted successfully");
                refreshPage();
            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher could not be deleted!");
            }
        }

    }

    @FXML
    void btnUpdateOnClicked(ActionEvent event) {
        RegexUtil.resetStyle(txtName,txtContactNumber,txtEmailAddress);
        btnUpdate.setDisable(false);

        if (!btnUpdate.isDisable()) {
            id = lblTeacherID.getText();
            title = cmbTitle.getValue();
            name = txtName.getText();
            contactNo = txtContactNumber.getText();
            email = txtEmailAddress.getText();

            boolean check = true;

            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(txtContactNumber);
            fields.add(txtEmailAddress);
            fields.add(txtName);

            if (cmbTitle.getValue() == null) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Please select a title.");
                cmbTitle.show();
                return;
            }

            for (TextField field : fields) {
                if (field.getText().isEmpty()) {
                    AlertUtil.informationAlert(UserFormController.class, null, true, "Fields cannot be empty. Please fill in all fields.");
                    RegexUtil.setErrorStyle(true, field);
                    return;
                }
            }


            if (!contactNo.matches(phoneNumberRegex)) {
                RegexUtil.setErrorStyle(true,txtContactNumber);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Phone number format is incorrect. It must start with '0' and be 10 digits.");
                return;
            }


            if (!email.matches(emailRegex)) {
                RegexUtil.setErrorStyle(true,txtEmailAddress);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Email format is incorrect. Please provide a valid email.");
                return;
            }



            TeacherDto existingTeacher = teacherModel.getTeacherByID(id);


            if (existingTeacher != null) {

                if (id.equals(existingTeacher.getTeacherId()) &&
                        title.equals(existingTeacher.getTitle()) &&
                        name.equals(existingTeacher.getName()) &&
                        contactNo.equals(existingTeacher.getPhoneNumber()) &&
                        email.equals(existingTeacher.getEmail())) {
                    check = false;
                    AlertUtil.informationAlert(UserFormController.class, null, false, "No changes detected. Update is not necessary.");
                } else {
                    boolean isUpdate = teacherModel.updateTeacher(new TeacherDto(id,title, name, contactNo, email));

                    if (isUpdate) {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Teacher : " + id + " updated successfully");
                        btnUpdate.setDisable(false);
                        btnSave.setVisible(true);
                    } else {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Teacher : " + id + " update failed!");
                    }
                }
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Teacher not found!");
            }

            if (check) {
                refreshPage();
            }
        }

    }

    @FXML
    void btnSendMailToTeacherOnClicked(ActionEvent event) throws IOException {
        if (isSelected == null) {
            AlertUtil.informationAlert(TeacherFormController.class, null, true, "Please choose a teacher!");
        } else {
            String checkEmail = txtEmailAddress.getText();
            ArrayList<TeacherDto> teachers = teacherModel.getAllTeachers();
            boolean emailFound = false;


            for (TeacherDto teacherDto : teachers) {
                if (teacherDto.getEmail().equals(checkEmail)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendMailToTeacherForm.fxml"));
                    Parent load = loader.load();

                    SendMailToTeacherFormController controller = loader.getController();
                    controller.setTeacherEmail(checkEmail);


                    Stage stage = new Stage();
                    stage.setTitle("Send email");
                    stage.setScene(new Scene(load));

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btnSendMailToTeacher.getScene().getWindow());
                    stage.showAndWait();

                    emailFound = true;
                }
            }
            if (!emailFound) {
                AlertUtil.informationAlert(TeacherFormController.class, null, false, checkEmail + " isn't in the database!\nFirst You should update the teacher ["+lblTeacherID.getText()+"]");
            }
        }
    }

    @FXML
    private void tblTeacherOnAction(MouseEvent mouseEvent) {
        btnSave.setVisible(false);
        isSelected = tblTeacher.getSelectionModel().getSelectedItem();
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

    @FXML
    private void btnSaveOnClicked(ActionEvent actionEvent) {
        id = lblTeacherID.getText();
        title = cmbTitle.getValue();

        if (!btnSave.isDisable()) {
            TeacherDto teacherDto = new TeacherDto(id,title,name,contactNo,email);

            boolean isSaved = teacherModel.saveTeacher(teacherDto);

            if (isSaved) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher Saved Successfully");
                refreshPage();
            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Teacher   Saved Failed!");
            }
        }else {
            System.out.println("save button is disabled");
        }

    }

    @FXML
    private void btnResetOnClicked(ActionEvent actionEvent) {
        refreshPage();
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
        if (txtEmailAddress.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                txtContactNumber.requestFocus();
                txtEmailAddress.positionCaret(txtEmailAddress.getText().length());
            }
        }

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
        btnSave.setVisible(true);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
        btnDelete.setDisable(true);
        btnSendMailToTeacher.setDisable(true);
        btnUpdate.setDisable(true);


        txtName.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");

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
        boolean isNameValid = txtName != null && !txtName.getText().isEmpty() && txtName.getText().matches(nameRegex);
        boolean isContactNumberValid = txtContactNumber != null && !txtContactNumber.getText().isEmpty() && txtContactNumber.getText().matches(phoneNumberRegex);
        boolean isEmailValid = txtEmailAddress != null && !txtEmailAddress.getText().isEmpty() && txtEmailAddress.getText().matches(emailRegex);

        btnSave.setDisable(!(isTitleValid && isNameValid && isContactNumberValid && isEmailValid));

    }

    private void checkFieldsEmpty() {
        boolean isFilled = (cmbTitle.getValue() != null && !cmbTitle.getValue().isEmpty()) ||
                (txtName.getText() != null && !txtName.getText().isEmpty()) ||
                (txtContactNumber.getText() != null && !txtContactNumber.getText().isEmpty()) ||
                (txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty());

        btnReset.setDisable(!isFilled);
    }


}




