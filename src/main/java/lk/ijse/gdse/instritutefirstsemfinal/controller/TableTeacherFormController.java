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
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.TeacherTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableTeacherFormController implements Initializable {

    TeacherModel teacherModel = new TeacherModel();
    FormTeacherController formTeacherController = new FormTeacherController();

    @FXML
    private Pane SubjectPane;

    @FXML
    private Button btnSendMail;

    @FXML
    private Button btnTeacher1;

    @FXML
    private TableColumn<TeacherTm, String> colContactNumber;


    @FXML
    private TableColumn<TeacherTm, String> colEmail;

    @FXML
    private TableColumn<TeacherTm, String> colName;

    @FXML
    private TableColumn<TeacherTm, String> colTeacherID;

    @FXML
    private TableColumn<TeacherTm, String> colTeachingSubjects;

    @FXML
    private TableColumn<TeacherTm, String> colTeachingGrades;

    @FXML
    private TableView<TeacherTm> tblTeacher;

    @FXML
    private TextField txtFindTeacher;

    boolean isButtonClicked = false;

    @FXML
    void btnSendMailOnAction(ActionEvent event) {

    }

    @FXML
    void btnTeacherOnAction(ActionEvent event) {
        isButtonClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formTeacherController.fxml"));
            Parent load = loader.load();

            FormTeacherController controller = loader.getController();

            controller.setTableTeacherFormController(this);

            this.formTeacherController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Teacher Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnSendMail.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void tblTeacherOnClicked(MouseEvent event) {

    }

    @FXML
    void txtFindTeacherOnKeyRelesed(KeyEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTeachingSubjects.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        colTeachingGrades.setCellValueFactory(new PropertyValueFactory<>("grades"));

        // Apply custom styling to Teacher ID column
        colTeacherID.setCellFactory(column -> {
            return new TableCell<TeacherTm, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Reset style
                    } else {
                        setText(item);
                        // Apply bold and larger font size to teacher ID
                        setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    }
                }
            };
        });

        loadTeacherTable();
    }


    public void loadTeacherTable() {
        // Fetching teacher data
        ArrayList<TeacherDto> teacherDtos = teacherModel.getAllTeachers();
        ObservableList<TeacherTm> teacherTms = FXCollections.observableArrayList();

        for (TeacherDto teacherDto : teacherDtos) {
            String grades = teacherDto.getGrades() != null && teacherDto.getGrades().length > 0
                    ? String.join(", ", teacherDto.getGrades())
                    : "N/A";

            // Create the TeacherTm object with the teacher's details and grades
            TeacherTm teacherTm = new TeacherTm(
                    teacherDto.getTeacherId(),
                    teacherDto.getName(),
                    teacherDto.getPhoneNumber(),
                    teacherDto.getEmail(),
                    teacherDto.getSubject(), // Directly using subject as a single value
                    grades
            );

            // Add the teacher row to the list
            teacherTms.add(teacherTm);
        }

        // Set the items in the table
        tblTeacher.setItems(teacherTms);
    }






}
