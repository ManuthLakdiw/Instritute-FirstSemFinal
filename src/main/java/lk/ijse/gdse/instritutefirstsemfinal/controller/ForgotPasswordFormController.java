package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordFormController {
    static String gmail;
    static String otp;

    UserModel model = new UserModel();

    @FXML
    private Button btnGetdigit;

    @FXML
    private ImageView forgotImage;

    @FXML
    private TextField txtEmail;

    @FXML
    private Pane forgotPasswordFormPane;


    public static boolean isInternetAvailable() {
        try {

            Socket socket = new Socket();
            // Google DNS සර්වරය වෙත සම්බන්ධ වීම හරහා සම්බන්ධතාව පරීක්ෂා කරයි
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @FXML
    void btnGetDigitOnAction(ActionEvent eve) {
        String email = txtEmail.getText().trim();

        Pair <Boolean,String> check = model.checkGmailDB(email);
        gmail = check.getValue();


        if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Reset Error!");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a valid email");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
            PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
            delay.setOnFinished(event -> alert.close());
            delay.play();
            alert.show();
        }else {
            if (check.getKey()) {
                try {
                    Random rand  = new Random();
                    int min = 1234;
                    int max = 9999;
                    otp = String.valueOf(rand.nextInt(max - min + 1) + min);

                    String subject = "Password Reset OTP Code!";
                    String body = "Your Confirmation Code is:\n"
                            + "****************************\n"
                            + "     " + otp + "      \n"
                            + "****************************";


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
                            // Create a default MimeMessage object
                            Message message = new MimeMessage(session);

                            // Set the From field
                            message.setFrom(new InternetAddress(from));

                            // Set the To field
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmail));

                            // Set the Subject field
                            message.setSubject(subject);

                            // Set the message
                            message.setText(body);


                            // Send the message
                            Transport.send(message);

                            // Show success alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " OTP code sent successfully!!" , ButtonType.CLOSE, ButtonType.OK);
                            alert.setTitle("Forgot Password");
                            alert.setHeaderText(null);
                            DialogPane dialogPane = alert.getDialogPane();
                            dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                            Optional<ButtonType> buttonType = alert.showAndWait();

                            if (buttonType.get() == ButtonType.OK){

                                forgotPasswordFormPane.getChildren().clear();
                                Pane pane = FXMLLoader.load(getClass().getResource("/view/resetPasswordConfirmForm.fxml"));
                                forgotPasswordFormPane.getChildren().add(pane);
                                Stage stage = (Stage) forgotPasswordFormPane.getScene().getWindow();
                                stage.setTitle("Confirm Email");

                            }

                    }catch (MessagingException e) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to send OTP code!.\nplease check your Internet connection");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                        alert.showAndWait();
                    }


                }catch (IOException e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load Confirm Email!");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    alert.showAndWait();
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Forgot Password");
                alert.setHeaderText(null);
                alert.setContentText(email+" is not in the dataBase. Try again!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                PauseTransition delay = new PauseTransition(Duration.seconds(1.9));
                delay.setOnFinished(event -> alert.close());
                delay.play();
                alert.show();
                txtEmail.clear();
            }
        }

    }

}



