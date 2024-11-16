package lk.ijse.gdse.instritutefirstsemfinal.controller;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TableSubjectFormController implements Initializable {
    SubjectModel model = new SubjectModel();
    FormSubjectController formSubjectController = new FormSubjectController();


    @FXML
    private Pane SubjectPane;

    @FXML
    private Button btnSubject;

    @FXML
    private TableColumn<SubjectTm, String> colDescription;

    @FXML
    private TableColumn<SubjectTm, String> colGrade;

    @FXML
    private TableColumn<SubjectTm, String> colSubID;

    @FXML
    private TableColumn<SubjectTm, String> colSubName;

    @FXML
    private TableView<SubjectTm> tblSubject;

    @FXML
    private TextField txtFindSubject;

    @FXML
    private void btnSubjectOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formSubject.fxml"));
            Parent load = loader.load();

            FormSubjectController controller = loader.getController();

            controller.setTblSubjectFormController(this);

            this.formSubjectController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Subject Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnSubject.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tblSubjectOnClicked(MouseEvent event) {
        SubjectTm isSelected = tblSubject.getSelectionModel().getSelectedItem();
        if (isSelected != null) {
            String grades = isSelected.getSubjectGrades(); // උදා: "grade 1, grade 2, grade 3"
            String[] gradeArray = new String[0]; // Default හිස් array එක

            if (grades != null && !grades.isEmpty()) {
                // කොමාවෙන් වෙන් කර String[] එකක් ලෙස ලබා ගන්න
                gradeArray = grades.split(", ");
            }

            // SubjectDto object එක නිර්මාණය කිරීම
            SubjectDto dto = new SubjectDto(
                    isSelected.getSubjectId(),
                    isSelected.getSubjectName(),
                    gradeArray, // gradeArray එක එකතු කරන ලදී
                    isSelected.getSubjectDescription()
            );
            formSubjectController.setUserDto(dto);
            formSubjectController.tableOnClickedButton();

        }
    }





        @FXML
    private void txtFindSubjectOnKeyRelesed(KeyEvent event) {
        // Add search functionality here
    }

    public void loadSubjectTable() {
        ArrayList<SubjectDto> subjectDtos = model.getAllSubjects();
        ObservableList<SubjectTm> subjectTms = FXCollections.observableArrayList();

        for (SubjectDto subjectDto : subjectDtos) {
            // Convert grades to a comma-separated string
            String grades = subjectDto.getSubjectGrades() != null && subjectDto.getSubjectGrades().length > 0
                    ? String.join(", ", subjectDto.getSubjectGrades())
                    : "N/A";

            SubjectTm subjectTm = new SubjectTm(
                    subjectDto.getSubjectId(),
                    subjectDto.getSubjectName(),
                    grades,
                    subjectDto.getSubjectDescription()
            );
            subjectTms.add(subjectTm);
        }

        tblSubject.setItems(subjectTms);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSubID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("subjectGrades"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));



        loadSubjectTable();
    }
}
