        package lk.ijse.gdse.instritutefirstsemfinal.controller;

        import com.jfoenix.controls.JFXComboBox;
        import javafx.collections.ListChangeListener;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.Pane;
        import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
        import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
        import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
        import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
        import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
        import org.controlsfx.control.CheckComboBox;

        import java.net.URL;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.ResourceBundle;

        public class StudentFormController implements Initializable {

            private StudentTableFormController studentTableFormController;
            StudentModel studentModel = new StudentModel();
            GradeModel gradeModel = new GradeModel();

            public void setStudentTableFormController(StudentTableFormController studentTableFormController) {
                this.studentTableFormController = studentTableFormController;
            }

            @FXML
            private Pane StudentPane;

            @FXML
            private Button btnDelete;

            @FXML
            private Button btnReset;

            @FXML
            private Button btnSave;

            @FXML
            private Button btnUpdate;

            @FXML
            private CheckComboBox<String> checkCBoxSubject;

            @FXML
            private JFXComboBox<String> cmbGrade;

            @FXML
            private DatePicker dpDOB;

            @FXML
            private Label lblAddress;

            @FXML
            private Label lblDOB;

            @FXML
            private Label lblEmail;

            @FXML
            private Label lblFee;

            @FXML
            private Label lblName;

            @FXML
            private Label lblParentName;

            @FXML
            private Label lblPhoneNumber;

            @FXML
            private Label lblStudentID;

            @FXML
            private TextField txtAddress;

            @FXML
            private TextField txtEmail;

            @FXML
            private TextField txtFee;

            @FXML
            private TextField txtParentName;

            @FXML
            private TextField txtPhoneNumber;

            @FXML
            private TextField txtName;

            private String nameRegex = "^[A-Za-z]+(\\.[A-Za-z]+)*(\\s[A-Za-z]+)*$";
            private String phoneNumberRegex = "^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$";
            private String emailRegex = "[\\w]*@*[a-z]*\\.*[\\w]{5,}(\\.)*(com)*(@gmail\\.com)";
            private String feeRegex = "^\\d+(\\.\\d{1,2})?$";

            String id;
            String name;
            String parentName;
            String phoneNumber;
            String email;
            double fee;
            String address;
            LocalDate dob;
            String grade;
            String [] subjects;



            @FXML
            void btnDeleteOnAction(ActionEvent event) {

            }

            @FXML
            void btnResetOnAction(ActionEvent event) {
                refreshPage();
            }

            @FXML
            void btnSaveOnAction(ActionEvent event) {
                ObservableList<String> selectedItems = checkCBoxSubject.getCheckModel().getCheckedItems();



            }

            @FXML
            void btnUpdateOnAction(ActionEvent event) {

            }

            @FXML
            void checkCBoxSubjectOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void cmbGradeOnAction(ActionEvent event) {
                String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();
                if (selectedGrade != null && !selectedGrade.isEmpty()) {
                    grade = selectedGrade;
                    System.out.println(grade);
                    btnReset.setDisable(false);

                    // Clear previous subjects
                    checkCBoxSubject.getItems().clear();
                    String gradeID = gradeModel.getGradeIdFromName(selectedGrade);
                    ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

                    if (subjects != null) {
                        checkCBoxSubject.getItems().addAll(subjects);
                    }
                } else {
                    btnReset.setDisable(true);
                }

            }






            @FXML
            void txtAddressOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtAddressOnKeyTyped(KeyEvent event) {
                address = txtAddress.getText();

            }

            @FXML
            void txtEmailOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtEmailOnKeyTyped(KeyEvent event) {
                email = txtEmail.getText();
                btnReset.setDisable(false);


                if (email.isEmpty()) {
                    btnReset.setDisable(true);
                    lblEmail.setText("");
                    RegexUtil.resetStyle(txtEmail);

                }else {
                      if (email.matches(emailRegex)) {
                          lblEmail.setText("");
                          RegexUtil.resetStyle(txtEmail);
                      }else if (!email.matches(emailRegex)) {
                          lblEmail.setStyle("-fx-text-fill: red");
                          lblEmail.setText("Email must start with letters, numbers, or underscores follow '@' ");
                          RegexUtil.setErrorStyle(false, txtEmail);
                      }



                }

            }

            @FXML
            void txtFeeOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtFeeOnKeyTyped(KeyEvent event) {
                String stringFee =  txtFee.getText().trim();
                RegexUtil.resetStyle(txtFee);
                btnReset.setDisable(false);

               if (stringFee.isEmpty()) {
                   btnReset.setDisable(true);
                   lblFee.setText("");
               }else{
                   if (stringFee.matches(feeRegex)) {
                       fee = Double.parseDouble(stringFee);
                       lblFee.setText("");
                       RegexUtil.resetStyle(txtFee);
                   }else if (!stringFee.matches(feeRegex)) {
                       lblFee.setStyle("-fx-text-fill: red");
                       RegexUtil.setErrorStyle(false, txtFee);
                       lblFee.setText("Invalid fee");

                   }
               }
            }

            @FXML
            void txtParentNameOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtParentNameOnKeyTyped(KeyEvent event) {
                parentName = txtParentName.getText().trim();
                btnSave.setDisable(false);
                lblParentName.setStyle("-fx-text-fill: #4a4848");
                RegexUtil.resetStyle(txtParentName);

                if (parentName.isEmpty()) {
                    btnReset.setDisable(true);
                    lblParentName.setText(" ");
                    RegexUtil.resetStyle(txtParentName);
//                    checkFieldsEmpty();
                }else {
                    btnReset.setDisable(false);
                    if (!parentName.matches(nameRegex)) {
                        lblParentName.setStyle("-fx-text-fill: red");
                        RegexUtil.setErrorStyle(false,txtParentName);
                        lblParentName.setText("Enter a valid name: use letters only, with optional dots or spaces");
                    }else {
                        lblParentName.setText(" ");
                        RegexUtil.resetStyle(txtParentName);}
                }
//                isSaveEnable();

            }

            @FXML
            void txtPhoneNumberOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtPhoneNumberOnKeyTyped(KeyEvent event) {
                phoneNumber = txtPhoneNumber.getText();
                btnReset.setDisable(false);
                lblPhoneNumber.setStyle("-fx-text-fill: #4a4848;");



                if (phoneNumber.isEmpty()) {
                    btnReset.setDisable(true);
                    lblPhoneNumber.setText("");
                    RegexUtil.resetStyle(txtPhoneNumber);
//                   checkFieldsEmpty();
                    return;
                }

                if (phoneNumber.matches(phoneNumberRegex)) {
                    lblPhoneNumber.setText("");
                    RegexUtil.resetStyle(txtPhoneNumber);
                } else if (!phoneNumber.matches(phoneNumberRegex)) {
                    lblPhoneNumber.setStyle("-fx-text-fill: red");
                    lblPhoneNumber.setText("Invalid Mobile Number");
                    RegexUtil.setErrorStyle(false, txtPhoneNumber);
                }
//               isSaveEnable();

            }

            @FXML
            void txtNameOnKeyPressed(KeyEvent event) {
            }

            @FXML
            void txtNameOnkeyTyped(KeyEvent event) {
                name = txtName.getText().trim();
                btnSave.setDisable(false);
                lblName.setStyle("-fx-text-fill: #4a4848");
                RegexUtil.resetStyle(txtName);

                if (name.isEmpty()) {
                    btnReset.setDisable(true);
                    lblName.setText(" ");
                    RegexUtil.resetStyle(txtName);
//                    checkFieldsEmpty();
                }else {
                    btnReset.setDisable(false);
                    if (!name.matches(nameRegex)) {
                        lblName.setStyle("-fx-text-fill: red");
                        RegexUtil.setErrorStyle(false,txtName);
                        lblName.setText("Enter a valid name: use letters only, with optional dots or spaces");
                    }else {
                        lblName.setText(" ");
                        RegexUtil.resetStyle(txtName);}
                }
//                isSaveEnable();

            }


            public void refreshPage(){
                String studentID = studentModel.getNextStudentID();
                lblStudentID.setText(studentID);

                checkCBoxSubject.getItems().clear();
                checkCBoxSubject.getCheckModel().clearChecks();  // Clears all selections

                ArrayList<GradeDto> grades = gradeModel.getGrades();
                cmbGrade.getItems().clear();
                if (grades != null) {
                    for (GradeDto grade : grades) {
                        String gradeName = grade.getGradeName();
                        if (gradeName != null && !gradeName.isEmpty()) {
                            cmbGrade.getItems().add(gradeName);
                        }
                    }
                }

                ArrayList<Label> labels = new ArrayList<>(Arrays.asList(lblAddress, lblName, lblDOB, lblFee, lblParentName, lblPhoneNumber, lblEmail));
                for (Label label : labels) {
                    label.setText("");
                }

                txtEmail.setText("");
                txtAddress.setText("");
                txtPhoneNumber.setText("");
                txtName.setText("");
                txtParentName.setText("");
                txtFee.setText("");

                dpDOB.setValue(null);

                RegexUtil.resetStyle(txtParentName, txtAddress, txtEmail, txtPhoneNumber, txtName, txtFee);

                // Disable buttons
                btnDelete.setDisable(true);
                btnReset.setDisable(true);
                btnSave.setDisable(true);
                btnUpdate.setDisable(true);
            }







            @Override
            public void initialize(URL url, ResourceBundle resourceBundle) {
                refreshPage();

            }

            public void dpDOBOnAction(ActionEvent actionEvent) {
                dob = dpDOB.getValue();

            }
        }




