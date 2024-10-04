module lk.ijse.gdse.instritutefirstsemfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires lombok;
    requires java.sql;



    opens lk.ijse.gdse.instritutefirstsemfinal.controller to javafx.fxml;
    exports lk.ijse.gdse.instritutefirstsemfinal;
}




