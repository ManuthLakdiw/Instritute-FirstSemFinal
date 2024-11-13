package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

public class SendMailToTeacherFormController {

    @FXML
    private Button btnSend;

    @FXML
    private Label lblClear;

    @FXML
    private JFXTextArea tareaBody;

    @FXML
    private Pane teacherMailPane;

    @FXML
    private TextField txtSubject;

    @Setter
    private String teacherEmail;


    @FXML
    void btnSendOnClicked(ActionEvent event) {
        txtSubject.requestFocus();
        String subject = txtSubject.getText();
        String body = tareaBody.getText();

       if (subject.isEmpty()){
           AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Subject cannot be empty");
           return;
       }
        String from = "manuthlakdiv2006@gmail.com";
        String host = "smtp.gmail.com";
        String username = "manuthlakdiv2006@gmail.com";
        String password = "aefq cods wrot ktgp";



        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(teacherEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Mail sent successfully");

        }catch (MessagingException e){
            AlertUtil.informationAlert(ForgotPasswordFormController.class,null,false,"Failed to send OTP code!\nPlease check your Internet connection.");
        }



    }

    @FXML
    void lblClearOnClicked(MouseEvent event) {
        tareaBody.clear();
    }

}
