package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import domain.DomainFacade;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import presentation.CreditWrapper;
import presentation.NewRightsholder;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class adminVerifyProductionController implements Initializable {

    @FXML
    private TextField genreField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField producerField;

    @FXML
    private TextArea descriptionProgramArea;

    @FXML
    private TextField programIDField;

    @FXML
    private TextField programNameField;

    @FXML
    private ListView<CreditWrapper> rightholderListview;

    @FXML
    private TextField rightholderFirstName;

    @FXML
    private TextField rightholderLastName;

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

    private Repository rep = Repository.getInstance();
    private DomainFacade domain = rep.domainFacade;

    @FXML
    void OnClickedSaveChanges(ActionEvent event) {
        toEdit.setProductionID(programIDField.getText());
        toEdit.setName(programNameField.getText());

        CreditWrapper[] rightsholders = rightholderListview.getItems().toArray(new CreditWrapper[0]);
        // Map over rightholders with their roles
        Map<IRightsholder, List<String>> RhsRoles = new HashMap<>();
        for (CreditWrapper credit: rightsholders) {
            RhsRoles.put(credit.getRightsholder(), credit.getRoles());
        }

        toEdit.setRightsholders(RhsRoles);
        domain.saveProduction(toEdit);

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
        String name = null;
        String description = null;
        List<String> roles = new ArrayList<>();

        /*
        TODO The checks for null values should be made in domain layer and not in presentation layer
            will be changed in iteration 2 if we run out of time in iteration 1
        */

        if (rightholderFirstName.getText() != null || !rightholderFirstName.getText().trim().isEmpty()) {
            name = rightholderFirstName.getText();
        }

        if (rightholderDescription.getText() != null || !rightholderDescription.getText().trim().isEmpty()) {
            description = rightholderDescription.getText();
        }

        if (rightholderRoles.getText() != null || !rightholderRoles.getText().trim().isEmpty()) {
            roles.addAll(Arrays.asList(rightholderRoles.getText().split(",")));
        }

        if (name != null && description != null) {
            //TODO pass first name and last name seperately to the constructor
            //TODO this should probably create a new creditWrapper
            IRightsholder newRightsholder = new NewRightsholder(name, "", description);
            CreditWrapper newCredit = new CreditWrapper(newRightsholder, roles);
            ObservableList<CreditWrapper> rightholders = rightholderListview.getItems();
            rightholders.add(newCredit);
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
        descriptionProgramArea.setText(toEdit.getDescription());
        genreField.setText(toEdit.getGenre().getGenreWord());
        typeField.setText(toEdit.getType().getTypeWord());
        producerField.setText(toEdit.getProducer().getUsername());

        //Doesn't work because of the persistence-layer
        List<CreditWrapper> credits = new ArrayList<>();
        for (IRightsholder rh : toEdit.getRightsholders().keySet()){
            credits.add(new CreditWrapper(rh, toEdit.getRightsholders().get(rh)));
        }
        rightholderListview.getItems().setAll(credits);
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
        rightholderFirstName.setText(cw.getRightsholder().getFirstName());
        rightholderLastName.setText(cw.getRightsholder().getLastName());
        rightholderDescription.setText(cw.getRightsholder().getDescription());
        String roles = "";
        for(String s : cw.getRoles()) {
            roles += s + ", ";
        }
        rightholderRoles.setText(roles);
    }
}