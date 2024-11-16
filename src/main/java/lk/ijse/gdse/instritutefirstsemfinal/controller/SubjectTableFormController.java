//package lk.ijse.gdse.instritutefirstsemfinal.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
//import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
//import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.ResourceBundle;
//
//public class SubjectTableFormController implements Initializable {
//
//    private SubjectModel model = new SubjectModel();
//
//    // Reference to SubjectFormController
//    private SubjectFormController subjectFormController;
//
//    // Setter method to inject SubjectFormController into this controller
//    public void setSubjectFormController(SubjectFormController subjectFormController) {
//        this.subjectFormController = subjectFormController;
//    }
//
//    @FXML
//    private TableColumn<SubjectTm, String> colDesc;
//
//    @FXML
//    private TableColumn<SubjectTm, String> colGrade;
//
//    @FXML
//    private TableColumn<SubjectTm, String> colSUbID;
//
//    @FXML
//    private TableColumn<SubjectTm, String> colSUbName;
//
//    @FXML
//    private TableView<SubjectTm> tblSubject;
//
////    @FXML
////    private void tblUserOnClicked(MouseEvent event) {
////        // Get the selected row from the table
////        SubjectTm selectedItem = tblSubject.getSelectionModel().getSelectedItem();
////
////        // If a row is selected, pass the data to the SubjectFormController
////        if (selectedItem != null) {
////            // Create a SubjectDto from the selected row
////            SubjectDto dto = new SubjectDto(
////                    selectedItem.getSubjectId(),
////                    selectedItem.getSubjectName(),
////                    selectedItem.getSubjectGrades(),
////                    selectedItem.getSubjectDescription()
////            );
////
////            if (subjectFormController != null) {
////                subjectFormController.setSubjectDto(dto);
////
////                // Enable or disable buttons based on selection
////                subjectFormController.enableUpdateButton(true);
////                subjectFormController.enableDeleteButton(true);
////                subjectFormController.enableResetButton(true);
////                subjectFormController.enableSaveButton(false);  // Disable Save if updating
////            }
////        } else {
////            if (subjectFormController != null) {
////                subjectFormController.enableUpdateButton(false);
////                subjectFormController.enableDeleteButton(false);
////                subjectFormController.enableResetButton(true); // Reset should still be enabled
////                subjectFormController.enableSaveButton(true);  // Enable Save for new entries
////            }
////        }
////    }
//
//
//
////    public void loadSubjectTable(){
////        ArrayList<SubjectDto> subjectDtos = model.getAllSubjects();
////        ObservableList<SubjectTm> subjectTms = FXCollections.observableArrayList();
////
////        for (SubjectDto subjectDto : subjectDtos) {
////            SubjectTm subjectTm = new SubjectTm(
////                    subjectDto.getSubjectId(),
////                    subjectDto.getSubjectName(),
////                    subjectDto.getSubjectGrades(),
////                    subjectDto.getSubjectDescription()
////
////            );
////            subjectTms.add(subjectTm);
////        }
////        tblSubject.setItems(subjectTms);
////
////
////
////    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        colSUbID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
//        colSUbName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
//        colDesc.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));
//        colGrade.setCellValueFactory(new PropertyValueFactory<>("subjectGrades"));
//
////        loadSubjectTable();
//
//    }
//}
