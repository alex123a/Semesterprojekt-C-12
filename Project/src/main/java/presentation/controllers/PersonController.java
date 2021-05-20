package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
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
import presentation.CreditWrapper;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    VBox scrollpaneVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository r = Repository.getInstance();

        setup(r.getRightsholderToBeShown().getFirstName() + " " + r.getRightsholderToBeShown().getLastName());

        // todo : Get all productions for this rightsholder and make a role for each
        String roles = "";
        for(IProduction p : r.getRightsholderToBeShown().getRightsholderFor()) {
            for(String s : p.getRightsholderRole(r.getRightsholderToBeShown())) {
                roles += s + ",";
            }
            createRole(p.getName(), roles);
        }
    }

    private void setup(String personName) {
        nameLabel.setText(personName);
    }

    // Method to create a box with the role
    public void createRole(String movieName, String role) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(548);
        notificationPane.setStyle("-fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(470);

        Label movieLabel = new Label(movieName);
        Label roleLabel = new Label(role);

        labelBox.getChildren().addAll(movieLabel, roleLabel);

        Image image = new Image(getClass().getResourceAsStream("/images/BackArrow.png"));
        ImageView arrow = new ImageView(image);

        movieLabel.setStyle("-fx-text-fill: #5c5c5c; -fx-font-size: 22; -fx-font-weight: bold;");
        roleLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 20;");

        arrow.setFitWidth(35);
        arrow.setRotate(180);
        arrow.setPreserveRatio(true);

        notificationPane.getChildren().addAll(labelBox, arrow);

        scrollpaneVBox.getChildren().add(notificationPane);
    }

    // Method to go back to the menu
    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
            Stage window = (Stage) scrollpaneVBox.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
