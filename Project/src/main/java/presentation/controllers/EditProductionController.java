package presentation.controllers;

import Interfaces.IProduction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import presentation.Credit;
import presentation.Repository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditProductionController implements Initializable {

    @FXML
    private TextArea descriptionProgramArea;

    @FXML
    private TextField programIDField;

    @FXML
    private TextField programNameField;

    @FXML
    private ListView<?> rightholderListview;

    @FXML
    private TextField rightholderName;

    @FXML
    private TextField rightholderDescription;

    @FXML
    private TextField rightholderRoles;

    @FXML
    private Button addRightholderBut;

    @FXML
    private Button addProductionBut;

    @FXML
    private Button addRightholderBut1;

    IProduction toEdit;

    List<Credit> credits;

    @FXML
    void OnClickedSaveChanges(ActionEvent event) {

    }

    @FXML
    void onClickedAddRightholder(ActionEvent event) {

    }

    @FXML
    void onClickedRemoveRightholder(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toEdit = Repository.getInstance().getToEdit();
        programIDField.setText(toEdit.getProductionID());
        programNameField.setText(toEdit.getName());
    }
}
