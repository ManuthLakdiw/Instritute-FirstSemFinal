package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private CheckComboBox<String> cmbCheckComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Define items
        ObservableList<String> items = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3", "Option 4");




        cmbCheckComboBox.getItems().addAll(items);

    }
}
