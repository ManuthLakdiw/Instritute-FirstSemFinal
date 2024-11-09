package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutFormController implements Initializable {

    @FXML
    private HBox btnDashBoard;

    @FXML
    private HBox btnStudent;

    @FXML
    private HBox btnTeacher;

    @FXML
    private Pane contentMainPane;

    private String currentLoadedFXML = "";
    private HBox lastClickedHBox = null;  // Track the last clicked HBox

    @FXML
    void btnDashBoardOnClicked(MouseEvent event) {
        applyHBoxBackground(btnDashBoard);  // Apply background color for the HBox
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
    }

    @FXML
    void btnStudentOnClicked(MouseEvent event) {
        applyHBoxBackground(btnStudent);  // Apply background color for the HBox
        navigateTo("/view/studentForm.fxml", "StudentForm");
    }

    @FXML
    void btnTeacherOnClicked(MouseEvent event) {
        applyHBoxBackground(btnTeacher);  // Apply background color for the HBox
        navigateTo("/view/teacherForm.fxml", "TeacherForm");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyHBoxBackground(btnDashBoard);
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
    }

    public void navigateTo(String fxmlPath, String loadErrorpane) {
        if (fxmlPath.equals(currentLoadedFXML)) {
            return;
        }

        try {
            currentLoadedFXML = fxmlPath;

            // Load the new pane from FXML
            Pane pane = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Bind the width and height of the loaded pane to the contentMainPane
            pane.prefWidthProperty().bind(contentMainPane.widthProperty());
            pane.prefHeightProperty().bind(contentMainPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            contentMainPane.getChildren().clear();
            contentMainPane.getChildren().add(pane);

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
}
