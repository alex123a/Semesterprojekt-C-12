package presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    ListView<String> resultsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> testData = new ArrayList<>();
        testData.add("Program 1");
        testData.add("Program 2");
        testData.add("Program 3");
        testData.add("Program 4");
        testData.add("Program 5");
        resultsList.getItems().setAll(testData);
    }
}
