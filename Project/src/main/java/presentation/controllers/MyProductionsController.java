package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.ISeeCredits;
import domain.CreditsManagement.CreditsSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProductionsController implements Initializable {
    @FXML
    ListView<IProduction> productionListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productionListView.getItems().setAll(CreditsSystem.)
    }
}
