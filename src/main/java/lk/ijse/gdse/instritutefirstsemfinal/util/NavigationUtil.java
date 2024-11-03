package lk.ijse.gdse.instritutefirstsemfinal.util;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class NavigationUtil {
    public static void loadPane(Class currentClass, Pane pane, String stageTitle, String resource) {
        try {
            pane.getChildren().clear();
            Pane load = FXMLLoader.load(currentClass.getResource(resource));
            pane.getChildren().add(load);
            loadPaneEffect(load);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setTitle("StudyHall - "+stageTitle);

        }catch (IOException e){
            e.printStackTrace();
            AlertUtil.errorAlert(currentClass,null,stageTitle);
        }
    }

    public static void loadPaneEffect(Pane load){
        load.setScaleX(0.8);
        load.setScaleY(0.8);
        load.setOpacity(0);

        // Create a fade transition to gradually make the pane visible
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), load);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Create a scale transition to make the pane "zoom in"
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), load);
        scaleTransition.setFromX(0.8);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Create a parallel transition to play both effects together
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();

    }

}
