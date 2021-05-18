package presentation.controllers;

import domain.CreditsManagement.CreditsSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    ListView<Object> resultsList;
    @FXML
    ComboBox<String> typeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList("Udsendelser", "Medvirkende");
        typeComboBox.setItems(options);
    }

    public void onComboBoxSelection(ActionEvent e){
        String value = typeComboBox.getValue();
        if (value == null){
            return;
        } else if (value.equals("Udsendelser")){
            resultsList.getItems().setAll(CreditsSystem.getInstance().getProductions());
        } else if (value.equals("Medvirkende")){
            resultsList.getItems().setAll(CreditsSystem.getInstance().getAllRightsholders());
        }
    }

    public void backButtonClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
            //It's not very pretty to use the resultslist to get the scene
            Stage window = Repository.getInstance().getWindow();
            window.setScene(new Scene(root, window.getWidth(), window.getHeight()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Menu");
    }


}
