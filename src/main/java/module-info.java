module lk.ijse.gdse.instritutefirstsemfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires javafx.media;


    opens lk.ijse.gdse.instritutefirstsemfinal.controller to javafx.fxml;
    exports lk.ijse.gdse.instritutefirstsemfinal;
}




