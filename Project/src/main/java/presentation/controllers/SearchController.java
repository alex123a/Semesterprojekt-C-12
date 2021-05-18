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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    ListView<Object> resultsList;
    @FXML
    ComboBox<String> typeComboBox;
    @FXML
    ImageView backButton;
    @FXML
    ComboBox<String> comboCategory;
    @FXML
    ComboBox<String> comboGenre;
    @FXML
    ComboBox<String> comboSort;
    @FXML
    TextField searchInput;
    @FXML
    VBox searchResultBox;
    @FXML
    Slider sliderYear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList("Serier", "Film", "Reality", "Underholdning", "Stand up", "Dokumentar", "Rejser og Eventyr", "Livsstil", "Magasiner");
        comboCategory.setItems(categoryOptions);
        ObservableList<String> genreOptions = FXCollections.observableArrayList("Krimi", "Action", "Komedie", "Drama", "Romance", "Fantasy", "Eventyr", "Gyser", "Thriller");
        comboGenre.setItems(genreOptions);
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

    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
            Stage window = (Stage) backButton.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToTop(MouseEvent mouseEvent) {
        //todo : Scroll to the top of the scrollpane
    }

    public void onSearchClicked(MouseEvent mouseEvent) {
        // Change to list
        String searchParameters = searchInput.getText();
        //todo : Call search
    }
}
