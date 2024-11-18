package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
        // Clear the TreeView root
//        treeViewSUbAndGrades.setRoot(null);
//
//        // Create the root item for TreeView
//        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>("Subjects");
//        rootItem.setExpanded(true); // Make the root expanded by default
//
//        // Get the selected subjects from CheckComboBox
//        List<String> selectedSubjects = checkComboBoxSubject.getCheckModel().getCheckedItems();
//
//        // For each selected subject, get its grades and create CheckBoxTreeItems
//        for (String subjectName : selectedSubjects) {
//            // Get grades for the selected subject
//            List<String> grades = gradeModel.getAllSubjects(subjectName);
//
//            // Create a CheckBoxTreeItem for the subject
//            CheckBoxTreeItem<String> subjectItem = new CheckBoxTreeItem<>(subjectName);
//
//            // Add grades as child nodes to the subject node
//            for (String grade : grades) {
//                // Create a CheckBoxTreeItem for each grade
//                CheckBoxTreeItem<String> gradeItem = new CheckBoxTreeItem<>(grade);
//                subjectItem.getChildren().add(gradeItem);
//            }
//
//            // Add a listener to select all children when the root is selected
//            subjectItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
//                // Select or deselect all child nodes (grades)
//                for (TreeItem<String> child : subjectItem.getChildren()) {
//                    if (child instanceof CheckBoxTreeItem) {
//                        ((CheckBoxTreeItem<String>) child).setSelected(newValue);
//                    }
//                }
//            });
//
//            // Add the subject item to the root item
//            rootItem.getChildren().add(subjectItem);
//        }
//
//        // Set the root for the TreeView
//        treeViewSUbAndGrades.setRoot(rootItem);
//    }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        List<String> selectedSubjects = checkComboBoxSubject.getCheckModel().getCheckedItems();
        List<String> selectedGrades = new ArrayList<>();

        for (TreeItem<String> subjectItem : treeViewSUbAndGrades.getRoot().getChildren()) {
            CheckBoxTreeItem<String> checkBoxSubjectItem = (CheckBoxTreeItem<String>) subjectItem;

            for (TreeItem<String> gradeItem : checkBoxSubjectItem.getChildren()) {
                CheckBoxTreeItem<String> checkBoxGradeItem = (CheckBoxTreeItem<String>) gradeItem;

                if (checkBoxGradeItem.isSelected()) {
                    selectedGrades.add(checkBoxGradeItem.getValue());
                }
            }
        }

        System.out.println(lblTeacherID.getText());
        System.out.println(txtName.getText());
        System.out.println(txtContactNumber.getText());
        System.out.println(txtEmailAddress.getText());

        // Create TeacherDto object based on form input
        TeacherDto teacherDto = new TeacherDto(
                lblTeacherID.getText(),
                txtName.getText(),
                txtContactNumber.getText(),
                txtEmailAddress.getText()
        );



        // Save teacher and link subjects and grades
        boolean isSaved = teacherModel.saveTeacher(teacherDto, selectedSubjects, selectedGrades);

        if (isSaved) {
            System.out.println("Teacher saved successfully!");
            refreshPage();
            tableTeacherFormController.loadTeacherTable();
        } else {
            System.out.println("Failed to save teacher.");
        }
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
//        btnSave.setDisable(true);
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

            for (SubjectDto subject : subjects) {
                checkComboBoxSubject.getItems().add(subject.getSubjectName());
            }



        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.errorAlert(this.getClass(), null, "Failed to load subjects.");
        }
    }


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
        populateSubjectCheckComboBox();

        // Add a ListChangeListener to monitor changes in checked items
        checkComboBoxSubject.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            // Whenever the list of checked items changes, update the TreeView
            updateTreeViewForSelectedSubjects((ObservableList<String>) c.getList()); // c.getList() returns ObservableList
        });
    }

    private void updateTreeViewForSelectedSubjects(ObservableList<String> selectedSubjects) {
        try {
            // Clear the TreeView root
            treeViewSUbAndGrades.setRoot(null);

            // Create the root item for TreeView
            CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>("Subjects");
            rootItem.setExpanded(true); // Make the root expanded by default

            // For each selected subject, get its grades and create CheckBoxTreeItems
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

                // Add a listener to select all children when the subject is selected
                subjectItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    // Select or deselect all child nodes (grades)
                    for (TreeItem<String> child : subjectItem.getChildren()) {
                        if (child instanceof CheckBoxTreeItem) {
                            ((CheckBoxTreeItem<String>) child).setSelected(newValue);
                        }
                    }
                });

                // Add the subject item to the root item
                rootItem.getChildren().add(subjectItem);
            }

            // Set the root for the TreeView
            treeViewSUbAndGrades.setRoot(rootItem);

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.errorAlert(this.getClass(), null, "Failed to load grades for subjects.");
        }
    }

}
