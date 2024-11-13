package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SubjectFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblSubID;

    @FXML
    private Pane subjectPane;

    @FXML
    private JFXTextArea tareaDescription;

    @FXML
    private TableView<?> tblSubject;

    @FXML
    private TextField txtSubName;

    @FXML
    void btnDeleteOnClicked(ActionEvent event) {

    }

    @FXML
    void btnResetOnClicked(ActionEvent event) {

    }

    @FXML
    void btnSaveOnClicked(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnClicked(ActionEvent event) {

    }

    @FXML
    void tblSubjectOnClicked(MouseEvent event) {

    }

    @FXML
    void txtSubNameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtSubNameOnKeyTyped(KeyEvent event) {

    }

}
