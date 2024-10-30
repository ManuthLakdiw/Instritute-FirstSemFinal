package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private Button btnTeacher;

    @FXML
    private AnchorPane dashBoardMainpane;

    @FXML
    void btnTeacherOnClicked(ActionEvent event) {
        try {

            dashBoardMainpane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacherForm.fxml"));
            AnchorPane pane = loader.load();

            pane.prefWidthProperty().bind(dashBoardMainpane.widthProperty());
            pane.prefHeightProperty().bind(dashBoardMainpane.heightProperty());

            dashBoardMainpane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load page!");
            alert.setHeaderText("Error");
            alert.showAndWait();
        }

    }



}
