package presentation.controllers;

import Interfaces.IProduction;
import domain.CreditsManagement.CreditsSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

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
    TextField yearFrom;
    @FXML
    TextField yearTo;
    @FXML
    ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList("Serier", "Film", "Reality", "Underholdning", "Stand up", "Dokumentar", "Rejser og Eventyr", "Livsstil", "Magasiner", "Medvirkende");
        comboCategory.setItems(categoryOptions);
        ObservableList<String> genreOptions = FXCollections.observableArrayList("Krimi", "Action", "Komedie", "Drama", "Romance", "Fantasy", "Eventyr", "Gyser", "Thriller");
        comboGenre.setItems(genreOptions);
        ObservableList<String> sortOptions = FXCollections.observableArrayList("Alfabetisk", "Dato");
        comboSort.setItems(sortOptions);
    }

    public void createMovie(String movieName, String year, IProduction production) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(733);
        notificationPane.setStyle("-fx-cursor: hand; -fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");
        notificationPane.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                Repository r = Repository.getInstance();
                // todo : Skal indsætte en IProduction
                // Men createMovie modtager ikke en IProduction fordi search ikke er lavet endnu
                r.setToBeShown(production);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/production.fxml"));
                    Stage window = (Stage) backButton.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(650);

        Label movieLabel = new Label(movieName);
        Label yearLabel = new Label(year);

        labelBox.getChildren().addAll(movieLabel, yearLabel);

        Image image = new Image(getClass().getResourceAsStream("/images/BackArrow.png"));
        ImageView arrow = new ImageView(image);

        movieLabel.setStyle("-fx-text-fill: #5c5c5c; -fx-font-size: 22; -fx-font-weight: bold;");
        yearLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 20;");

        arrow.setFitWidth(35);
        arrow.setRotate(180);
        arrow.setPreserveRatio(true);

        notificationPane.getChildren().addAll(labelBox, arrow);

        searchResultBox.getChildren().add(notificationPane);
    }

    public void createPerson(String personName, String description) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(548);
        notificationPane.setStyle("-fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(470);

        Label personNameLabel = new Label(personName);
        Label descriptionLabel = new Label(description);

        labelBox.getChildren().addAll(personNameLabel, descriptionLabel);

        Image image = new Image(getClass().getResourceAsStream("/images/BackArrow.png"));
        ImageView arrow = new ImageView(image);

        personNameLabel.setStyle("-fx-text-fill: #5c5c5c; -fx-font-size: 22; -fx-font-weight: bold;");
        descriptionLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 20;");

        arrow.setFitWidth(35);
        arrow.setRotate(180);
        arrow.setPreserveRatio(true);

        notificationPane.getChildren().addAll(labelBox, arrow);
        notificationPane.setStyle("-fx-cursor: hand");

        searchResultBox.getChildren().add(notificationPane);
    }

    public void onComboBoxSelection(){
        String value = comboCategory.getValue();
        if (value == null){
            return;
        } else if (value.equals("Film")){
            comboGenre.setDisable(false);

            // todo : call search class and run through the list
            for(IProduction ip : CreditsSystem.getInstance().getProductions()) {
                createMovie(ip.getName(), "2021", ip);
            }
            /*for(IProduction pl : productionList) {
                createMovie(pl.getName(), "NaN");
            }*/
        } else if (value.equals("Medvirkende")){
            comboGenre.setDisable(true);

            /*List<IRightsholder> rightsholderList = CreditsSystem.getInstance().getAllRightsholders();
            for(IRightsholder rl : rightsholderList) {
                createMovie(rl.getName(), rl.getDescription());
            }*/
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

    public void onSearchClicked(MouseEvent mouseEvent) {
        // Change to list
        String searchParameters = searchInput.getText();
        //todo : Call search
        onComboBoxSelection();
    }

    public void resetScrollHeight() {
        scrollPane.setVvalue(0.0);
    }
}
