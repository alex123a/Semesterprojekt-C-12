package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.CreditsManagement.CreditsSystem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import presentation.NewProduction;
import presentation.NewRightsholder;

import java.util.*;

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

    @FXML
    private Button removeRightholder;

    private CreditsSystem creditsSystem = CreditsSystem.getInstance();

    @FXML
    public void onClickedAddRightholder(ActionEvent event) {
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
    public void onClickedRemoveRightholder(ActionEvent event) {
        rightholderListview.getItems().remove(rightholderListview.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onClickedAddProduction(ActionEvent event) {
        /*
        TODO For now we ignore the description since it's not made in the other layers yet, because we forgot it
            will be added in iteration 2
        */

        String id = programIDField.getText();
        String name = programNameField.getText();
        String description = descriptionProgramArea.getText();
        IRightsholder[] rightsholders = rightholderListview.getItems().toArray(new IRightsholder[0]);
        // Map over rightholders with their roles
        Map<IRightsholder, List<String>> RhsRoles = new HashMap<>();
        for (IRightsholder rh: rightsholders) {
            for (String role: ((NewRightsholder) rh).getRoles()) {
                System.out.println(role);
            }
            RhsRoles.put(rh, ((NewRightsholder) rh).getRoles());
        }

        IProduction newProduction = new NewProduction(id, name, RhsRoles);
        creditsSystem.addProduction(newProduction);
    }

}