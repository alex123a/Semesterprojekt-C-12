package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.ISeeCredits;
import domain.CreditsManagement.CreditsSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyProductionsController implements Initializable {

    @FXML
    public Button addProgramBut;

    @FXML
    public Button removeProgramBut;

    @FXML
    ListView<IProduction> productionsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productionsListView.getItems().setAll(CreditsSystem.getInstance().getProductions());
    }

    @FXML
    public void onEditProgramClicked(ActionEvent event){
        IProduction selected = productionsListView.getSelectionModel().getSelectedItem();
        if (selected==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kan ikke ændre");
            alert.setHeaderText(null);
            alert.setContentText("Du skal vælge en produktion, før du kan ændre den");

            alert.showAndWait();
            return;
        }
        Repository.getInstance().setToEdit(selected);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/edit_production.fxml"));
            Stage window = Repository.getInstance().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAddProgramClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/add_production.fxml"));
            Stage window = Repository.getInstance().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onRemoveProgramClicked(ActionEvent event){
        CreditsSystem.getInstance().deleteProduction(productionsListView.getSelectionModel().getSelectedItem());
        productionsListView.getItems().setAll(CreditsSystem.getInstance().getProductions());
    }
}
