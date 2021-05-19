package presentation.controllers;

import Interfaces.ICreditManagement;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import presentation.NewProduction;
import presentation.NewRightsholder;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddProductionController implements Initializable {

    @FXML
    private ComboBox<String> comboGenre;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private ComboBox<String> comboProducer;

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

    private Repository rep = Repository.getInstance();
    private ICreditManagement creditsSystem = rep.creditsSystem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList("Serier", "Film", "Reality", "Underholdning", "Stand up", "Dokumentar", "Rejser og Eventyr", "Livsstil", "Magasiner", "Medvirkende");
        comboCategory.setItems(categoryOptions);
        ObservableList<String> genreOptions = FXCollections.observableArrayList("Krimi", "Action", "Komedie", "Drama", "Romance", "Fantasy", "Eventyr", "Gyser", "Thriller");
        comboGenre.setItems(genreOptions);
        ObservableList<String> sortOptions = FXCollections.observableArrayList("Kristian", "John");
        comboProducer.setItems(sortOptions);
    }

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
        TODO For now we ignore the description since it's not made in the other layers yet, because we forgot it,
            will be added in iteration 2
        */

        /*
        TODO still needs a check for null values in domain layer when trying to pass it down to data layer
         */

        String id = programIDField.getText();
        String name = programNameField.getText();
        String description = descriptionProgramArea.getText();
        IRightsholder[] rightsholders = rightholderListview.getItems().toArray(new IRightsholder[0]);
        // Map over rightholders with their roles
        Map<IRightsholder, List<String>> RhsRoles = new HashMap<>();
        for (IRightsholder rh: rightsholders) {
            RhsRoles.put(rh, ((NewRightsholder) rh).getRoles());
        }

        IProduction newProduction = new NewProduction(id, name, RhsRoles);
        creditsSystem.addProduction(newProduction);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
            Stage window = (Stage) addRightholderBut.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackClicked(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
            Stage window = (Stage) addRightholderBut.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}