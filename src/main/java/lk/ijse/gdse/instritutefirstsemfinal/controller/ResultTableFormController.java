package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ResultTableFormController {

    @FXML
    private Pane ResultPane;

    @FXML
    private Button btnResultAction;

    @FXML
    private TableColumn<?, ?> colExamID;

    @FXML
    private TableColumn<?, ?> colGradeArchieve;

    @FXML
    private TableColumn<?, ?> colMarks;

    @FXML
    private TableColumn<?, ?> colResultID;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudent;

    @FXML
    private TableColumn<?, ?> colSubject;

    @FXML
    private TableView<?> tblResult;

    @FXML
    private TextField txtFindResult;

    @FXML
    void btnResultActionOnAction(ActionEvent event) {

    }

    @FXML
    void tblUserOnClicked(MouseEvent event) {

    }

    @FXML
    void txtFindResultOnKeyRelesed(KeyEvent event) {

    }

}
