package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
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
import presentation.credits.CreditWrapper;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class adminVerifyProductionController implements Initializable {

    @FXML
    private Button showAsUser;
    @FXML
    private TextField inputYear;
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
    private TextField rightholderName;

    @FXML
    private TextField rightholderDescription;

    @FXML
    private TextField rightholderRoles;

    IProduction toEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets all the information up
        toEdit = Repository.getInstance().getToEdit();
        programIDField.setText(toEdit.getProductionID());
        programNameField.setText(toEdit.getName());
        descriptionProgramArea.setText(toEdit.getDescription());
        inputYear.setText(String.valueOf(toEdit.getYear()));
        genreField.setText(toEdit.getGenre().getGenreWord());
        typeField.setText(toEdit.getType().getTypeWord());
        producerField.setText(toEdit.getProducer().getUsername());

        List<CreditWrapper> credits = new ArrayList<>();
        for (IRightsholder rh : toEdit.getRightsholders().keySet()){
            credits.add(new CreditWrapper(rh, toEdit.getRightsholders().get(rh)));
        }
        rightholderListview.getItems().setAll(credits);
    }

    public void onBackClicked(MouseEvent mouseEvent) {
        Stage window = (Stage) inputYear.getScene().getWindow();
        window.close();
    }

    public void getClickedRightsholder(MouseEvent mouseEvent) {
        // Inserts the rightsholders information, in the rightsholders input boxes
        CreditWrapper cw = rightholderListview.getSelectionModel().getSelectedItem();
        rightholderName.setText(cw.getRightsholder().getFirstName() + " " + cw.getRightsholder().getLastName());
        rightholderDescription.setText(cw.getRightsholder().getDescription());
        String roles = "";
        for(String s : cw.getRoles()) {
            roles += s + ", ";
        }
        rightholderRoles.setText(roles);
    }

    public void showAsUser(MouseEvent mouseEvent) {
        // Opens the production page to show the production as a normal user would see it
        try {
            Repository.getInstance().setLastPage("adminVerifyProduction");
            Repository.getInstance().setProductionToBeShown(Repository.getInstance().getToEdit());
            Parent root = FXMLLoader.load(getClass().getResource("/layout/production.fxml"));
            Stage window = (Stage) descriptionProgramArea.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
