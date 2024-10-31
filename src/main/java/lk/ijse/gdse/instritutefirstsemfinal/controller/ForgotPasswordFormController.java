package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordFormController {
    private final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static String gmail;
    static String otp;

    private final UserModel model = new UserModel();

    @FXML
    private Button btnGetdigit;

    @FXML
    private ImageView forgotImage;

    @FXML
    private TextField txtEmail;

    @FXML
    private Pane forgotPasswordFormPane;


    @FXML
    void btnGetDigitOnAction(ActionEvent eve) {
        String email = txtEmail.getText().trim();
        boolean isValidEmail = email.matches(emailRegex);


        List<Object> result = model.checkGmailDB(email);
        boolean isEmailExists = (boolean) result.get(0);
        gmail = (String) result.get(1);
        String userName = (String) result.get(2);

        txtEmail.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");

        if (!isValidEmail) {
            txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");
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
        } else {
            if (isEmailExists) {
                try {
                    Random rand = new Random();
                    int min = 1234;
                    int max = 9999;
                    otp = String.valueOf(rand.nextInt(max - min + 1) + min);


                    String subject = "Password Reset OTP Code!";
                    String body = "Hello, "+userName+"\n\n"
                            + "Please use the following OTP to reset your password:\n\n"
                            + "╔══════════════════════════════╗\n"
                            + "║         \"" + otp + "\"          ║\n"
                            + "╚══════════════════════════════╝\n\n"
                            + "If you did not request a password reset, please ignore this email.\n\n"
                            + "Thank You!!!";

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



                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmail));
                    message.setSubject(subject);
                    message.setText(body);
                    Transport.send(message);

                    btnGetdigit.setText("Done ✔");

                    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "OTP code sent successfully!", ButtonType.CLOSE, ButtonType.OK);
                    successAlert.setTitle("Forgot Password");
                    successAlert.setHeaderText(null);
                    successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
                    Optional<ButtonType> buttonType = successAlert.showAndWait();

                    if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                        forgotPasswordFormPane.getChildren().clear();
                        Pane pane = FXMLLoader.load(getClass().getResource("/view/resetPasswordConfirmForm.fxml"));
                        forgotPasswordFormPane.getChildren().add(pane);
                        Stage stage = (Stage) forgotPasswordFormPane.getScene().getWindow();
                        stage.setTitle("Confirm Email");
                    }else if (buttonType.isPresent() && buttonType.get() == ButtonType.CLOSE) {
                        btnGetdigit.setText("Get 4-digit code");
                    }

                } catch (MessagingException e) {
                    showAlert("Error", "Failed to send OTP code!\nPlease check your Internet connection.", Alert.AlertType.INFORMATION);
                } catch (IOException e) {
                    showAlert("Error", "Failed to load Confirm Email!", Alert.AlertType.ERROR);
                }
            } else {
                txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");
                showAlert("Forgot Password", email + " is not in the database. Try again!", Alert.AlertType.INFORMATION);
                txtEmail.clear();
                txtEmail.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");


            }
        }
    }



    @FXML
    public void txtEmailOnkeyType(KeyEvent keyEvent) {
        String isEmptycheckTxtEmail = keyEvent.getText();

        if (isEmptycheckTxtEmail.isEmpty()) {
            txtEmail.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");
        }
    }




    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style/Style.css").toExternalForm());
        alert.showAndWait();
    }

}



