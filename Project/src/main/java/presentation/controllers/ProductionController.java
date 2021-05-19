package presentation.controllers;

import Interfaces.IProduction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class ProductionController implements Initializable {
    @FXML
    Label movieLabel;
    @FXML
    Label movieDescription;
    @FXML
    ImageView editImage;
    @FXML
    VBox roleBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository r = Repository.getInstance();

        // .getDescription() er ikke implementeret i IProduction
        //draw(r.getToBeShown().getName(), r.getToBeShown().getDescription());

        draw("Star Wars", "It's a movie bro");
        createRole("John", "Han Solo");
    }

    public void draw(String title, String description) {
        movieLabel.setText(title);
        movieDescription.setText(description);
    }

    // Method to create a box with the role
    public void createRole(String personName, String role) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(548);
        notificationPane.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF; -fx-cursor: hand;");

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(470);

        Label personLabel = new Label(personName);
        Label roleLabel = new Label(role);

        labelBox.getChildren().addAll(personLabel, roleLabel);

        Image image = new Image(getClass().getResourceAsStream("/images/BackArrow.png"));
        ImageView arrow = new ImageView(image);

        personLabel.setStyle("-fx-text-fill: #5c5c5c; -fx-font-size: 22; -fx-font-weight: bold;");
        roleLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 20;");

        arrow.setFitWidth(35);
        arrow.setRotate(180);
        arrow.setPreserveRatio(true);

        notificationPane.getChildren().addAll(labelBox, arrow);

        roleBox.getChildren().add(notificationPane);
    }

    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
            Stage window = (Stage) movieLabel.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editClicked(MouseEvent mouseEvent) {
        Repository r = Repository.getInstance();
        Repository.getInstance().setToEdit(r.getToBeShown());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/edit_production.fxml"));
            Stage window = (Stage) movieLabel.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
