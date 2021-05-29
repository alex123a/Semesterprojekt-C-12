package presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotController {

    @FXML
    private Label message;
    
    @FXML
    private TextField usernameOutput;

    @FXML
    private TextField mailOutput;


    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/login.fxml"));
            Stage window = (Stage) usernameOutput.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Printed out to the controller because we didnt have time to put it into notifications
    public void ForgotClicked(MouseEvent mouseEvent) {
        System.out.println("Denne person har glemt sit kodeord '" + usernameOutput.getText() + "'. Det skal sendes til '" + mailOutput.getText() + "'");
        usernameOutput.setText("");
        mailOutput.setText("");
        message.setVisible(true);
    }
}
