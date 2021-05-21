package presentation.controllers;

import Interfaces.IUser;
import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class helppageController {
    @FXML
    TextArea informationText;

    public void goBack(MouseEvent mouseEvent) {
        IUser user = DomainFacade.getInstance().getCurrentUser();
        if (user == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
                Stage window = (Stage) informationText.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (DomainFacade.getInstance().validateUser(user)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                Stage window = (Stage) informationText.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                Stage window = (Stage) informationText.getScene().getWindow();
                window.setScene(new Scene(root, 1300, 700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
