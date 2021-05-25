package presentation.controllers;

import Interfaces.IProducer;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
import enumerations.ProductionGenre;
import enumerations.ProductionType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import presentation.credits.CreditWrapper;
import presentation.credits.NewProduction;
import presentation.credits.NewRightsholder;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddProductionController implements Initializable {

    @FXML
    private ComboBox<String> nameInput;

    @FXML
    private ComboBox<String> comboGenre;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private ComboBox<String> comboProducer;
    @FXML
    private TextField yearInput;

    @FXML
    private TextArea descriptionProgramArea;

    @FXML
    private TextField programIDField;

    @FXML
    private TextField programNameField;

    @FXML
    private ListView<CreditWrapper> rightholderListview;

    @FXML
    private TextField rightholderDescription;

    @FXML
    private TextField rightholderRoles;

    @FXML
    private Button addRightholderBut;

    private Repository rep = Repository.getInstance();
    private ObservableList<String> rightsholderList;
    private List<IRightsholder> rightList;
    private List<IRightsholder> finalRightsholdersList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set categories
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        for(ProductionType pType : ProductionType.values()) {
            categoryOptions.add(pType.getTypeWord());
        }
        comboCategory.setItems(categoryOptions);

        // Set Genres
        ObservableList<String> genreOptions = FXCollections.observableArrayList();
        for(ProductionGenre pGenre : ProductionGenre.values()) {
            genreOptions.add(pGenre.getGenreWord());
        }
        comboGenre.setItems(genreOptions);

        // Get Producers
        ObservableList<String> sortOptions = FXCollections.observableArrayList();
        if (rep.domainFacade.validateUser(rep.domainFacade.getCurrentUser())) {
            List<IUser> userList = rep.domainFacade.getAllProducers();
            for (IUser user : userList) {
                sortOptions.addAll(user.getUsername());
            }
        } else {
            sortOptions.add(rep.domainFacade.getCurrentUser().getUsername());
            comboProducer.setValue(rep.domainFacade.getCurrentUser().getUsername());
        }
        comboProducer.setItems(sortOptions);

        // Makes sure that only numbers can be written in the yearInput
        yearInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    yearInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        finalRightsholdersList = rep.domainFacade.getRightsholders();
        rightList = rep.domainFacade.getRightsholders();
        setRightsholderComboBox();

        nameInput.getEditor().setOnKeyPressed(this::handleOnKeyPressed);

        nameInput.hide();
        rightholderDescription.setText("");
        rightholderRoles.setText("");
    }

    @FXML
    public void onClickedAddRightholder(ActionEvent event) {
        String firstName = null;
        String lastName = null;
        String description = null;
        List<String> roles = new ArrayList<>();

        /*
        TODO The checks for null values should be made in domain layer and not in presentation layer
            will be changed in iteration 2 if we run out of time in iteration 1
        */

        if (nameInput.getEditor().getText() != null || !nameInput.getEditor().getText().trim().isEmpty()) {
            String name = nameInput.getEditor().getText();
            String[] splitter = name.split(" ");
            firstName = splitter[0];
            lastName = splitter[1];
        }

        if (rightholderDescription.getText() != null || !rightholderDescription.getText().trim().isEmpty()) {
            description = rightholderDescription.getText();
        }

        if (rightholderRoles.getText() != null || !rightholderRoles.getText().trim().isEmpty()) {
            roles = Arrays.asList(rightholderRoles.getText().split(","));
            for(int i = 0; i<roles.size(); i++) {
                roles.set(i, roles.get(i).trim());
            }
        }

        if (firstName != null && lastName != null && description != null) {
            IRightsholder newRightsholder = doesRightsholderExist();
            // Checks if the rightsholder exist doesn't exist
            if(newRightsholder == null) {
                // Rightsholder doesn't exist
                newRightsholder = new NewRightsholder(firstName, lastName, description);
            }
            CreditWrapper newCredit = new CreditWrapper(newRightsholder, roles);
            ObservableList<CreditWrapper> rightholders = rightholderListview.getItems();
            rightholders.add(newCredit);
        }

        nameInput.getEditor().setText("");
        nameInput.getSelectionModel().clearSelection();
        findRightsholder();
        nameInput.hide();
        rightholderDescription.setText("");
        rightholderRoles.setText("");
    }

    @FXML
    public void onClickedRemoveRightholder(ActionEvent event) {
        rightholderListview.getItems().remove(rightholderListview.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onClickedAddProduction(ActionEvent event) {
        /*
        TODO still needs a check for null values in domain layer when trying to pass it down to data layer
         */

        String id = programIDField.getText();
        String name = programNameField.getText();
        String description = descriptionProgramArea.getText();
        int year = Integer.parseInt(yearInput.getText());
        // Get Production Genre
        ProductionGenre genre = null;
        for(ProductionGenre pg : ProductionGenre.values()) {
            if(comboGenre.getValue().equals(pg.getGenreWord())) {
                genre = pg;
            }
        }
        // Get Production Category
        ProductionType category = null;
        for(ProductionType pt : ProductionType.values()) {
            if(comboCategory.getValue().equals(pt.getTypeWord())) {
                category = pt;
            }
        }
        // Get Production Producer
        IProducer producer = null;
        Repository r = Repository.getInstance();
        for(IUser user : r.domainFacade.getAllProducers()) {
            if(user.getUsername().equals(comboProducer.getValue())) {
                producer = (IProducer) user;
            }
        }
        // Get Production Rightsholders
        CreditWrapper[] rightsholders = rightholderListview.getItems().toArray(new CreditWrapper[0]);
        // Map over rightholders with their roles
        Map<IRightsholder, List<String>> RhsRoles = new HashMap<>();
        for (CreditWrapper credit: rightsholders) {
            RhsRoles.put(credit.getRightsholder(), credit.getRoles());
        }

        // Save the production
        IProduction newProduction = new NewProduction(id, name, description, year, genre, category, producer, RhsRoles);
        r.domainFacade.saveProduction(newProduction);

        try {
            Repository.getInstance().setLastPage("add_production");
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

    public void findRightsholder() {
        // Should have been in domain instead
        rightList  = new ArrayList<>();

        for(IRightsholder ir : finalRightsholdersList) {
            String name = ir.getFirstName() + " " + ir.getLastName();
            if(name.toLowerCase().contains(nameInput.getEditor().getText().toLowerCase())) {
                rightList.add(ir);
            }
        }

        setRightsholderComboBox();
    }

    public void setRightsholderComboBox() {
        rightsholderList = FXCollections.observableArrayList();
        for(IRightsholder ir : rightList) {
            rightsholderList.add(ir.getFirstName() + " " + ir.getLastName());
            rightholderDescription.setText(ir.getDescription());
        }
        if(rightsholderList.size() == 0) {
            rightholderDescription.setText("");
            rightholderDescription.setEditable(true);
        }
        else {
            rightholderDescription.setEditable(false);
        }
        if(rightsholderList.size() > 0) {
            nameInput.getSelectionModel().clearSelection();
            nameInput.getItems().clear();
            nameInput.setItems(rightsholderList);
            nameInput.show();
        }
    }

    public IRightsholder doesRightsholderExist() {
        // Should have been in domain instead
        IRightsholder existingUser = null;
        Repository r = Repository.getInstance();
        finalRightsholdersList = r.domainFacade.getRightsholders();

        if(rightsholderList.contains(nameInput.getValue())) {
            for(IRightsholder rightsholder : finalRightsholdersList) {
                String name = rightsholder.getFirstName() + " " + rightsholder.getLastName();
                if(name.contains(rightsholderList.get(rightsholderList.indexOf(nameInput.getValue())))) {
                    existingUser = rightsholder;
                }
            }
        }
        return existingUser;
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        findRightsholder();
    }

    @FXML
    private void updateDescription(ActionEvent actionEvent) {
        String chosenValue = nameInput.getValue();
        for(IRightsholder rightsholder : rightList) {
            String name = rightsholder.getFirstName() + " " + rightsholder.getLastName();
            if(name.equals(chosenValue)) {
                rightholderDescription.setText(rightsholder.getDescription());
            }
        }
    }
}
