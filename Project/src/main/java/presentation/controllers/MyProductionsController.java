package presentation.controllers;

import Interfaces.IProduction;
import domain.CreditsManagement.CreditsSystem;
import domain.DomainFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

public class MyProductionsController implements Initializable {

    @FXML
    public Button addProgramBut;

    @FXML
    public ImageView backButton;

    @FXML
    VBox productionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(IProduction p : CreditsSystem.getInstance().getProductions()) {
            createProduction(p);
        }
    }

    // Method to create a box with the production
    public void createProduction(IProduction p) {
        HBox notificationPane = new HBox();
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setPrefHeight(50);
        notificationPane.setPrefWidth(700);
        notificationPane.setStyle("-fx-border-color: #BBBBBB; -fx-background-color: #FFFFFF;");

        VBox labelBox = new VBox();
        labelBox.setPrefWidth(680);

        Label productionLabel = new Label(p.getName());
        Label productionIDLabel = new Label(p.getProductionID());

        labelBox.getChildren().addAll(productionLabel, productionIDLabel);

        productionLabel.setStyle("-fx-text-fill: #5c5c5c; -fx-font-size: 20; -fx-font-weight: bold;");
        productionIDLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 18;");

        Image editImage = new Image(getClass().getResourceAsStream("/images/Edit.jpg"));
        ImageView edit = new ImageView(editImage);
        edit.setFitWidth(25);
        edit.setPreserveRatio(true);
        edit.setStyle("-fx-cursor: hand;");
        edit.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                Repository.getInstance().setToEdit(p);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/layout/edit_production.fxml"));
                    Stage window = (Stage) addProgramBut.getScene().getWindow();
                    window.setScene(new Scene(root, 1300, 700));

                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Image TrashImage = new Image(getClass().getResourceAsStream("/images/Trash.jpg"));
        ImageView trash = new ImageView(TrashImage);
        trash.setFitWidth(30);
        trash.setPreserveRatio(true);
        trash.setStyle("-fx-cursor: hand;");
        trash.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                CreditsSystem.getInstance().deleteProduction(p);
                productionList.getChildren().clear();

                for(IProduction p : CreditsSystem.getInstance().getProductions()) {
                    createProduction(p);
                }
            }
        });

        Separator s = new Separator();
        s.setPrefWidth(30);
        s.setOpacity(0);

        notificationPane.getChildren().addAll(labelBox, edit, s, trash);

        productionList.getChildren().add(notificationPane);
    }

    @FXML
    public void onAddProgramClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/add_production.fxml"));
            Stage window = (Stage) addProgramBut.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackClicked(MouseEvent mouseEvent) {
        if (DomainFacade.getInstance().validateUser(DomainFacade.getInstance().getCurrentUser())) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                Stage window = (Stage) addProgramBut.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                Stage window = (Stage) addProgramBut.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
