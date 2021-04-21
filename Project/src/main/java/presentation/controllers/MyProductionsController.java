package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.ISeeCredits;
import domain.CreditsManagement.CreditsSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyProductionsController implements Initializable {
    @FXML
    ListView<String> productionsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> testData = new ArrayList<>();
        testData.add("One");
        testData.add("Two");
        testData.add("Three");
        testData.add("Four");
        productionsListView.getItems().setAll(testData);
    }
}
