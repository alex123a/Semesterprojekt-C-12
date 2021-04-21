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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyProductionsController implements Initializable {

    @FXML
    public Button addProgramBut;
    @FXML
    public ImageView backButton;

    @FXML
    public Button removeProgramBut;

    @FXML
    ListView<IProduction> productionsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productionsListView.getItems().setAll(CreditsSystem.getInstance().getProductions());
    }



    @FXML
    public void onAddProgramClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/add_production.fxml"));
            Stage window = (Stage) addProgramBut.getScene().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackClicked(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
            Stage window = (Stage) addProgramBut.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    @FXML
    public void onRemoveProgramClicked(ActionEvent event){
        System.out.println(productionsListView.getSelectionModel().getSelectedItems().get(0));
        CreditsSystem.getInstance().deleteProduction(productionsListView.getSelectionModel().getSelectedItem());
        productionsListView.getItems().setAll(CreditsSystem.getInstance().getProductions());
    }
}
