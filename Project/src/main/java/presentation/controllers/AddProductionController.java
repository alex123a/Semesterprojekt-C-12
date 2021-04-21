package presentation.controllers;

import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import presentation.NewRightsholder;

public class AddProductionController {

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
    private Button addProductionBut;

    private CreditsSystem creditsSystem = CreditsSystem.getInstance();

    @FXML
    public void onClickedAddRightholder(ActionEvent event) {
        String name = null;
        String description = null;

        if (name != null && description != null) {
            NewRightsholder newRightsholder = new NewRightsholder(name, description, null);
        }

        ObservableList<IRightsholder> rightholders = rightholderListview.getItems();
        //rightholders.add(new NewRightsholder);


    }

    @FXML void onClickedAddProduction(ActionEvent event) {

    }

}