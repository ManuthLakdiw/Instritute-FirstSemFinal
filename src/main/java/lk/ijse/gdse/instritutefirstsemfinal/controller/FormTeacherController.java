package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormTeacherController implements Initializable {

    TeacherModel teacherModel = new TeacherModel();
    SubjectDto subjectDto = new SubjectDto();
    GradeModel gradeModel = new GradeModel();
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
    private String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" ;;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckComboBox<String> checkComboBoxSubject;



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
        try {
            // Clear the TreeView root
            treeViewSUbAndGrades.setRoot(null);

            // Create the root item for TreeView
            TreeItem<String> rootItem = new TreeItem<>("Subjects");
            rootItem.setExpanded(true);  // Make the root expanded by default

            // Get the selected subjects from CheckComboBox
            List<String> selectedSubjects = checkComboBoxSubject.getCheckModel().getCheckedItems();

            // For each selected subject, get its grades and create TreeItems
            for (String subjectName : selectedSubjects) {
                // Get grades for the selected subject
                List<String> grades = gradeModel.getAllSubjects(subjectName);

                // Create a CheckBoxTreeItem for the subject
                CheckBoxTreeItem<String> subjectItem = new CheckBoxTreeItem<>(subjectName);

                // Add grades as child nodes to the subject node
                for (String grade : grades) {
                    // Create a CheckBoxTreeItem for each grade
                    CheckBoxTreeItem<String> gradeItem = new CheckBoxTreeItem<>(grade);
                    subjectItem.getChildren().add(gradeItem);
                }

                // Add a listener to select all children when the root is selected
                subjectItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    // If the subject is selected, select all its children (grades)
                    for (TreeItem<String> child : subjectItem.getChildren()) {
                        CheckBoxTreeItem<String> gradeItem = (CheckBoxTreeItem<String>) child;
                        gradeItem.setSelected(newValue);
                    }
                });

                // Add subject item to the root item
                rootItem.getChildren().add(subjectItem);
            }

            // Set the root for the TreeView
            treeViewSUbAndGrades.setRoot(rootItem);

        } catch (Exception e) {
            e.printStackTrace();
            // Show user-friendly error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong");
            alert.setContentText("An error occurred while loading subjects and grades. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        id = lblTeacherID.getText();
        name = txtName.getText();
        contactNo = txtContactNumber.getText();
        email = txtEmailAddress.getText();
        ObservableList<String> selectedSubject = checkComboBoxSubject.getCheckModel().getCheckedItems();






//        // Proceed with saving if the button is enabled
//        if (!btnSave.isDisable()) {
//            List<String> gradeIds = subjectModel.getGradeIdsFromNames(new ArrayList<>(selectedItems));
//
//            // Validate that gradeIds is not empty
//            if (gradeIds.isEmpty()) {
//                AlertUtil.informationAlert(UserFormController.class, null, true, "Invalid grade selection.");
//                return;  // Exit the method if no valid grade_ids could be found
//            }
//
//            // Create the SubjectDto object with the provided details
//            SubjectDto subjectDto = new SubjectDto(subjectId, subjectName, subjectDescription);
//
//            // Save the subject and its associated grades
//            boolean isSaved = subjectModel.saveSubjectWithGrades(subjectDto, gradeIds);
//
//            // Show an alert based on the result of the save operation
//            if (isSaved) {
//                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject Saved Successfully");
//                refreshPage();  // Refresh the page after successful save
//                tableSubjectFormController.loadSubjectTable();  // Reload the subject table
//            } else {
//                AlertUtil.errorAlert(UserFormController.class, null,  "Subject Save Failed");
//            }
//        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }


    @FXML
    void txtContactNumberOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtContactNumberOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtEmailAddressOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtEmailAddressOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyTyped(KeyEvent event) {

    }

    private void refreshPage() {

        String nextTeacherID = teacherModel.getNextTeacherID();
        lblTeacherID.setText(nextTeacherID);

        RegexUtil.resetStyle(txtContactNumber, txtEmailAddress, txtName);
        checkComboBoxSubject.getCheckModel().clearChecks();
        treeViewSUbAndGrades.setRoot(null);

        btnSave.setVisible(true);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
//        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        txtName.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        lblEmail.setText("");
        lblName.setText("");
        lblPhoneNumber.setText("");
    }


    private void populateSubjectCheckComboBox() {
        try {
            List<SubjectDto> subjects = subjectModel.getAllSubjects();
            List<GradeDto> gradeDto = gradeModel.getGrades();

            for (SubjectDto subject : subjects) {
                checkComboBoxSubject.getItems().add(subject.getSubjectName());
            }



        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.errorAlert(this.getClass(), null, "Failed to load subjects.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
        populateSubjectCheckComboBox();
    }
}
