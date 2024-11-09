package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutFormController implements Initializable {

    @FXML
    private HBox hBoxDashBoard;

    @FXML
    private HBox hBoxStudent;

    @FXML
    private HBox hBoxTeacher;

    @FXML
    private HBox hBoxLogout;

    @FXML
    private Pane contentMainPane;

    @FXML
     private Button btnDashBoard;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnTeacher;

    @FXML
    private Button btnLogout;


    private String currentLoadedFXML = "";
    private HBox lastClickedHBox = null;


    @FXML
    void hBoxDashBoardOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxDashBoard);
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
    }

    @FXML
    void hBoxStudentOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxStudent);
        navigateTo("/view/studentForm.fxml", "StudentForm");
    }

    @FXML
    void hBoxTeacherOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxTeacher);
        navigateTo("/view/teacherForm.fxml", "TeacherForm");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHBoxBackground(hBoxDashBoard);
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
        btnDashBoard.setMouseTransparent(true);
        btnStudent.setMouseTransparent(true);
        btnTeacher.setMouseTransparent(true);
        btnLogout.setMouseTransparent(true);

    }

    public void navigateTo(String fxmlPath, String loadErrorpane) {
        if (fxmlPath.equals(currentLoadedFXML)) {
            return;
        }

        try {
            currentLoadedFXML = fxmlPath;

            // Load the new pane from FXML
            Pane newPane = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Set initial position for the new pane off the screen to the left
            newPane.setTranslateX(-contentMainPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(contentMainPane.widthProperty());
            newPane.prefHeightProperty().bind(contentMainPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            contentMainPane.getChildren().clear();
            contentMainPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(100));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(MainLayoutFormController.class, null, loadErrorpane);
        }
    }

    private void applyHBoxBackground(HBox hbox) {
        // Remove the 'selected' style from the last clicked HBox if it exists
        if (lastClickedHBox != null) {
            lastClickedHBox.getStyleClass().remove("selected");
        }

        // Add the 'selected' style to the currently clicked HBox
        hbox.getStyleClass().add("selected");

        // Update the last clicked HBox to the current one
        lastClickedHBox = hbox;
    }

    public void hBoxLogoutOnClicked(MouseEvent mouseEvent) {

    }
}
