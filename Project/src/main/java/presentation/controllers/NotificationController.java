package presentation.controllers;

import Interfaces.IAdministrator;
import Interfaces.INotification;
import Interfaces.IProducer;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import presentation.Repository;
import presentation.userManage.Systemadministrator;

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

    public String viewedConverter(boolean viewed) {
        return viewed ? "Viewed" : "Not viewed";
    }

    public void createNotification(String productionName, String productionID, String status, String description, int index) {
            // VBox for all the labels
            VBox vbox = new VBox();
            Label labelName = new Label(productionName);
            labelName.setFont(new Font(14));
            Label labelID = new Label("Produktions ID: " + productionID);
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
                    // TODO : find the production from the productionID and set it to setToEdit
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

    public void loadNotifications() {
        notifications = domain.validateUser(domain.getCurrentUser()) ? domain.getAdminNotifications() : domain.getProducerNotifications(domain.getCurrentUser());

        String status = "";

        for (int i = 0; i < notifications.size(); i++) {
            if (domain.getCurrentUser() instanceof IAdministrator) {
                status = approvalConverter(notifications.get(i).getApproval());
            } else if (domain.getCurrentUser() instanceof IProducer) {
                status = viewedConverter(notifications.get(i).getViewed());
            }
            createNotification(notifications.get(i).getProduction().getName(), notifications.get(i).getProduction().getProductionID(), status, notifications.get(i).getText(), i);
        }
        if (domain.getCurrentUser() instanceof IProducer) {
            for (INotification notification : notifications) {
                notification.setViewed(true);
                domain.editProducerNotification(notification);
            }
        }

    }

    public void refreshPage(int index, int status) {
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

    public void notificationYesClicked(int index) {
        notifications.get(index).setApproval(2);
        refreshPage(index, 2);
    }

    public void notificationNoClicked(int index) {
        notifications.get(index).setApproval(3);
        refreshPage(index, 3);
    }

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
