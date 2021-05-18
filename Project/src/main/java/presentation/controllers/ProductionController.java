package presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import presentation.Repository;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {
    @FXML
    Label movieLabel;
    @FXML
    Label movieDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository r = Repository.getInstance();

        if(r.getToBeShown().getProductionID().equals("SW3")) {
            draw("Star Wars", "Det er en film bro.");
        }
    }

    public void draw(String title, String description) {
        movieLabel.setText(title);
        movieDescription.setText(description);
    }
}
