package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
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
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectFormController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));

        refreshPage();

    }

    SubjectModel model = new SubjectModel();

    String subjectId;
    String subjectName;
    String subjectDescription;


    String subjectRegex = "^[A-Za-z][A-Za-z0-9 .,_]*$";

    @FXML
    void tblSubjectOnClicked(MouseEvent event) {
        btnSave.setVisible(false);
        SubjectTm isSelected = tblSubject.getSelectionModel().getSelectedItem();

        if (isSelected != null) {
            lblSubID.setText(isSelected.getSubjectId());
            txtSubName.setText(isSelected.getSubjectName());
            tareaDescription.setText(isSelected.getSubjectDescription());

            btnReset.setDisable(false);
            RegexUtil.resetStyle(txtSubName);
        }


    }

    private void refreshPage(){
        String nextSubjectID = model.getNextTeacherID();
        lblSubID.setText(nextSubjectID);

        RegexUtil.resetStyle(txtSubName);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
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
                    subjectDto.getSubjectDescription()
            );
            subjectTms.add(subjectTm);
        }
        tblSubject.setItems(subjectTms);



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
    private TextField txtSubName;

    @FXML
    void btnDeleteOnClicked(ActionEvent event) {

    }

    @FXML
    void btnResetOnClicked(ActionEvent event) {
        refreshPage();

    }

    @FXML
    void btnSaveOnClicked(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnClicked(ActionEvent event) {

    }



    public void tareaDescriptionOnKeyPressed(KeyEvent keyEvent) {
        if (tareaDescription.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                txtSubName.requestFocus();
                txtSubName.positionCaret(txtSubName.getText().length());
            }
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
//            checkFieldsEmpty();
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
    }

}




