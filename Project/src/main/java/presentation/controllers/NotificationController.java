package presentation.controllers;

import Interfaces.INotification;
import Interfaces.IProduction;
import domain.DomainFacade;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import presentation.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {
    @FXML VBox notificationBox;

    private Repository rep = Repository.getInstance();
    private DomainFacade domain = rep.domainFacade;

    List<INotification> notifications = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNotifications();
    }

    // approval converter. In the database the value is an int, so here we convert it to a text string which describes what the status is (used for systemadministrator notifications).
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

    // This method converts the boolean value on producer notifications to a text string which describes the viewed state.
    public String viewedConverter(boolean viewed) {
        return viewed ? "Viewed" : "Not viewed";
    }

    // Here it creates the notifications for GUI.
    public void createNotification(IProduction production, String status, String description, int index) {
            // VBox for all the labels
            VBox vbox = new VBox();
            Label labelName = new Label(production.getName());
            labelName.setFont(new Font(14));
            Label labelID = new Label("Produktions ID: " + production.getProductionID());
            labelID.setFont(new Font(14));
            Label labelStatus = new Label("Status: " + status);
            labelStatus.setFont(new Font(14));
            Label descriptionLabel = new Label(description);
            labelStatus.setFont(new Font(14));
            vbox.getChildren().addAll(labelName, labelID, labelStatus, descriptionLabel);
            vbox.setPrefWidth(585);

            // HBox for the whole notification
            HBox hbox = new HBox();
            hbox.getChildren().addAll(vbox);


            // Checks if the user is a systemadministrator, since approve/not approve buttons are only for admins.
            if (domain.validateUser(domain.getCurrentUser())) {
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
                        approveBut.setDisable(true);
                    }
                });
                hbox.getChildren().addAll(approveBut);
                if (status.equals("Not Approved")) {
                    approveBut.setVisible(false);
                    declineBut.setDisable(true);
                }

                // Set action on declineBut
                declineBut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        approveBut.setVisible(false);
                        notificationNoClicked(Integer.parseInt(declineBut.getId()));
                        declineBut.setDisable(true);
                    }
                });
                hbox.getChildren().addAll(declineBut);
                if (status.equals("Approved")) {
                    declineBut.setVisible(false);
                    approveBut.setDisable(true);
                }
            }

            // Needed for spacing in HBox
            Label space = new Label("");
            hbox.getChildren().addAll(space);

            // HBox style
            hbox.getStylesheets().add(getClass().getResource("/stylesheets/hboxStyle.css").toExternalForm());
            hbox.getStyleClass().add("hbox");
            hbox.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Repository.getInstance().setToEdit(production);

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

    // Here it loads the notifications.
    public void loadNotifications() {
        // Tenary operator there gets the systemadministrator notifications it's a systemadministrator and the producer notifications if it's a producer
        notifications = domain.validateUser(domain.getCurrentUser()) ? domain.getAdminNotifications() : domain.getProducerNotifications(domain.getCurrentUser());

        String status = "";

        for (int i = 0; i < notifications.size(); i++) {
            if (domain.validateUser(domain.getCurrentUser())) {
                status = approvalConverter(notifications.get(i).getApproval());
            } else if (!domain.validateUser(domain.getCurrentUser())) {
                status = viewedConverter(notifications.get(i).getViewed());
            }
            createNotification(notifications.get(i).getProduction(), status, notifications.get(i).getText(), i);
        }

        if (!domain.validateUser(domain.getCurrentUser())) {
            for (INotification notification : notifications) {
                notification.setViewed(true);
                domain.editProducerNotification(notification);
            }
        }

    }

    // For refreshing the page when a systemadministrator clicks approve or not approve on a notification.
    public void refreshPage(int index, int status) {
        // It checks for user type. But this method only get used if the user is a systemadministrator, so it is just a extra check
        // It change approval status to whatever the systemadministrator clicked and then it edit the notification in database.
        if (domain.validateUser(domain.getCurrentUser())) {
            notifications.get(index).setApproval(status);
            domain.editAdminNotification(notifications.get(index));
        }

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

    // Approved button handler
    public void notificationYesClicked(int index) {
        notifications.get(index).setApproval(2);
        refreshPage(index, 2);
    }

    // Not approved button handler
    public void notificationNoClicked(int index) {
        notifications.get(index).setApproval(3);
        refreshPage(index, 3);
    }

    // Back arrow
    public void backClicked(MouseEvent mouseEvent) {
        if (domain.validateUser(domain.getCurrentUser())) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                Stage window = (Stage) notificationBox.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (!domain.validateUser(domain.getCurrentUser())) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                Stage window = (Stage) notificationBox.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
