package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private CheckComboBox<String> cmbCheckComboBox;

    @FXML
    private DatePicker dpDOB;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
