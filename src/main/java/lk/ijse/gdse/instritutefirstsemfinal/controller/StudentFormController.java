        package lk.ijse.gdse.instritutefirstsemfinal.controller;

        import com.jfoenix.controls.JFXComboBox;
        import javafx.collections.ListChangeListener;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.Pane;
        import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
        import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
        import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
        import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
        import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
        import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
        import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
        import org.controlsfx.control.CheckComboBox;

        import java.net.URL;
        import java.time.LocalDate;
        import java.util.*;

        public class StudentFormController implements Initializable {

            private StudentTableFormController studentTableFormController;
            StudentModel studentModel = new StudentModel();
            GradeModel gradeModel = new GradeModel();
            SubjectModel subjectModel = new SubjectModel();

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



            @FXML
            void btnDeleteOnAction(ActionEvent event) {

                // Show confirmation alert to the user
                Optional<ButtonType> buttonType = AlertUtil.ConfirmationAlert("Are you sure you want to delete this Student?", ButtonType.NO, ButtonType.YES);
                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                    // Call deleteStudent method to perform the deletion
                    boolean isDeleted = studentModel.deleteStudent(lblStudentID.getText());

                    if (isDeleted) {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Student deleted successfully.");
                        refreshPage();
                        studentTableFormController.loadTable();  // Update student table
                    } else {
                        // Notify the user of failure
                        AlertUtil.informationAlert(UserFormController.class, null, true, "Student could not be deleted!");
                    }
                }
            }

            @FXML
            void btnResetOnAction(ActionEvent event) {
                refreshPage();
            }

            @FXML
            void btnSaveOnAction(ActionEvent event) {
                String id = lblStudentID.getText();
                LocalDate birthday = dpDOB.getValue();
                String name = txtName.getText();
                String parentName = txtParentName.getText();
                String phoneNumber = txtPhoneNumber.getText().trim();
                System.out.println("Phone Number: [" + phoneNumber + "] Length: " + phoneNumber.length());
                String email = txtEmail.getText();
                String address = txtAddress.getText();
                String grade = cmbGrade.getValue(); // Ensure cmbGrade returns a valid grade ID
                ObservableList<String> selectedItems = checkCBoxSubject.getCheckModel().getCheckedItems();
                String admin = LoginFormController.uName;

                double fee;
                try {
                    fee = Double.parseDouble(txtFee.getText());
                } catch (NumberFormatException e) {
                    AlertUtil.errorAlert(this.getClass(), null, "Invalid fee. Please enter a numeric value.");
                    return;
                }

                // Field validation
                if (id.isEmpty() || name.isEmpty() || parentName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()
                        || address.isEmpty() || grade == null) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "All fields must be filled!");
                    return;
                }

                if (selectedItems.isEmpty()) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Please select at least one subject.");
                    return;
                }

                // Get subject IDs
                List<String> subjectIds = subjectModel.getSubjectIdsFromNames(new ArrayList<>(selectedItems));
                if (subjectIds.isEmpty()) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Invalid subject selection.");
                    return;
                }


                String[] subjectArray = subjectIds.toArray(new String[0]);

                String gradeID = gradeModel.getGradeIdFromName(grade);

                StudentDto studentDto = new StudentDto(id, birthday, name, fee, parentName, email, phoneNumber, address, admin, gradeID, subjectArray);


                boolean isSaved = studentModel.saveStudent(studentDto, subjectIds);

                if (isSaved) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Student Saved Successfully!");
                    refreshPage();
                    studentTableFormController.loadTable();
                } else {
                    AlertUtil.errorAlert(this.getClass(), null, "Student Save Failed.");
                }
            }


            @FXML
            void btnUpdateOnAction(ActionEvent event) {
                String id = lblStudentID.getText();
                LocalDate birthday = dpDOB.getValue();
                String name = txtName.getText();
                String parentName = txtParentName.getText();
                String phoneNumber = txtPhoneNumber.getText().trim();
                String email = txtEmail.getText();
                String address = txtAddress.getText();
                String grade = cmbGrade.getValue();
                ObservableList<String> selectedItems = checkCBoxSubject.getCheckModel().getCheckedItems();
                String admin = LoginFormController.uName;

                double fee;
                try {
                    fee = Double.parseDouble(txtFee.getText());
                } catch (NumberFormatException e) {
                    AlertUtil.errorAlert(this.getClass(), null, "Invalid fee. Please enter a numeric value.");
                    return;
                }

                if (id.isEmpty() || name.isEmpty() || parentName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()
                        || address.isEmpty() || grade == null || birthday == null) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "All fields must be filled!");
                    return;
                }

                if (selectedItems.isEmpty()) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Please select at least one subject.");
                    return;
                }

                List<String> subjectIds = subjectModel.getSubjectIdsFromNames(new ArrayList<>(selectedItems));
                if (subjectIds.isEmpty()) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Invalid subject selection.");
                    return;
                }

                String[] subjectArray = subjectIds.toArray(new String[0]);

                String gradeID = gradeModel.getGradeIdFromName(grade);

                StudentDto studentDto = new StudentDto(id, birthday, name, fee, parentName, email, phoneNumber, address, admin, gradeID, subjectArray);

                ArrayList<StudentDto> students = studentModel.getStudentsById(lblStudentID.getText());

                for (StudentDto studentDto1 : students) {
                    // Check if all fields (except ID) match
                    boolean isSameGrade = studentDto1.getGrade().equals(grade);
                    boolean isSameName = studentDto1.getName().equals(name);
                    boolean isSameParentName = studentDto1.getParentName().equals(parentName);
                    boolean isSamePhone = studentDto1.getPhoneNumber().equals(phoneNumber);
                    boolean isSameEmail = studentDto1.getEmail().equals(email);
                    boolean isSameAddress = studentDto1.getAddress().equals(address);
                    boolean isSameDOB = studentDto1.getBirthday().equals(birthday);
                    boolean isSameSubjects = Arrays.equals(studentDto1.getSubjects(), selectedItems.toArray(new String[0]));




                    if (isSameGrade && isSameName && isSameParentName && isSamePhone && isSameEmail && isSameAddress && isSameSubjects && isSameDOB ) {
                        AlertUtil.informationAlert(this.getClass(), null, true, "No changes detected. Update is not necessary.");
                        return;
                    }
                }

                // Update the student
                boolean isUpdated = studentModel.updateStudent(studentDto, subjectIds);

                if (isUpdated) {
                    AlertUtil.informationAlert(this.getClass(), null, true, "Student Updated Successfully!");
                    refreshPage();
                    studentTableFormController.loadTable();
                } else {
                    AlertUtil.errorAlert(this.getClass(), null, "Student Update Failed.");
                }
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

                    checkCBoxSubject.getItems().clear();
                    String gradeID = gradeModel.getGradeIdFromName(selectedGrade);
                    ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

                    if (subjects != null) {
                        checkCBoxSubject.getItems().addAll(subjects);
                        isResetEnable();
                    }
                } else {
                    btnReset.setDisable(true);
                }
                isSaveEnabled();


            }






            @FXML
            void txtAddressOnKeyPressed(KeyEvent event) {

            }

            @FXML
            void txtAddressOnKeyTyped(KeyEvent event) {
                address = txtAddress.getText();
                isSaveEnabled();
                isResetEnable();



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
                    isResetEnable();
                    return;
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
                isSaveEnabled();


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
                   isResetEnable();

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
                isSaveEnabled();

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
                    isResetEnable();

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
                isSaveEnabled();


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
                    isResetEnable();

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
                isSaveEnabled();


            }

            @FXML
            void txtNameOnKeyPressed(KeyEvent event) {
            }

            @FXML
            void txtNameOnkeyTyped(KeyEvent event) {
                name = txtName.getText().trim();
                btnReset.setDisable(false);
                lblName.setStyle("-fx-text-fill: #4a4848");
                RegexUtil.resetStyle(txtName);

                if (name.isEmpty()) {
                    btnReset.setDisable(true);
                    lblName.setText(" ");
                    RegexUtil.resetStyle(txtName);
//                    checkFieldsEmpty();
                    isResetEnable();

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
                isSaveEnabled();
            }


            public void refreshPage() {
                String studentID = studentModel.getNextStudentID();
                lblStudentID.setText(studentID);



              checkCBoxSubject.getCheckModel().clearChecks();
                checkCBoxSubject.getItems().clear();// Clears all selections

                ArrayList<GradeDto> grades = gradeModel.getGrades();
                cmbGrade.getItems().clear();
                if (grades != null && !grades.isEmpty()) {
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

                btnSave.setVisible(true);
                btnDelete.setDisable(true);
                btnReset.setDisable(true);
                btnSave.setDisable(true);
                btnUpdate.setDisable(true);
            }








            @Override
            public void initialize(URL url, ResourceBundle resourceBundle) {
                refreshPage();

                cmbGrade.valueProperty().addListener((observable, oldValue, newValue) -> {
                    isSaveEnabled();
                    isResetEnable();
                });

                // Listener for DatePicker (DOB)
                dpDOB.valueProperty().addListener((observable, oldValue, newValue) -> {
                    isSaveEnabled();
                    isResetEnable();
                });

                // Listener for CheckListView (Subject Checkboxes)
                checkCBoxSubject.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) change -> {
                    isSaveEnabled();
                    isResetEnable();
                });
            }

            public void dpDOBOnAction(ActionEvent actionEvent) {
                dob = dpDOB.getValue();

                if (dob != null) {
                    btnReset.setDisable(false);
                }

                System.out.println(dob);

                isSaveEnabled();


            }


            public void setStudentDto(StudentDto studentDto) {
                lblStudentID.setText(studentDto.getId());
                txtName.setText(studentDto.getName());
                txtEmail.setText(studentDto.getEmail());
                txtAddress.setText(studentDto.getAddress());
                txtPhoneNumber.setText(studentDto.getPhoneNumber());
                txtFee.setText(String.valueOf(studentDto.getAdmissionFee()));
                dpDOB.setValue(studentDto.getBirthday());
                cmbGrade.setValue(studentDto.getGrade());
                txtParentName.setText(studentDto.getParentName());

                String gradeID = gradeModel.getGradeIdFromName(studentDto.getGrade());

                ArrayList<String> subjects = gradeModel.getSubjectsByGradeId(gradeID);

                checkCBoxSubject.getItems().clear();
                if (subjects != null) {
                    checkCBoxSubject.getItems().addAll(subjects);
                }


                if (!checkCBoxSubject.getCheckModel().getCheckedItems().isEmpty()) {
                    checkCBoxSubject.getCheckModel().clearChecks();
                }




                String[] subjectGrades = studentDto.getSubjects();


                if (subjectGrades != null) {
                    for (String grade : subjectGrades) {
                        if (checkCBoxSubject.getItems().contains(grade)) {
                            checkCBoxSubject.getCheckModel().check(grade);
                        }
                    }
                }
            }

            public void isSaveEnabled() {
                // Check if the student ID is empty
                boolean checkID = lblStudentID != null && lblStudentID.getText().isEmpty();

                // Check if the name matches the regex
                boolean checkName = txtName != null && !txtName.getText().matches(nameRegex);

                // Check if the email matches the regex
                boolean checkEmail = txtEmail != null && !txtEmail.getText().matches(emailRegex);

                // Check if the address is empty
                boolean checkAddress = txtAddress != null && txtAddress.getText().isEmpty();

                // Check if the phone number matches the regex
                boolean checkPhoneNumber = txtPhoneNumber != null && !txtPhoneNumber.getText().matches(phoneNumberRegex);

                // Check if the fee matches the regex
                boolean checkFee = txtFee != null && !txtFee.getText().matches(feeRegex);

                // Check if the date of birth is not selected
                boolean checkDOB = dpDOB != null && dpDOB.getValue() == null;

                // Check if the grade has not been selected
                boolean checkGrade = cmbGrade.getValue() == null;

                // Check if at least one subject checkbox is selected
                boolean checkGrades = checkCBoxSubject != null
                        && !checkCBoxSubject.getCheckModel().getCheckedItems().isEmpty();

                // Disable the save button if any condition is not met
                btnSave.setDisable(checkID || checkName || checkEmail || checkAddress
                        || checkPhoneNumber || checkFee || checkDOB || checkGrade || !checkGrades);
            }

            public void isResetEnable() {
                // Check if Name, Description, or ComboBox has any value
                boolean isCheckName = txtName != null && !txtName.getText().isEmpty();
                boolean isCheckDescription = txtAddress != null && !txtAddress.getText().isEmpty();
                boolean isCheckComboBox = cmbGrade != null && cmbGrade.getValue() != null;
                boolean isCheckCBoxSubject = checkCBoxSubject != null && !checkCBoxSubject.getCheckModel().getCheckedItems().isEmpty();

                // Enable the reset button if any field has input
                btnReset.setDisable(!(isCheckName || isCheckDescription || isCheckComboBox || isCheckCBoxSubject));
            }

            public void tableClickButton(){
                btnSave.setVisible(false);
                btnReset.setDisable(false);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);


                RegexUtil.resetStyle(txtName, txtAddress, txtEmail, txtPhoneNumber,txtFee,txtParentName);
                lblName.setText("");
                lblParentName.setText("");
                lblPhoneNumber.setText("");
                lblAddress.setText("");
                lblFee.setText("");
                lblDOB.setText("");
                lblEmail.setText("");
            }




        }




