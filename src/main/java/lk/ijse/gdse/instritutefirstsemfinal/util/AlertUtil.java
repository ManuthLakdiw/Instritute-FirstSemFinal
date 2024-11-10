package lk.ijse.gdse.instritutefirstsemfinal.util;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.util.Duration;

public class AlertUtil {
     public static void errorAlert(Class currentClass, String header, String content) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(header);
         alert.setContentText("Fail to load "+content+" !");
         DialogPane dialogPane = alert.getDialogPane();
         dialogPane.getStylesheets().add(currentClass.getResource("/style/Style.css").toExternalForm());
         alert.showAndWait();
     }

     public static void informationAlert(Class currentClass, String header,boolean autoClose , String content) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Information");
         alert.setHeaderText(header);
         alert.setContentText(content);

         DialogPane dialogPane = alert.getDialogPane();
         dialogPane.getStylesheets().add(currentClass.getResource("/style/Style.css").toExternalForm());
         dialogPane.getStyleClass().add("custom-alert");


         if(autoClose){
             PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
             delay.setOnFinished(event -> alert.close());
             delay.play();
             alert.show();
             return;
         }

         alert.showAndWait();

     }

}