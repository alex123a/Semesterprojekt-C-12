package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.IUser;
import domain.CreditsManagement.CreditsSystem;
import domain.DomainFacade;
import enumerations.ProductionGenre;
import enumerations.ProductionSorting;
import enumerations.ProductionType;
import enumerations.RightholderSorting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
        comboGenre.setDisable(true);

        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        for(ProductionType pType : ProductionType.values()) {
            categoryOptions.add(pType.getTypeWord());
        }
        categoryOptions.add("Medvirkende");
        comboCategory.setItems(categoryOptions);

        ObservableList<String> genreOptions = FXCollections.observableArrayList();
        for(ProductionGenre pGenre : ProductionGenre.values()) {
            genreOptions.add(pGenre.getGenreWord());
        }
        comboGenre.setItems(genreOptions);
    }

    public void createMovie(String movieName, int year, IProduction production) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(733);
        notificationPane.setStyle("-fx-cursor: hand; -fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");
        notificationPane.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                Repository r = Repository.getInstance();
                r.setProductionToBeShown(production);
                r.setLastPage("search");

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
        Label yearLabel = new Label("" + year);

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

    public void createPerson(String personName, String description, IRightsholder rightsholder) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(733);
        notificationPane.setStyle("-fx-cursor: hand; -fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");
        notificationPane.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                Repository r = Repository.getInstance();
                r.setRightsholderToBeShown(rightsholder);
                r.setLastPage("search");

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/person.fxml"));
                    Stage window = (Stage) backButton.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(650);

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

        searchResultBox.getChildren().add(notificationPane);
    }

    public void checkComboBoxSelection(){
        String value = comboCategory.getValue();
        if (value == null){
            return;
        } else if (value.equals("Film")){
            comboGenre.setDisable(false);

            // todo : call search class and run through the list
            // Make it search for only the chosen category
            // Right now it gets all when choosing "Film"
            for(IProduction ip : CreditsSystem.getInstance().getProductions()) {
                createMovie(ip.getName(), ip.getYear(), ip);
            }
        } else if (value.equals("Medvirkende")){
            comboGenre.setDisable(true);

            // todo : Use Search to get all rightsholders

            List<IRightsholder> rightsholderList = CreditsSystem.getInstance().getAllRightsholders();
            for(IRightsholder r : rightsholderList) {
                createPerson(r.getFirstName() + " " + r.getLastName(), r.getDescription(), r);
            }

        }
    }

    public void goBack(MouseEvent mouseEvent) {
        IUser user = DomainFacade.getInstance().getCurrentUser();
        if (user == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
                Stage window = (Stage) backButton.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (DomainFacade.getInstance().validateUser(user)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                Stage window = (Stage) backButton.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                Stage window = (Stage) backButton.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onSearchClicked(MouseEvent mouseEvent) {
        // Change to list
        String searchParameters = searchInput.getText();

        searchResultBox.getChildren().clear();
        //todo : Call search
        checkComboBoxSelection();
    }

    public void resetScrollHeight() {
        scrollPane.setVvalue(0.0);
    }

    public void onComboCategory(ActionEvent mouseEvent) {
        comboGenre.setDisable(true);
        comboSort.setDisable(true);

        String value = comboCategory.getValue();

        if (value == null){
        }
        else if (value.equals(ProductionType.FILM.getTypeWord())){
            comboGenre.setDisable(false);
            setComboSort("Production");
        }
        // "Medvirkende" is not in the ProductionType Enums
        else if (value.equals("Medvirkende")){
            comboGenre.setDisable(true);

            // todo : don't use CreditsSystem!!!!
            List<IRightsholder> rightsholderList = CreditsSystem.getInstance().getAllRightsholders();
            for(IRightsholder r : rightsholderList) {
                createPerson(r.getFirstName() + " " + r.getLastName(), r.getDescription(), r);
            }
        }
    }

    private void setComboSort(String type) {
        comboSort.setDisable(false);
        if(type.equals("Production")) {
            ObservableList<String> sortOptions = FXCollections.observableArrayList();
            for(ProductionSorting pSort : ProductionSorting.values()) {
                sortOptions.add(String.valueOf(pSort));
            }
            comboSort.setItems(sortOptions);
        }
        else if(type.equals("Rightsholders")) {
            ObservableList<String> sortOptions = FXCollections.observableArrayList();
            for(RightholderSorting rSort : RightholderSorting.values()) {
                sortOptions.add(String.valueOf(rSort));
            }
            comboSort.setItems(sortOptions);
        }
    }
}
