package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.ResultTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.ResultModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultTableFormController implements Initializable {

    ResultFormController resultFormController = new ResultFormController();

    ResultModel resultModel = new ResultModel();

    @FXML
    private Pane ResultPane;

    @FXML
    private Button btnResultAction;

    @FXML
    private TableColumn<ResultTm, String> colExamID;

    @FXML
    private TableColumn<ResultTm, String> colGrade;

    @FXML
    private TableColumn<ResultTm, String> colGradeArchieved;

    @FXML
    private TableColumn<ResultTm, String> colGradeStatus;

    @FXML
    private TableColumn<ResultTm, Integer> colMarks;

    @FXML
    private TableColumn<ResultTm, String> colResultID;

    @FXML
    private TableColumn<ResultTm, String> colStudent;

    @FXML
    private TableColumn<ResultTm, String> colSubject;


    @FXML
    private TableView<ResultTm> tblResult;

    @FXML
    private TextField txtFindResult;

    boolean isClicked = false;
    @FXML
    void btnResultActionOnAction(ActionEvent event) {
        isClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/resultForm.fxml"));
            Parent load = loader.load();

            ResultFormController controller = loader.getController();

            controller.setResultTableFormController(this);

            this.resultFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Exam Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnResultAction.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void tblUserOnClicked(MouseEvent event) {

    }

    @FXML
    void txtFindResultOnKeyRelesed(KeyEvent event) {

    }

    public void loadTable(){
        ArrayList<ResultDto> resultDtos = resultModel.getAllResults();
        ObservableList<ResultTm > resultTms = FXCollections.observableArrayList();
        for (ResultDto resultDto : resultDtos) {
            ResultTm resultTm = new ResultTm(
                    resultDto.getResultID(),
                    resultDto.getGrade(),
                    resultDto.getSubject(),
                    resultDto.getExam(),
                    resultDto.getStudent(),
                    resultDto.getMarks(),
                    resultDto.getGradeArchieved(),
                    resultDto.getStatus()
            );
            resultTms.add(resultTm);
        }
        tblResult.setItems(resultTms);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colResultID.setCellValueFactory(new PropertyValueFactory<>("resultID"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("student"));
        colExamID.setCellValueFactory(new PropertyValueFactory<>("exam"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGradeArchieved.setCellValueFactory(new PropertyValueFactory<>("gradeArchieved"));
        colGradeStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colMarks.setCellValueFactory(new PropertyValueFactory<>("marks"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        loadTable();

    }
}
