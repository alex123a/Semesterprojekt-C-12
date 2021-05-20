package presentation.controllers;

import Interfaces.INotification;
import domain.DomainFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import presentation.Repository;
import presentation.userManage.Systemadministrator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {
    @FXML VBox notificationBox;

    private Repository rep = Repository.getInstance();
    private DomainFacade domain = rep.domainFacade;

    List<INotification> notifications = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNotifications();
    }

    public String approvalConverter(int i) {
        switch(i) {
            case 1:
                return "Waiting";
            case 2:
                return "Approved";
            case 3:
                return "Not Approved";
            default:
                return "Unknown status";
        }
    }

    public void createNotification(String productionName, String productionID, String status, int index) {
        // VBox for all the labels
        VBox vbox = new VBox();
        Label labelName = new Label(productionName);
        labelName.setFont(new Font(14));
        Label labelID = new Label("Produktions ID: " + productionID);
        labelID.setFont(new Font(14));
        Label labelStatus = new Label("Status: " + status);
        labelStatus.setFont(new Font(14));
        vbox.getChildren().addAll(labelName, labelID, labelStatus);
        vbox.setPrefWidth(585);

        // HBox for the whole notification
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox);

        // Make decline Button
        Button declineBut = new Button("Afvis");
        declineBut.setId("" + index);
        declineBut.getStylesheets().add(getClass().getResource("/stylesheets/notificationNoButtonStyle.css").toExternalForm());

        // Make approve Button and set action on the button
        Button approveBut = new Button("Godkend");
        approveBut.setId("" + index);
        approveBut.getStylesheets().add(getClass().getResource("/stylesheets/notificationYesButtonStyle.css").toExternalForm());
        approveBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                declineBut.setVisible(false);
                notificationYesClicked(Integer.parseInt(approveBut.getId()));
            }
        });
        hbox.getChildren().addAll(approveBut);
        if (status.equals("Not Approved")) {
            approveBut.setVisible(false);
        }

        // Set action on declineBut
        declineBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                approveBut.setVisible(false);
                notificationNoClicked(Integer.parseInt(declineBut.getId()));
            }
        });
        hbox.getChildren().addAll(declineBut);
        if (status.equals("Approved")) {
            declineBut.setVisible(false);
        }

        // Needed for spacing in HBox
        Label space = new Label("");
        hbox.getChildren().addAll(space);

        // HBox style
        hbox.getStylesheets().add(getClass().getResource("/stylesheets/hboxStyle.css").toExternalForm());
        hbox.getStyleClass().add("hbox");

        // Add HBox to the VBox in the scrollpane
        notificationBox.getChildren().addAll(hbox);
    }

    public void loadNotifications() {
        // TODO this should get the notification by sending the current user to the method
        // TODO Need the interface to check for which type of user it is
        // notifications = domain.getCurrentUser() instanceof Systemadministrator ? domain.getAdminNotifications() : domain.getProducerNotifications(domain.getCurrentUser());

        // For now it just always takes the admins notifications
        notifications = domain.getAdminNotifications();

        // TODO Do so status match the type of user
        // String status = "";

        for (int i = 0; i < notifications.size(); i++) {
            // TODO Need a method to convert boolean for producer to String like I have done for administrator and approval
            String status = approvalConverter(notifications.get(i).getApproval());
            createNotification(notifications.get(i).getProductionName(), notifications.get(i).getProductionId(), status, i);
        }
    }

    public void refreshPage(int index, int status) {
        // TODO do some it depends on the type of user
        // For now it is just admin
        notifications.get(index).setApproval(status);
        domain.editAdminNotification(notifications.get(index));
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/notification.fxml"));
            Stage window = (Stage) notificationBox.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            loadNotifications();
        }
    }

    public void notificationYesClicked(int index) {
        notifications.get(index).setApproval(2);
        refreshPage(index, 2);
    }

    public void notificationNoClicked(int index) {
        notifications.get(index).setApproval(3);
        refreshPage(index, 3);
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
