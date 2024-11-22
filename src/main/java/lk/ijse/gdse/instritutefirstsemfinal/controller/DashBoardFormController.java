package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    StudentModel studentModel = new StudentModel();
    TeacherModel teacherModel = new TeacherModel();
    ExamModel examModel = new ExamModel();

    @FXML
    private HBox hBox2;

    @FXML
    private Label lblExam;

    @FXML
    private Label lblStudent;

    @FXML
    private Label lblTeacher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblExam.setText(String.valueOf(examModel.getExamCount()));
        lblStudent.setText(String.valueOf(studentModel.getStudentCount()));
        lblTeacher.setText(String.valueOf(teacherModel.getTeacherCount()));

    }
}
