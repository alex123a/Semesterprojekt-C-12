package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import Interfaces.ISearchable;
import Interfaces.IUser;
import domain.CreditsManagement.CreditsSystem;
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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
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
        searchInput.setText(Repository.getInstance().getProgramName());
        Repository.getInstance().setProgramName("");
        searchInput.setFocusTraversable(false);

        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        categoryOptions.add("Kategori");
        for(ProductionType pType : ProductionType.values()) {
            categoryOptions.add(pType.getTypeWord());
        }
        categoryOptions.add("Medvirkende");
        comboCategory.setItems(categoryOptions);

        ObservableList<String> genreOptions = FXCollections.observableArrayList();
        genreOptions.add("Genre");
        for(ProductionGenre pGenre : ProductionGenre.values()) {
            genreOptions.add(pGenre.getGenreWord());
        }
        comboGenre.setItems(genreOptions);

        comboSort.setValue(String.valueOf(ProductionSorting.NAME));
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

    public void goBack(MouseEvent mouseEvent) {
        Repository r = Repository.getInstance();
        IUser user = r.domainFacade.getCurrentUser();
        if (user == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
                Stage window = (Stage) backButton.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (r.domainFacade.validateUser(user)) {
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
        searchResultBox.getChildren().clear();
        search();
    }

    private void search() {
        Repository r = Repository.getInstance();

        String searchFor = null;

        // Checks if there is not chosen a category, then showing all categories
        if(comboCategory.getValue() == null ||comboCategory.getValue().equals("Kategori")) {
            searchFor = "both";
            comboSort.setValue(null);
        }
        else {
            // Check if it should search for Productions or Rightsholders
            for(ProductionType pType : ProductionType.values()) {
                if(comboCategory.getValue().equals(pType.getTypeWord())) {
                    searchFor = "production";
                }
            }
            if(comboCategory.getValue().equals("Medvirkende")) {
                searchFor = "person";
            }
        }

        if(searchFor.equals("production") || searchFor.equals("both")) {
            // SEARCH FUNCTION //
            // Henter liste over alle productions
            List<ISearchable> searchableList = new ArrayList<>(r.domainFacade.getProductions());
            // Finder alle resultater som indeholder searchInput teksten
            List<IProduction> productionList = new ArrayList<>();
            productionList.addAll((Collection<? extends IProduction>) r.domainFacade.findMatch(searchableList, searchInput.getText()));

            // SORTING FUNCTION //
            // Get Genre
            ProductionGenre pGenre = null;
            for(ProductionGenre pg : ProductionGenre.values()) {
                if(pg.getGenreWord().equals(comboGenre.getValue())) {
                    pGenre = pg;
                }
            }

            // Get Years
            int yFrom = 1800;
            int yTo = 3000;
            if(!yearFrom.getText().equals("")) {
                yFrom = Integer.parseInt(yearFrom.getText());
            }
            if(!yearTo.getText().equals("")) {
                yTo = Integer.parseInt(yearTo.getText());
            }
            int[] years = new int[] {yFrom, yTo};

            // Get Sort
            ProductionType pType = null;
            for(ProductionType pt : ProductionType.values()) {
                if(pt.getTypeWord().equals(comboCategory.getValue())) {
                    pType = pt;
                }
            }

            List<IProduction> filteredList = r.domainFacade.filterProduction(productionList, years, pGenre, pType);

            if(comboSort.getValue() != null) {
                ProductionSorting pSort = null;
                for(ProductionSorting ps : ProductionSorting.values()) {
                    String temp = String.valueOf(ps);
                    if(temp.equals(comboSort.getValue())) {
                        pSort = ps;
                    }
                }
                List<IProduction> sortedList = r.domainFacade.sortProductionBy(filteredList, pSort);
                for(IProduction ip : sortedList) {
                    System.out.println("name: " + ip.getName() + ", genre: " + ip.getGenre() + ", type: " + ip.getType());
                    createMovie(ip.getName(), ip.getYear(), ip);
                }
            }
            else{
                for(IProduction ip : filteredList) {
                    System.out.println("name: " + ip.getName() + ", genre: " + ip.getGenre() + ", type: " + ip.getType());
                    createMovie(ip.getName(), ip.getYear(), ip);
                }
            }
        }
        if(searchFor.equals("person") || searchFor.equals("both")) {
            // SEARCH FUNCTION //
            // Henter liste over alle medvirkende
            List<ISearchable> searchableList = new ArrayList<>(r.domainFacade.getRightsholders());
            // Finder alle resultater som indeholder searchInput teksten
            List<IRightsholder> rightsholderList = new ArrayList<>();
            rightsholderList.addAll((Collection<? extends IRightsholder>) r.domainFacade.findMatch(searchableList, searchInput.getText()));

            if(comboSort.getValue() != null) {
                RightholderSorting rSort = null;
                for(RightholderSorting rs : RightholderSorting.values()) {
                    String temp = String.valueOf(rs);
                    if(temp.equals(comboSort.getValue())) {
                        rSort = rs;
                    }
                }
                List<IRightsholder> sortedList = r.domainFacade.sortPersonBy(rightsholderList, rSort);
                for(IRightsholder ir : sortedList) {
                    createPerson(ir.getFirstName() + " " + ir.getLastName(), ir.getDescription(), ir);
                }
            }
            else{
                for(IRightsholder ir : rightsholderList) {
                    createPerson(ir.getFirstName() + " " + ir.getLastName(), ir.getDescription(), ir);
                }
            }
        }
    }

    public void resetScrollHeight() {
        scrollPane.setVvalue(0.0);
    }

    public void onComboCategory(ActionEvent mouseEvent) {
        comboGenre.setDisable(true);

        String value = comboCategory.getValue();

        if (value == null){
        }
        // "Medvirkende" is not in the ProductionType Enums
        else if (value.equals("Medvirkende")){
            comboGenre.setDisable(true);
            setComboSort("Medvirkende");
        }
        else {
            comboGenre.setDisable(false);
            setComboSort("Production");
        }
    }

    private void setComboSort(String type) {
        if(type.equals("Production")) {
            ObservableList<String> sortOptions = FXCollections.observableArrayList();
            for(ProductionSorting pSort : ProductionSorting.values()) {
                sortOptions.add(String.valueOf(pSort));
            }
            comboSort.setItems(sortOptions);
            comboSort.setValue(String.valueOf(ProductionSorting.NAME));
        }
        else if(type.equals("Medvirkende")) {
            ObservableList<String> sortOptions = FXCollections.observableArrayList();
            for(RightholderSorting rSort : RightholderSorting.values()) {
                sortOptions.add(String.valueOf(rSort));
            }
            comboSort.setItems(sortOptions);
            comboSort.setValue(String.valueOf(RightholderSorting.FIRST_NAME));
        }
    }

    public void onEnter(ActionEvent actionEvent) {
        searchResultBox.getChildren().clear();
        search();
    }
}
