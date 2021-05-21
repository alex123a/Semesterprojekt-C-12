package presentation.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;

public class NotificationController {
    @FXML VBox notificationBox;

    public void createNotification(String productionName, String productionID, String date, String status) {
        // VBox for all the labels
        VBox vbox = new VBox();
        Label labelName = new Label(productionName);
        labelName.setFont(new Font(14));
        Label labelID = new Label("Produktions ID: " + productionID);
        labelID.setFont(new Font(14));
        Label labelDate = new Label(date);
        labelDate.setFont(new Font(14));
        Label labelStatus = new Label("Status: " + status);
        labelStatus.setFont(new Font(14));
        vbox.getChildren().addAll(labelName, labelID, labelDate, labelStatus);
        vbox.setPrefWidth(585);

        // HBox for the whole notification
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox);

        // Make decline Button
        Button declineBut = new Button("Afvis");
        declineBut.getStylesheets().add(getClass().getResource("/stylesheets/notificationNoButtonStyle.css").toExternalForm());

        // Make approve Button and set action on the button
        Button approveBut = new Button("Godkend");
        approveBut.getStylesheets().add(getClass().getResource("/stylesheets/notificationYesButtonStyle.css").toExternalForm());
        approveBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                declineBut.setVisible(false);
                notificationYesClicked();
            }
        });
        hbox.getChildren().addAll(approveBut);

        // Set action on declineBut
        declineBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                approveBut.setVisible(false);
                notificationNoClicked();
            }
        });
        hbox.getChildren().addAll(declineBut);

        // Needed for spacing in HBox
        Label space = new Label("");
        hbox.getChildren().addAll(space);

        // HBox style
        hbox.getStylesheets().add(getClass().getResource("/stylesheets/hboxStyle.css").toExternalForm());
        hbox.getStyleClass().add("hbox");
        hbox.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                //Repository.getInstance().setToEdit(p);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/adminVerifyProduction.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Add HBox to the VBox in the scrollpane
        notificationBox.getChildren().addAll(hbox);
    }

    public void notificationYesClicked() {
        createNotification("Star Wars", "SW01", "2021", "Afventer Godkendelse");
    }

    public void notificationNoClicked() {
    }

    public void backClicked(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
            Stage window = (Stage) notificationBox.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
