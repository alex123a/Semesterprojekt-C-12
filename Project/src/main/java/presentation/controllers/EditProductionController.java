package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import javafx.collections.ObservableList;
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
import presentation.NewRightsholder;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ListView<IRightsholder> rightholderListview;

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

    String oldId = null;

    @FXML
    void OnClickedSaveChanges(ActionEvent event) {
        CreditsSystem.getInstance().setProductionID(toEdit, programIDField.getText());
        CreditsSystem.getInstance().setName(toEdit, programNameField.getText());
        CreditsSystem.getInstance().saveChanges();

        if (!oldId.equals(programIDField.getText())) {
            CreditsSystem.getInstance().deleteProduction(CreditsSystem.getInstance().getProduction(oldId));
        }

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
        String name = null;
        String description = null;
        List<String> roles = new ArrayList<>();

        /*
        TODO The checks for null values should be made in domain layer and not in presentation layer
            will be changed in iteration 2 if we run out of time in iteration 1
        */

        if (rightholderName.getText() != null || !rightholderName.getText().trim().isEmpty()) {
            name = rightholderName.getText();
        }

        if (rightholderDescription.getText() != null || !rightholderDescription.getText().trim().isEmpty()) {
            description = rightholderDescription.getText();
        }

        if (rightholderRoles.getText() != null || !rightholderRoles.getText().trim().isEmpty()) {
            roles.addAll(Arrays.asList(rightholderRoles.getText().split(",")));
        }

        if (name != null && description != null) {
            IRightsholder newRightsholder = new NewRightsholder(name, description, roles);
            ObservableList<IRightsholder> rightholders = rightholderListview.getItems();
            rightholders.add(newRightsholder);
        }
    }

    @FXML
    void onClickedRemoveRightholder(ActionEvent event) {
        rightholderListview.getItems().remove(rightholderListview.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toEdit = Repository.getInstance().getToEdit();
        programIDField.setText(toEdit.getProductionID());
        oldId = toEdit.getProductionID();
        programNameField.setText(toEdit.getName());

        //Doesn't work because of the persistence-layer
        List<IRightsholder> credits = new ArrayList<>();
        for (IRightsholder rh : toEdit.getRightsholders().keySet()){
            credits.add(new NewRightsholder(rh.getName(), rh.getDescription(), toEdit.getRightsholders().get(rh)));
            System.out.println("read one");
        }
        rightholderListview.getItems().setAll(credits);
    }
}
