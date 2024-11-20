package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;


public class StudentOtherInfoTableFormController {

    private String currentLoadedFXML = "";

    @FXML
    private Pane StudentOtherInfoPane;

    @FXML
    private Button btnSendMail;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colParentName;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Label lblBack;

    @FXML
    private TableView<?> tblStudentOtherInfo;

    @FXML
    private TextField txtFindStudent;

    @FXML
    void btnSendMailOnAction(ActionEvent event) {

    }

    @FXML
    void lblBackOnAction(MouseEvent event) {
        navigateTo("/view/StudentTableForm.fxml", "StudentForm");
    }

    @FXML
    void tblStudentOtherInfoOnMouseClicked(MouseEvent event) {

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
            newPane.setTranslateX(-StudentOtherInfoPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(StudentOtherInfoPane.widthProperty());
            newPane.prefHeightProperty().bind(StudentOtherInfoPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            StudentOtherInfoPane.getChildren().clear();
            StudentOtherInfoPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(250));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(StudentOtherInfoTableFormController.class, null, loadErrorpane);
        }
    }


}
