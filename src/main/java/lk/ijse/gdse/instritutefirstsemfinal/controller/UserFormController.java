package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.UserTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    @FXML
    private Pane AdminPane;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSendMailUser;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserTm, String> colContactNumber;

    @FXML
    private TableColumn<UserTm, String> colEmailAddress;

    @FXML
    private TableColumn<UserTm, String> colPassword;

    @FXML
    private TableColumn<UserTm, String> colUserName;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblUserNameStatus;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label llblConfirmPassword;

    String userName;
    String newPassword;
    String confirmPassword;
    String phoneNumber;
    String email;


    private String usernameRegex = "^[a-zA-Z][a-zA-Z0-9_-]{3,8}[a-zA-Z0-9]$";
    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";
    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";
    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";
    private String phoneNumberRegex = "^0\\d{9}$";
    private String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" ;



    UserModel userModel = new UserModel();



    @FXML
    private void btnSaveOnAction(ActionEvent event) {


    }

    @FXML
    void txtConfirmPasswordOnKeyPressed(KeyEvent event) {
        if (txtConfirmPassword.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtConfirmPassword);
                txtNewPassword.requestFocus();
                txtNewPassword.positionCaret(txtNewPassword.getText().length());
            }
        }else if (event.getCode() == KeyCode.ENTER) {
            txtContactNumber.requestFocus();
            txtContactNumber.positionCaret(txtContactNumber.getText().length());

        }
    }



    @FXML
    void txtConfirmPasswordOnKeyTyped(KeyEvent event) {
        confirmPassword = txtConfirmPassword.getText();


        if (txtConfirmPassword.getText().isEmpty() && !txtNewPassword.getText().isEmpty()) {
            llblConfirmPassword.setText("Confirm your new password ⇪");
            llblConfirmPassword.setTextFill(Color.web("#4a4848"));
            RegexUtil.resetStyle(txtConfirmPassword);
        }

        else if (confirmPassword.equals(newPassword)) {
            RegexUtil.resetStyle(txtConfirmPassword);
            llblConfirmPassword.setText("Password confirmed ✔︎");
            llblConfirmPassword.setTextFill(Color.GREEN);
        }

        else {
            RegexUtil.setErrorStyle(false, txtConfirmPassword);
            llblConfirmPassword.setText("Passwords do not match! ⛔");
            llblConfirmPassword.setTextFill(Color.RED);
        }

        isSaveEnable();

    }



    @FXML
    void txtContactNumberOnKeyPressed(KeyEvent event) {
        if (txtContactNumber.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtContactNumber);
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.positionCaret(txtConfirmPassword.getText().length());
            }
        }else if (event.getCode() == KeyCode.ENTER) {
            txtEmailAddress.requestFocus();
            txtEmailAddress.positionCaret(txtEmailAddress.getText().length());
        }
    }



    @FXML
    void txtContactNumberOnKeyTyped(KeyEvent event) {
        phoneNumber = txtContactNumber.getText();
        lblPhoneNumber.setStyle("-fx-text-fill: #4a4848;");
        ArrayList<UserDto> users = userModel.getAllUsers();
        ArrayList<String> userContactNumbers = new ArrayList<>();

        for (UserDto userDto : users) {
            userContactNumbers.add(userDto.getUsPhone());
        }

        if (phoneNumber.isEmpty()) {
            lblPhoneNumber.setText("");
            RegexUtil.resetStyle(txtContactNumber);
            return;
        }

        boolean contactExists = false;
        for (String existingContactNumber : userContactNumbers) {
            if (existingContactNumber.equals(phoneNumber)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber already exists!");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        } else if (phoneNumber.matches(phoneNumberRegex)) {
            lblPhoneNumber.setStyle("-fx-text-fill: green");
            lblPhoneNumber.setText("ContactNumber is eligible✔︎");
            RegexUtil.resetStyle(txtContactNumber);
        } else if (!phoneNumber.matches(phoneNumberRegex)){
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber must be 10 numbers and start with \"0\"");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        }

        isSaveEnable();



    }



    @FXML
    void txtEmailAddressOnKeyPressed(KeyEvent event) {
        if (txtEmailAddress.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtEmailAddress);
                txtContactNumber.requestFocus();
                txtContactNumber.positionCaret(txtContactNumber.getText().length());
            }
        }if (event.getCode() == KeyCode.ENTER) {
            txtUserName.requestFocus();
            txtUserName.positionCaret(txtUserName.getText().length());
        }

    }



    @FXML
    private void txtEmailAddressOnKeyTyped(KeyEvent event) {
        email = txtEmailAddress.getText();
        ArrayList<UserDto> users = userModel.getAllUsers();
        ArrayList<String> userEmails = new ArrayList<>();

        for (UserDto userDto : users) {
            userEmails.add(userDto.getUsEmail());
        }


        if (email.isEmpty()) {
            lblEmail.setText("");
            RegexUtil.resetStyle(txtEmailAddress);
            return;
        }

        boolean contactExists = false;
        for (String existingEmailAddress : userEmails) {
            if (existingEmailAddress.equals(email)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email Address already exists!");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        } else if (email.matches(emailRegex)) {
            lblEmail.setStyle("-fx-text-fill: green");
            lblEmail.setText("Email Address is eligible✔︎");
            RegexUtil.resetStyle(txtEmailAddress);
        } else if (!email.matches(emailRegex)){
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email must start with letters, numbers, or underscores follow '@' ");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        }

        isSaveEnable();


    }



    @FXML
    void txtNewPasswordOnKeyPressed(KeyEvent event) {
        if (txtNewPassword.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                lblNewPassword.setText("");
                llblConfirmPassword.setText("");
                RegexUtil.resetStyle(txtNewPassword,txtConfirmPassword);
                txtUserName.requestFocus();
                txtUserName.positionCaret(txtUserName.getText().length());
            }
        }else if (!txtConfirmPassword.isDisable()) {
            if (event.getCode() == KeyCode.ENTER) {
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.positionCaret(txtConfirmPassword.getText().length());
            }
        }
    }



    @FXML
    private void txtNewPasswordOnKeyTyped(KeyEvent event) {
        newPassword = txtNewPassword.getText();

        RegexUtil.resetStyle(txtNewPassword);

        if (newPassword.isEmpty()) {
            lblNewPassword.setText(" ");
            llblConfirmPassword.setText(" ");
            txtConfirmPassword.clear();
            txtConfirmPassword.setDisable(true);
        }

        else if (newPassword.length() > 13) {
            RegexUtil.setErrorStyle(true, txtNewPassword);
            lblNewPassword.setText("Password is too long! > 13");
            lblNewPassword.setTextFill(Color.RED);
            txtConfirmPassword.setDisable(true);
        }

        else if (newPassword.matches(passwordStrongRegex)) {
            lblNewPassword.setText("Strong password ✔︎");
            lblNewPassword.setTextFill(Color.GREEN);
            txtConfirmPassword.setDisable(false);
            llblConfirmPassword.setText("Please confirm your password ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #4a4848");
        }

        else if (newPassword.matches(passwordMediumRegex)) {
            lblNewPassword.setText("Medium password ⚠︎");
            lblNewPassword.setTextFill(Color.BROWN);
            txtConfirmPassword.setDisable(false);
            llblConfirmPassword.setText("Please confirm your password ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #4a4848");
        }

        else if (newPassword.matches(passwordWeakRegex)) {
            lblNewPassword.setText("Weak password ⛔");
            lblNewPassword.setTextFill(Color.RED);
            txtConfirmPassword.setDisable(true);
            llblConfirmPassword.setText("Your new password should be medium or strong for confirmation ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #ff0000");
        }

        else if (!txtConfirmPassword.getText().isEmpty() && !txtNewPassword.getText().isEmpty()) {
            txtConfirmPassword.clear();
            llblConfirmPassword.setText("Your new password should be medium or strong for confirmation ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #ff0000");


            if (newPassword.matches(passwordWeakRegex)) {
                txtConfirmPassword.setDisable(false);
            }
        }

        isSaveEnable();

    }



    @FXML
    void txtUserNameOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            txtNewPassword.requestFocus();
            txtNewPassword.positionCaret(txtNewPassword.getText().length());
        }
    }



    @FXML
    void txtUserNameOnKeyTyped(KeyEvent event) {
        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
        btnReset.setDisable(false);
        userName = txtUserName.getText();
        ArrayList<UserDto> users = userModel.getAllUsers();
        ArrayList<String> userNames = new ArrayList<>();

        for (UserDto userDto : users) {
            userNames.add(userDto.getUsName());
        }

        if (userName.isEmpty()) {
            lblUserNameStatus.setStyle("-fx-text-fill: #4a4848");
            lblUserNameStatus.setText("Username should be Unique");
            RegexUtil.resetStyle(txtUserName);
            return;
        }

        boolean userExists = false;
        for (String existingUserName : userNames) {
            if (existingUserName.equals(userName)) {
                userExists = true;
                break;
            }
        }

        if (userExists) {
            lblUserNameStatus.setStyle("-fx-text-fill: red");
            lblUserNameStatus.setText("Username already exists!");
            RegexUtil.setErrorStyle(false, txtUserName);
        } else if (userName.matches(usernameRegex)) {
            lblUserNameStatus.setStyle("-fx-text-fill: green");
            lblUserNameStatus.setText("Username eligible✔︎");
            RegexUtil.resetStyle(txtUserName);
        } else if (!userName.matches(usernameRegex)){
            lblUserNameStatus.setStyle("-fx-text-fill: red");
            lblUserNameStatus.setText("Username must be between 5 and 10 characters long, starting with a letter and ending with a letter or number.");
            RegexUtil.setErrorStyle(false, txtUserName);
        }

        isSaveEnable();

    }



    @FXML
    private void btnResetOnAction(ActionEvent actionEvent) {
        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
        lblUserNameStatus.setText("Username should be Unique");
        lblNewPassword.setText("");
        llblConfirmPassword.setText("");
        lblPhoneNumber.setText("");
        lblEmail.setText("");
        RegexUtil.resetStyle(txtUserName,txtConfirmPassword,txtNewPassword,txtContactNumber,txtEmailAddress);
        refreshPage();
    }



    private void refreshPage()  {
        txtUserName.requestFocus();
        loadUserTable();

        txtUserName.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");

        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnSendMailUser.setDisable(true);
    }



    private void loadUserTable()  {
        ArrayList<UserDto> userDtos = userModel.getAllUsers();
        ObservableList<UserTm> userTms = FXCollections.observableArrayList();


        for (UserDto userDto : userDtos) {
            UserTm userTm = new UserTm(
                    userDto.getUsName(),
                    userDto.getUsPassword(),
                    userDto.getUsPhone(),
                    userDto.getUsEmail()
            );
            userTms.add(userTm);
        }
        tblUser.setItems(userTms);
    }



    private void isSaveEnable(){
        boolean isUserNameValid = userName != null && !userName.isEmpty() && userName.matches(usernameRegex);
        boolean isPasswordValid = txtConfirmPassword.getText() != null && !txtConfirmPassword.getText().isEmpty()
                && txtConfirmPassword.getText().equals(txtNewPassword.getText())
                && (newPassword != null && (newPassword.matches(passwordStrongRegex) || newPassword.matches(passwordMediumRegex)));
        boolean isContactNumberValid = phoneNumber != null && !phoneNumber.isEmpty() && phoneNumber.matches(phoneNumberRegex);
        boolean isEmailValid = txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty() && txtEmailAddress.getText().matches(emailRegex);

        btnSave.setDisable(!(isUserNameValid && isPasswordValid && isContactNumberValid && isEmailValid));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUserName.requestFocus();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("usName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("usPassword"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("usEmail"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("usPhone"));

//        colPassword.setCellFactory(new Callback<TableColumn<UserTm, String>, TableCell<UserTm, String>>() {
//            @Override
//            public TableCell<UserTm, String> call(TableColumn<UserTm, String> column) {
//                return new TableCell<UserTm, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        // this method in the Table cell class
//                        // used for update the cell in the table
//                        super.updateItem(item, empty);
//                        if (empty || item == null) {
//                            setText(null);  // If cell is empty or item is null, clear the text.
//                        } else {
//                            setText("*".repeat(item.length()));  // Display '*' for password characters.
//                        }
//                    }
//                };
//            }
//        });




        refreshPage();


    }

}
