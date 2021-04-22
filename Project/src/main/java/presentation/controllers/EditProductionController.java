package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.Credit;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ListView<Credit> rightholderListview;

    @FXML
    private TextField rightholderName;

    @FXML
    private TextField rightholderDescription;

    @FXML
    private TextField rightholderRoles;

    @FXML
    private Button addRightholderBut;

    @FXML
    private Button saveChangesBut;

    @FXML
    private Button removeRightholderBut;

    IProduction toEdit;

    List<Credit> credits;

    @FXML
    void OnClickedSaveChanges(ActionEvent event) {
        CreditsSystem.getInstance().setProductionID(toEdit, programIDField.getText());
        CreditsSystem.getInstance().setName(toEdit, programNameField.getText());
        CreditsSystem.getInstance().saveChanges();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
            Stage window = Repository.getInstance().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        //Doesn't work because of the persistence-layer
        List<Credit> credits = new ArrayList<>();
        for (IRightsholder rh : toEdit.getRightsholders().keySet()){
            credits.add(new Credit(rh, toEdit.getRightsholders().get(rh)));
            System.out.println("read one");
        }
        rightholderListview.getItems().setAll(credits);
    }
}
