package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
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
        String lastPage = Repository.getInstance().getLastPage();
        if(lastPage.equals("menu")) {
            IUser user = DomainFacade.getInstance().getCurrentUser();
            if (user == null) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
                    Stage window = (Stage) descriptionProgramArea.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (DomainFacade.getInstance().validateUser(user)) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                    Stage window = (Stage) descriptionProgramArea.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                    Stage window = (Stage) descriptionProgramArea.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/" + lastPage + ".fxml"));
                Stage window = (Stage) descriptionProgramArea.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getClickedRightsholder(MouseEvent mouseEvent) {
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
