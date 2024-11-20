package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;

public class StudentTableFormController {
    StudentFormController studentFormController = new StudentFormController();

    @FXML
    private Pane StudentPane;

    @FXML
    private Button btnAction;

    @FXML
    private Button btnOtherInfo;

    @FXML
    private TableColumn<?, ?> colAddBy;

    @FXML
    private TableColumn<?, ?> colAdmissionFee;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colGrade;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colSubject;

    @FXML
    private TableView<?> tblStudent;

    @FXML
    private TextField txtFindStudent;

    private String currentLoadedFXML = "";
    boolean isClicked = false;

    @FXML
    void btnActionOnClicked(ActionEvent event) {
        isClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentForm.fxml"));
            Parent load = loader.load();

            StudentFormController controller = loader.getController();

            controller.setStudentTableFormController(this);

            this.studentFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("User Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnAction.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnOtherInfoOnAction(ActionEvent event) {
        navigateTo("/view/studentOtherInfoTableForm.fxml","student - Other Details");

    }

    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtFindStudentOnKeyRelesed(KeyEvent event) {

    }


    public  void navigateTo(String fxmlPath, String loadErrorpane) {
        if (fxmlPath.equals(currentLoadedFXML)) {
            return;
        }

        try {
            currentLoadedFXML = fxmlPath;

            // Load the new pane from FXML
            Pane newPane = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Set initial position for the new pane off the screen to the left
            newPane.setTranslateX(-StudentPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(StudentPane.widthProperty());
            newPane.prefHeightProperty().bind(StudentPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            StudentPane.getChildren().clear();
            StudentPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(250));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(StudentTableFormController.class, null, loadErrorpane);
        }
    }

}
