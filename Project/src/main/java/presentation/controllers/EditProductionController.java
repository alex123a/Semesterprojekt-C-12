package presentation.controllers;

import Interfaces.IProducer;
import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
import domain.DomainFacade;
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
import presentation.CreditWrapper;
import presentation.NewRightsholder;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EditProductionController implements Initializable {

    public ComboBox<String> nameInput;
    @FXML
    private ComboBox comboCategory;
    @FXML
    private ComboBox comboGenre;
    @FXML
    private ComboBox comboProducer;
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

    IProduction toEdit;

    String oldId = null;

    private Repository rep = Repository.getInstance();
    private DomainFacade domain = rep.domainFacade;

    private ObservableList<String> rightsholderList;
    private List<IRightsholder> rightList;
    private List<IRightsholder> finalRightsholdersList;

    @FXML
    void OnClickedSaveChanges(ActionEvent event) {
        toEdit.setProductionID(programIDField.getText());
        toEdit.setName(programNameField.getText());
        toEdit.setDescription(descriptionProgramArea.getText());
        toEdit.setYear(Integer.parseInt(yearInput.getText()));

        // Get the Production Genre
        ProductionGenre genre = null;
        for(ProductionGenre pg : ProductionGenre.values()) {
            if(comboGenre.getValue().equals(pg.getGenreWord())) {
                genre = pg;
            }
        }
        toEdit.setGenre(genre);

        // Get the Production Category
        ProductionType category = null;
        for(ProductionType pt : ProductionType.values()) {
            if(comboCategory.getValue().equals(pt.getTypeWord())) {
                category = pt;
            }
        }
        toEdit.setType(category);

        // Get the Production Producer
        IProducer producer = null;
        Repository r = Repository.getInstance();
        for(IUser user : r.domainFacade.getAllProducers()) {
            if(user.getUsername().equals(comboProducer.getValue())) {
                producer = (IProducer) user;
            }
        }
        toEdit.setProducer(producer);

        // Get the Production Rightsholders
        CreditWrapper[] rightsholders = rightholderListview.getItems().toArray(new CreditWrapper[0]);
        // Map over rightholders with their roles
        Map<IRightsholder, List<String>> RhsRoles = new HashMap<>();
        for (CreditWrapper credit: rightsholders) {
            RhsRoles.put(credit.getRightsholder(), credit.getRoles());
        }
        toEdit.setRightsholders(RhsRoles);

        // Save the changes
        domain.saveProduction(toEdit);

        // Go back to my_productions
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/my_productions.fxml"));
            Stage window = (Stage) programIDField.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClickedAddRightholder(ActionEvent event) {
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
            roles.addAll(Arrays.asList(rightholderRoles.getText().split(",")));
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
    void onClickedRemoveRightholder(ActionEvent event) {
        rightholderListview.getItems().remove(rightholderListview.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toEdit = Repository.getInstance().getToEdit();
        programIDField.setText(toEdit.getProductionID());
        oldId = toEdit.getProductionID();
        programNameField.setText(toEdit.getName());
        descriptionProgramArea.setText(toEdit.getDescription());
        yearInput.setText(String.valueOf(toEdit.getYear()));

        //Doesn't work because of the persistence-layer
        List<CreditWrapper> credits = new ArrayList<>();
        for (IRightsholder rh : toEdit.getRightsholders().keySet()){
            credits.add(new CreditWrapper(rh, toEdit.getRightsholders().get(rh)));
        }
        rightholderListview.getItems().setAll(credits);

        // Set categories
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        for(ProductionType pType : ProductionType.values()) {
            categoryOptions.add(pType.getTypeWord());
            if(pType.getTypeWord().equals(toEdit.getType().getTypeWord())) {
                comboCategory.setValue(pType.getTypeWord());
            }
        }
        comboCategory.setItems(categoryOptions);

        // Set Genres
        ObservableList<String> genreOptions = FXCollections.observableArrayList();
        for(ProductionGenre pGenre : ProductionGenre.values()) {
            genreOptions.add(pGenre.getGenreWord());
            if(pGenre.getGenreWord().equals(toEdit.getGenre().getGenreWord())) {
                comboGenre.setValue(pGenre.getGenreWord());
            }
        }
        comboGenre.setItems(genreOptions);

        // Get Producers
        Repository r = Repository.getInstance();
        List<IUser> userList = r.domainFacade.getUsers();
        ObservableList<String> sortOptions = FXCollections.observableArrayList();
        for(IUser user : userList) {
            sortOptions.add(user.getUsername());
            if(user.getUsername().equals(toEdit.getProducer().getUsername())) {
                comboProducer.setValue(user.getUsername());
            }
        }
        comboProducer.setItems(sortOptions);

        // Makes sure that the user only inputs numbers
        yearInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    yearInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        finalRightsholdersList = r.domainFacade.getRightsholders();
        rightList = r.domainFacade.getRightsholders();
        setRightsholderComboBox();

        nameInput.getEditor().setOnKeyPressed(this::handleOnKeyPressed);
        nameInput.hide();
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

    public void getClickedRightsholder(MouseEvent mouseEvent) {
        CreditWrapper cw = rightholderListview.getSelectionModel().getSelectedItem();
        nameInput.getEditor().setText(cw.getRightsholder().getFirstName() + " " + cw.getRightsholder().getLastName());
        rightholderDescription.setText(cw.getRightsholder().getDescription());
        String roles = "";
        for(String s : cw.getRoles()) {
            roles += s + ", ";
        }
        rightholderRoles.setText(roles);
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
                    existingUser = finalRightsholdersList.get(rightsholderList.indexOf(nameInput.getValue()));
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
