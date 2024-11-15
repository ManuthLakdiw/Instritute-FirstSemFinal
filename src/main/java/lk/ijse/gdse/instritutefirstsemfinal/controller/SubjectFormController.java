package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
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
import javafx.stage.StageStyle;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SubjectFormController implements Initializable {

    GradeModel gradeModel = new GradeModel();

    SubjectTableFormController subjectTableFormController = new SubjectTableFormController();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SubjectTm, String> colDescription;

    @FXML
    private TableColumn<SubjectTm, String> colID;

    @FXML
    private TableColumn<SubjectTm, String> colName;

    @FXML
    private Label lblSubID;

    @FXML
    private Label lblSubName;

    @FXML
    private Pane subjectPane;

    @FXML
    private JFXTextArea tareaDescription;

    @FXML
    private TableView<SubjectTm> tblSubject;

    @FXML
    private CheckComboBox<String> cComboBoxGrade;

    @FXML
    private Label lblGrade;

    @FXML
    private TextField txtSubName;

    public void enableSaveButton(boolean enable) {
        btnSave.setDisable(!enable);
    }

    // Method to enable or disable Update Button
    public void enableUpdateButton(boolean enable) {
        btnUpdate.setDisable(!enable);
    }

    // Method to enable or disable Reset Button
    public void enableResetButton(boolean enable) {
        btnReset.setDisable(!enable);
    }

    // Method to enable or disable Delete Button
    public void enableDeleteButton(boolean enable) {
        btnDelete.setDisable(!enable);
    }

    SubjectModel model = new SubjectModel();

    String subjectId;
    String subjectName;
    String subjectDescription;

    String subjectRegex = "^[A-Za-z][A-Za-z0-9 .,_]*$";

    public void setSubjectDto(SubjectDto subjectDto) {
        if (subjectDto != null) {
            lblSubID.setText(subjectDto.getSubjectId());
            txtSubName.setText(subjectDto.getSubjectName());
            cComboBoxGrade.getItems().clear();
            cComboBoxGrade.getItems().addAll(subjectDto.getSubjectGrades());
            tareaDescription.setText(subjectDto.getSubjectDescription());


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));

        ArrayList<GradeDto> grades = gradeModel.getGrades();
        ArrayList<String>  items = new ArrayList<>();

        for (GradeDto grade : grades) {
            items.add(grade.getGradeName());
        }
        cComboBoxGrade.getItems().addAll(items);

        enableSaveButton(true);  // Enable Save by default
        enableUpdateButton(false);
        enableDeleteButton(false);
        enableResetButton(true);

       refreshPage();

    }

    private void refreshPage(){
        String nextSubjectID = model.getNextSubjectID();
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

        loadSubjectTable();

    }

    private void loadSubjectTable(){
        ArrayList<SubjectDto> subjectDtos = model.getAllSubjects();
        ObservableList<SubjectTm> subjectTms = FXCollections.observableArrayList();

        for (SubjectDto subjectDto : subjectDtos) {
            SubjectTm subjectTm = new SubjectTm(
                    subjectDto.getSubjectId(),
                    subjectDto.getSubjectName(),
                    subjectDto.getSubjectGrades(),
                    subjectDto.getSubjectDescription()

                    );
            subjectTms.add(subjectTm);
        }
        tblSubject.setItems(subjectTms);



    }



    @FXML
    void tblSubjectOnClicked(MouseEvent event) {
        btnSave.setVisible(false);
        SubjectTm isSelected = tblSubject.getSelectionModel().getSelectedItem();

        if (isSelected != null) {
            lblSubID.setText(isSelected.getSubjectId());
            txtSubName.setText(isSelected.getSubjectName());
            tareaDescription.setText(isSelected.getSubjectDescription());

            btnReset.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            RegexUtil.resetStyle(txtSubName);
        }


    }

    @FXML
    void btnDeleteOnClicked(ActionEvent event) {
        RegexUtil.resetStyle(txtSubName);
        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure delete this Subject?", ButtonType.NO, ButtonType.YES);
        successAlert.setTitle("Confirmation");
        successAlert.setHeaderText(null);
        successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        Optional<ButtonType> buttonType = successAlert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = model.deleteSubject(lblSubID.getText());

            if (isDeleted) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Subject deleted successfully");
                refreshPage();
                subjectTableFormController.loadSubjectTable();

            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Subject could not be deleted!");
            }
        }

    }

    @FXML
    void btnResetOnClicked(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void btnSaveOnClicked(ActionEvent event) {
        ObservableList<String> selectedItems = cComboBoxGrade.getCheckModel().getCheckedItems();

        subjectId = lblSubID.getText();
        subjectName = txtSubName.getText();
        subjectDescription = tareaDescription.getText();

        if (subjectDescription == null || subjectDescription.isEmpty()) {
            subjectDescription = "Not specified Description";
        }

        if (!btnSave.isDisable()){
            SubjectDto subjectDto = new SubjectDto(subjectId, subjectName , subjectDescription);

            boolean isSave = model.saveSubject(subjectDto);

            if (isSave){
                AlertUtil.informationAlert(UserFormController.class,null,true,"Subject Saved Successfully");
                refreshPage();
                subjectTableFormController.loadSubjectTable();
            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"Subject Saved Failed");
            }
        }
    }

    @FXML
    void btnUpdateOnClicked(ActionEvent event) {
        RegexUtil.resetStyle(txtSubName);
        btnUpdate.setDisable(false);

        if (!btnUpdate.isDisable()) {
            subjectId = lblSubID.getText();
            subjectName = txtSubName.getText();
            subjectDescription = tareaDescription.getText();

            boolean check = true;

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

            SubjectDto existingSubject = model.getSubjectByID(subjectId);

            if (existingSubject != null) {

                if (subjectDescription != null && subjectDescription.equals(existingSubject.getSubjectDescription()) &&
                        subjectName != null && subjectName.equals(existingSubject.getSubjectName())) {
                    check = false;
                    AlertUtil.informationAlert(UserFormController.class, null, false, "No changes detected. Update is not necessary.");
                } else {
                    if (subjectDescription == null || subjectDescription.isEmpty()) {
                        subjectDescription = "Not specified Description";
                    }


                    boolean isUpdate = model.updateSubject(new SubjectDto(subjectId, subjectName, subjectDescription));

                    if (isUpdate) {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Subject : " + subjectId + " updated successfully");
                        btnUpdate.setDisable(false);
                        btnSave.setVisible(true);
                        subjectTableFormController.loadSubjectTable();
                    } else {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Subject : " + subjectId + " update failed!");
                    }
                }


            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject not found!");
            }

            if (check) {
                refreshPage();
            }
        }
    }



    public void tareaDescriptionOnKeyPressed(KeyEvent keyEvent) {
        if (tareaDescription.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                txtSubName.requestFocus();
                txtSubName.positionCaret(txtSubName.getText().length());
            }
        }
    }

    public void tareaDescriptionOnKeyTyped(KeyEvent keyEvent) {
        subjectDescription = tareaDescription.getText();

        if (subjectDescription.isEmpty()) {
            btnReset.setDisable(true);
            isResetEnable();
        }else {
            btnReset.setDisable(false);

        }
    }



    @FXML
    void txtSubNameOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tareaDescription.requestFocus();
            tareaDescription.positionCaret(tareaDescription.getText().length());
        }

    }
    @FXML
    void txtSubNameOnKeyTyped(KeyEvent event) {
        subjectName = txtSubName.getText();
        btnReset.setDisable(false);
        RegexUtil.resetStyle(txtSubName);

        if (subjectName.isEmpty()) {
            btnReset.setDisable(true);
            RegexUtil.resetStyle(txtSubName);
            lblSubName.setText("");
            isResetEnable();
        }else {
            btnReset.setDisable(false);
            if (!subjectName.matches(subjectRegex)) {
                lblSubName.setStyle("-fx-text-fill: red");
                RegexUtil.setErrorStyle(false,txtSubName);
                lblSubName.setText("Start with a letter, use only letters, numbers, spaces");
            }else {
                lblSubName.setText(" ");
                RegexUtil.resetStyle(txtSubName);
            }
        }
        isSaveEnable();
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

    @FXML
    private void cComboBoxGradeMouseEntered(MouseEvent mouseEvent) {
        if (cComboBoxGrade.getCheckModel().getCheckedItems().isEmpty()) {
            lblGrade.setText("Choose a Grade");}

    }

    @FXML
    private void cComboBoxGradeMouseExited(MouseEvent event) {
        lblGrade.setText("");
    }

    public void btnLoadTableOnCLicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/subjectTableForm.fxml"));
            Parent load = loader.load();

            SubjectTableFormController controller = loader.getController();
            controller.setSubjectFormController(this);

            this.subjectTableFormController = controller;



            Stage stage = new Stage();
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("subject Table");
            stage.setScene(new Scene(load));

            stage.initModality(null);
            stage.initOwner(btnSave.getScene().getWindow());
            stage.setResizable(false);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}




