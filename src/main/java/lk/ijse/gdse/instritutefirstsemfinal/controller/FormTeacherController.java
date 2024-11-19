package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormTeacherController implements Initializable {

    TeacherModel teacherModel = new TeacherModel();
    SubjectModel subjectModel = new SubjectModel();
    private TableTeacherFormController tableTeacherFormController;

    public void setTableTeacherFormController(TableTeacherFormController tableTeacherFormController) {
        this.tableTeacherFormController = tableTeacherFormController;
    }

    String id;
    String title;
    String name;
    String contactNo;
    String email;

    private String nameRegex = "^[A-Za-z]+(\\.[A-Za-z]+)*(\\s[A-Za-z]+)*$";
    private String phoneNumberRegex = "^0\\d{9}$";
    private String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblGrades;

    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private CheckTreeView<String> treeViewSUbAndGrades;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label lblTeacherID;

    @FXML
    private Pane teacherPane;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        cmbSubject.getSelectionModel().clearSelection();
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        // Collect teacher details from the form
        String teacherId = lblTeacherID.getText();
        String name = txtName.getText();
        String phoneNumber = txtContactNumber.getText();
        String email = txtEmailAddress.getText();
        String subjectName = cmbSubject.getSelectionModel().getSelectedItem();

        // Collect grades from the tree view
        List<String> selectedGrades = new ArrayList<>();
        TreeItem<String> root = treeViewSUbAndGrades.getRoot(); // TreeItem<String> instead of CheckTreeItem for root

        if (root != null) {
            // Traverse the children of the root to check selected grades
            for (TreeItem<String> gradeItem : root.getChildren()) {
                if (gradeItem instanceof CheckBoxTreeItem) {
                    CheckBoxTreeItem<String> checkTreeItem = (CheckBoxTreeItem<String>) gradeItem;
                    if (checkTreeItem.isSelected()) {
                        selectedGrades.add(checkTreeItem.getValue()); // Add selected grade
                    }
                }
            }
        }

//        // Validate input
//        if (teacherId.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || subjectName == null || selectedGrades.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Please fill all fields and select at least one grade!").show();
//            return;
//        }

        // Create TeacherDto object
        TeacherDto teacherDto = new TeacherDto(teacherId, name, phoneNumber, email, subjectName);

        // Save teacher details
        boolean isSaved = teacherModel.saveTeacher(teacherDto, selectedGrades);

        if (isSaved) {
            AlertUtil.informationAlert(this.getClass(),null,true,"Teacher Saved Successfully");
            refreshPage();
            tableTeacherFormController.loadTeacherTable();// Refresh the form
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save teacher. Please try again!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {


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
        } else if (!contactNo.matches(phoneNumberRegex)) {
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber must be 10 numbers and start with \"0\"");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        }
        isSaveEnable();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get all subjects from the model
        txtName.textProperty().addListener((observable, oldValue, newValue) -> isSaveEnable());
        txtContactNumber.textProperty().addListener((observable, oldValue, newValue) -> isSaveEnable());
        txtEmailAddress.textProperty().addListener((observable, oldValue, newValue) -> isSaveEnable());

        // Add listener to ComboBox
        cmbSubject.valueProperty().addListener((observable, oldValue, newValue) -> isSaveEnable());

        // Ensure TreeView items are dynamically validated when a root is set
        treeViewSUbAndGrades.rootProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (TreeItem<String> gradeItem : newValue.getChildren()) {
                    CheckBoxTreeItem<String> checkBoxItem = (CheckBoxTreeItem<String>) gradeItem;
                    checkBoxItem.selectedProperty().addListener((obs, wasSelected, isSelected) -> isSaveEnable());
                }
            }
        });

        // Call the refresh method
        refreshPage();
    }


    private void refreshPage() {
        cmbSubject.getItems().clear();
        ArrayList<SubjectDto> subjectInformations = subjectModel.getAllSubjects();

        // Add subjects to the ComboBox
        for (SubjectDto subjectDto : subjectInformations) {
            cmbSubject.getItems().add(subjectDto.getSubjectName());
        }

        String nextTeacherID = teacherModel.getNextTeacherID();
        lblTeacherID.setText(nextTeacherID);

        RegexUtil.resetStyle(txtContactNumber, txtEmailAddress, txtName);
        treeViewSUbAndGrades.setRoot(null);

        btnSave.setVisible(true);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtName.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        lblEmail.setText("");
        lblName.setText("");
        lblPhoneNumber.setText("");
        lblGrades.setText("");
    }

    private void isSaveEnable() {
        boolean isTitleValid = cmbSubject.getValue() != null && !cmbSubject.getValue().isEmpty();
        boolean isNameValid = txtName != null && !txtName.getText().isEmpty() && txtName.getText().matches(nameRegex);
        boolean isContactNumberValid = txtContactNumber != null && !txtContactNumber.getText().isEmpty() && txtContactNumber.getText().matches(phoneNumberRegex);
        boolean isEmailValid = txtEmailAddress != null && !txtEmailAddress.getText().isEmpty() && txtEmailAddress.getText().matches(emailRegex);

        // Check if at least one grade is selected in TreeView
        boolean isGradeSelected = false;
        if (treeViewSUbAndGrades != null && treeViewSUbAndGrades.getRoot() != null) {
            for (TreeItem<String> gradeItem : treeViewSUbAndGrades.getRoot().getChildren()) {
                CheckBoxTreeItem<String> checkBoxItem = (CheckBoxTreeItem<String>) gradeItem;
                if (checkBoxItem.isSelected()) {
                    isGradeSelected = true;
                    break; // No need to check further once we find a selected grade
                }
            }
        }

        // Enable/Disable the Save button based on the validation results
        btnSave.setDisable(!(isTitleValid && isNameValid && isContactNumberValid && isEmailValid && isGradeSelected));
    }


    private void checkFieldsEmpty() {
        // Check if the combo box, name, contact, email, or any grade is filled/selected
        boolean isFilled = (cmbSubject.getValue() != null && !cmbSubject.getValue().isEmpty()) ||
                (txtName.getText() != null && !txtName.getText().isEmpty()) ||
                (txtContactNumber.getText() != null && !txtContactNumber.getText().isEmpty()) ||
                (txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty());

        // Check if at least one grade is selected
        boolean isGradeSelected = false;
        if (treeViewSUbAndGrades != null && treeViewSUbAndGrades.getRoot() != null) {
            for (TreeItem<String> gradeItem : treeViewSUbAndGrades.getRoot().getChildren()) {
                CheckBoxTreeItem<String> checkBoxItem = (CheckBoxTreeItem<String>) gradeItem;
                if (checkBoxItem.isSelected()) {
                    isGradeSelected = true;
                    break; // No need to check further once we find a selected grade
                }
            }
        }

        // Enable/Disable the Reset button based on whether any field is filled or a grade is selected
        btnReset.setDisable(!(isFilled || isGradeSelected));
    }

    @FXML
    private void cmbSubjectOnAction(ActionEvent actionEvent) {
        String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();

        // If no subject is selected, disable the TreeView
        if (selectedSubject == null) {
            treeViewSUbAndGrades.setOpacity(0.5);
            return;
        }
        checkFieldsEmpty();
        treeViewSUbAndGrades.setOpacity(1);
        ArrayList<SubjectDto> allSubjects = subjectModel.getAllSubjects();
        isSaveEnable();

        for (SubjectDto subjectDto : allSubjects) {
            if (subjectDto.getSubjectName().equals(selectedSubject)) {
                String[] grades = subjectDto.getSubjectGrades();

                // Create the root item for the TreeView with the selected subject
                CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>(selectedSubject);
                rootItem.setExpanded(true);

                // Create grade items and add them to the root
                for (String grade : grades) {
                    CheckBoxTreeItem<String> gradeItem = new CheckBoxTreeItem<>(grade);
                    rootItem.getChildren().add(gradeItem);
                }

                // Set the root of the TreeView
                treeViewSUbAndGrades.setRoot(rootItem);
                return; // Exit once the subject is found and TreeView is updated
            }
        }
        isSaveEnable();
    }


    public void txtEmailAddressOnKeyPressed(KeyEvent keyEvent) {
        if (txtEmailAddress.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                txtContactNumber.requestFocus();
                txtEmailAddress.positionCaret(txtEmailAddress.getText().length());
            }
        }else {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cmbSubject.show();
            }
        }
    }

    public void txtEmailAddressOnKeyTyped(KeyEvent keyEvent) {
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

    public void txtNameOnKeyPressed(KeyEvent keyEvent) {
        if (!txtName.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtContactNumber.requestFocus();
                txtContactNumber.positionCaret(txtContactNumber.getText().length());
            }
        }
    }

    public void txtNameOnKeyTyped(KeyEvent keyEvent) {
        name = txtName.getText().trim();
        btnReset.setDisable(false);
        lblName.setStyle("-fx-text-fill: #4a4848");
        RegexUtil.resetStyle(txtName);

            if (name.isEmpty()) {
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
               RegexUtil.resetStyle(txtName);}
            }
       isSaveEnable();

    }



    public void treeViewSUbAndGradesMouseEntered(MouseEvent mouseEvent) {
        if (cmbSubject.getSelectionModel().getSelectedItem() == null) {
            lblGrades.setText("First you choose a Subject");
        }
    }

    public void treeViewSUbAndGradesMouseExited(MouseEvent mouseEvent) {
        lblGrades.setText("");
    }
}