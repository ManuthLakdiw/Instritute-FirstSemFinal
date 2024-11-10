module lk.ijse.gdse.instritutefirstsemfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires javafx.media;
    requires java.mail;

    opens lk.ijse.gdse.instritutefirstsemfinal.dto.tm to javafx.base;
    opens lk.ijse.gdse.instritutefirstsemfinal.controller to javafx.fxml;
    exports lk.ijse.gdse.instritutefirstsemfinal;
}




