package presentation.controllers;

import Interfaces.IProduction;
import Interfaces.IRightsholder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private TextArea descriptionBox;
    @FXML
    private Label nameLabel;
    @FXML
    VBox scrollpaneVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository r = Repository.getInstance();
        System.out.println("name: " + r.getRightsholderToBeShown().getFirstName());

        setup(r.getRightsholderToBeShown().getFirstName() + " " + r.getRightsholderToBeShown().getLastName(), r.getRightsholderToBeShown().getDescription());

        for(IProduction p : r.getRightsholderToBeShown().getRightsholderFor()) {
            String roles = "";
            // new CreditWrapper(r.getRightsholderToBeShown(), p.getRightsholders().get(r.getRightsholderToBeShown())).getRoles()
            // todo : getRoles returns null
            for(String s : new CreditWrapper(r.getRightsholderToBeShown(), p.getRightsholderRole(r.getRightsholderToBeShown())).getRoles()) {
                roles += s + ",";
            }
            createRole(p.getName(), roles, p);
        }
    }

    private void setup(String personName, String description) {
        nameLabel.setText(personName);
        descriptionBox.setText(description);
    }

    // Method to create a box with the role
    public void createRole(String movieName, String role, IProduction p) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(548);
        notificationPane.setStyle("-fx-border-width: 1; -fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");
        notificationPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                Repository.getInstance().setProductionToBeShown(p);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/production.fxml"));
                    Stage window = (Stage) descriptionBox.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

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
        Repository r = Repository.getInstance();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/" + r.getLastPage() + ".fxml"));
            Stage window = (Stage) scrollpaneVBox.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
