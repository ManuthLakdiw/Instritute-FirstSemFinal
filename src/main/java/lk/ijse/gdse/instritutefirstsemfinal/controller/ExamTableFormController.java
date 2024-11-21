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
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.ExamTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamTableFormController implements Initializable {

    ExamFormController examFormController = new ExamFormController();

    ExamModel examModel = new ExamModel();

    @FXML
    private Pane AdminPane;

    @FXML
    private Button btnExamAction;

    @FXML
    private TableColumn<ExamTm, LocalDate> colDate;

    @FXML
    private TableColumn<ExamTm, String> colDesc;

    @FXML
    private TableColumn<ExamTm, String> colExamID;

    @FXML
    private TableColumn<ExamTm, String> colExamType;

    @FXML
    private TableColumn<ExamTm, String> colGrade;

    @FXML
    private TableColumn<ExamTm, String> colSubject;

    @FXML
    private TableView<ExamTm> tblExam;

    @FXML
    private TextField txtFindExam;

    boolean isClicked = false;
    @FXML
    void btnExamActionOnAction(ActionEvent event) {
        isClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/examForm.fxml"));
            Parent load = loader.load();

            ExamFormController controller = loader.getController();

            controller.setExamTableFormController(this);

            this.examFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Exam Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnExamAction.getScene().getWindow());

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
    void txtFindExamOnKeyRelesed(KeyEvent event) {

    }

    public void loadExamTable(){
        ArrayList<ExamDto> examDtos = examModel.getAllExams();
        ObservableList<ExamTm> examTms = FXCollections.observableArrayList();

        for (ExamDto examDto : examDtos) {
            ExamTm examTm = new ExamTm(
                    examDto.getExamId(),
                    examDto.getGrade() == null ? "Not Specified grade" : examDto.getGrade(),
                    examDto.getSubject(),
                    examDto.getExamDate(),
                    examDto.getExamType(),
                    examDto.getExamDescription()
            );
            examTms.add(examTm);
        }

        tblExam.setItems(examTms);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colExamID.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colExamType.setCellValueFactory(new PropertyValueFactory<>("examType"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("examDescription"));

        loadExamTable();
    }
}