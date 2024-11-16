package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormSubjectController implements Initializable {

    SubjectModel subjectModel = new SubjectModel();
    GradeModel gradeModel = new GradeModel();
    private TableSubjectFormController tableSubjectFormController;

    public void setTblSubjectFormController(TableSubjectFormController tableSubjectFormController) {
        this.tableSubjectFormController = tableSubjectFormController;
    }


    String subjectId;
    String subjectName;
    String subjectDescription;

    String subjectRegex = "^[A-Za-z][A-Za-z0-9 .,_]*$";


    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckComboBox<String> checkComboBoxGrade;

    @FXML
    private Label lblSubID;

    @FXML
    private Label lblSubNameRegex;

    @FXML
    private Pane subjectPane;

    @FXML
    private JFXTextArea tareaDescription;

    @FXML
    private TextField txtSubName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        RegexUtil.resetStyle(txtSubName);
        Optional<ButtonType> buttonType = AlertUtil.ConfirmationAlert("Are you sure you want to delete this Subject?", ButtonType.NO, ButtonType.YES);
        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDeleted = subjectModel.deleteSubject(lblSubID.getText());

            if (isDeleted) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject deleted successfully.");
                refreshPage();
                tableSubjectFormController.loadSubjectTable();
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject could not be deleted!");
            }
        }
    }


    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        // Get the selected grades from the CheckComboBox
        ObservableList<String> selectedItems = checkComboBoxGrade.getCheckModel().getCheckedItems();

        // Get the subject details from the UI
        subjectId = lblSubID.getText();
        subjectName = txtSubName.getText();
        subjectDescription = tareaDescription.getText();

        // Provide a default description if not specified
        if (subjectDescription == null || subjectDescription.isEmpty()) {
            subjectDescription = "Not specified Description";
        }

        // Validate subjectId and subjectName
        if (subjectId == null || subjectId.isEmpty() || subjectName == null || subjectName.isEmpty()) {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Subject ID or Subject Name cannot be empty.");
            return;  // Exit the method if subjectId or subjectName is empty
        }

        // Validate that at least one grade is selected
        if (selectedItems.isEmpty()) {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Please select at least one grade.");
            return;  // Exit the method if no grades are selected
        }

        // Proceed with saving if the button is enabled
        if (!btnSave.isDisable()) {
            // Get the grade IDs for all selected grades
            List<String> gradeIds = subjectModel.getGradeIdsFromNames(new ArrayList<>(selectedItems));

            // Validate that gradeIds is not empty
            if (gradeIds.isEmpty()) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Invalid grade selection.");
                return;  // Exit the method if no valid grade_ids could be found
            }

            // Create the SubjectDto object with the provided details
            SubjectDto subjectDto = new SubjectDto(subjectId, subjectName, subjectDescription);

            // Save the subject and its associated grades
            boolean isSaved = subjectModel.saveSubjectWithGrades(subjectDto, gradeIds);

            // Show an alert based on the result of the save operation
            if (isSaved) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject Saved Successfully");
                refreshPage();  // Refresh the page after successful save
                tableSubjectFormController.loadSubjectTable();  // Reload the subject table
            } else {
                AlertUtil.errorAlert(UserFormController.class, null,  "Subject Save Failed");
            }
        }
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        RegexUtil.resetStyle(txtSubName);
        btnUpdate.setDisable(false);

        if (!btnUpdate.isDisable()) {
            subjectId = lblSubID.getText();
            subjectName = txtSubName.getText();
            subjectDescription = tareaDescription.getText();

            // Default description if not provided
            if (subjectDescription == null || subjectDescription.isEmpty()) {
                subjectDescription = "Not specified Description";
            }

            // Subject name validation
            if (txtSubName.getText().isEmpty()) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject Name cannot be empty");
                RegexUtil.setErrorStyle(true, txtSubName);
                return;
            }

            if (!subjectName.matches(subjectRegex)) {
                RegexUtil.setErrorStyle(true, txtSubName);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Invalid Subject Name");
                return;
            }

            // Collect selected grade IDs from CheckComboBox (only IDs, not grade details)
            ObservableList<String> selectedItems = checkComboBoxGrade.getCheckModel().getCheckedItems();

            List<String> gradeIds = subjectModel.getGradeIdsFromNames(new ArrayList<>(selectedItems));


            // Create the DTO with updated data
            SubjectDto updatedSubjectDto = new SubjectDto(subjectId, subjectName, subjectDescription);

            // Pass only the grade IDs to update the subject
            boolean isUpdated = subjectModel.updateSubjectWithGrades(updatedSubjectDto, new ArrayList<>(gradeIds));
            System.out.println(isUpdated);

            if (isUpdated) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject updated successfully");
                refreshPage();
                tableSubjectFormController.loadSubjectTable();
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject update failed!");
            }
        } else {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Please fill in the details correctly!");
        }
    }







    @FXML
    void lblSubNameRegexOnKeyTyped(KeyEvent event) {


    }

    @FXML
    void tareaDescriptionOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void tareaDescriptionOnKeyTyped(KeyEvent event) {
        subjectDescription = tareaDescription.getText();
        btnReset.setDisable(false);

        if (subjectDescription.isEmpty()) {
            btnReset.setDisable(true);
            isResetEnable();
        }
        isSaveEnable();

    }


    @FXML
    void txtSubNameOnKeyTyped(KeyEvent event) {
        subjectName = txtSubName.getText();
        btnReset.setDisable(false);
        RegexUtil.resetStyle(txtSubName);

        if (subjectName.isEmpty()) {
            btnReset.setDisable(true);
            RegexUtil.resetStyle(txtSubName);
            lblSubNameRegex.setText("");
            isResetEnable();
        }else {
            btnReset.setDisable(false);
            if (!subjectName.matches(subjectRegex)) {
                lblSubNameRegex.setStyle("-fx-text-fill: red");
                RegexUtil.setErrorStyle(false,txtSubName);
                lblSubNameRegex.setText("Start with a letter, use only letters, numbers, spaces");
            }else {
                lblSubNameRegex.setText(" ");
                RegexUtil.resetStyle(txtSubName);
            }
        }
        isSaveEnable();

    }

    @FXML
    void txtSubNameOnKeyPressed(KeyEvent event) {


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<GradeDto> grades = gradeModel.getGrades();
        ArrayList<String>  items = new ArrayList<>();

        for (GradeDto grade : grades) {
            items.add(grade.getGradeName());
        }
        checkComboBoxGrade.getItems().addAll(items);
        refreshPage();
    }

    public void refreshPage(){
        String nextSubjectID = subjectModel.getNextSubjectID();
        lblSubID.setText(nextSubjectID);
        txtSubName.requestFocus();
        RegexUtil.resetStyle(txtSubName);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
        btnSave.setVisible(true);
        btnUpdate.setDisable(true);
        txtSubName.setText("");
        tareaDescription.setText("");
        checkComboBoxGrade.getCheckModel().clearChecks();

    }

    public void isSaveEnable(){
        boolean isCheckName = txtSubName != null && !txtSubName.getText().isEmpty() && txtSubName.getText().matches(subjectRegex);

        btnSave.setDisable(!isCheckName);
    }

    public void isResetEnable() {
        boolean isCheckName = txtSubName != null && !txtSubName.getText().isEmpty();
        boolean isCheckDescription = tareaDescription != null && !tareaDescription.getText().isEmpty();

        btnReset.setDisable(!(isCheckName || isCheckDescription));
    }

    public void setUserDto(SubjectDto dto) {
        // Step 1: Set text fields and labels
        lblSubID.setText(dto.getSubjectId());
        txtSubName.setText(dto.getSubjectName());
        tareaDescription.setText(dto.getSubjectDescription());

        // Step 2: Clear previous selections in the CheckComboBox
        checkComboBoxGrade.getCheckModel().clearChecks();

        // Step 3: Mark grades in the CheckComboBox
        String[] subjectGrades = dto.getSubjectGrades(); // e.g., ["grade 1", "grade 2"]

        if (subjectGrades != null) {
            for (String grade : subjectGrades) {
                // Check if the grade exists in the CheckComboBox options
                if (checkComboBoxGrade.getItems().contains(grade)) {
                    checkComboBoxGrade.getCheckModel().check(grade); // Mark the grade
                }
            }
        }
    }

    public void tableOnClickedButton(){
        btnSave.setVisible(false);
        btnReset.setDisable(false);
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

}
