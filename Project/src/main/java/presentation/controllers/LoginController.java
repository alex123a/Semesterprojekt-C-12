package presentation.controllers;

import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

import java.io.IOException;

public class LoginController {
    @FXML
    ImageView backArrow;
    @FXML
    Label wrongInput;
    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordHiddenInput;
    @FXML
    TextField passwordShownInput;
    @FXML
    ImageView passwordToggle;
    Image openEye;
    Image closedEye;



    // Sets up the toggle show/hide the password when the page loads
    @FXML
    public void initialize() {
        openEye = new Image(getClass().getResourceAsStream("/images/openEye.png"));
        closedEye = new Image(getClass().getResourceAsStream("/images/closedEye.png"));

        passwordToggle.setImage(closedEye);
    }

    // Method for when the user clicks login
    public void loginClicked(MouseEvent mouseEvent) {
        wrongInput.setVisible(false);

        // Checks if the input is empty
        // There is two password inputs
        // One for if the password is hidden and one for if the password isn't hidden
        if (passwordToggle.getImage().equals(closedEye)) {
            checkPassword(passwordHiddenInput);
        } else {
            checkPassword(passwordShownInput);
        }
    }

    private void checkPassword(TextField passwordShownInput) {
        if (usernameInput.getText().equals("") && passwordShownInput.getText().equals("")) {
            emptyLogin();
        } else {
            User user = new User(usernameInput.getText(), passwordHiddenInput.getText());
            if (DomainFacade.getInstance().login(user)) {
                if (DomainFacade.getInstance().validateUser(DomainFacade.getInstance().getUser(user))) {
                    DomainFacade.getInstance().setCurrentUser(new Systemadministrator(usernameInput.getText(), passwordHiddenInput.getText()));
                    System.out.println("ADmini");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
                        Stage window = (Stage) backArrow.getScene().getWindow();
                        window.setScene(new Scene(root, 1300, 700));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    DomainFacade.getInstance().setCurrentUser(new Producer(usernameInput.getText(), passwordHiddenInput.getText()));
                    System.out.println("producer");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/layout/menuProducer.fxml"));
                        Stage window = (Stage) backArrow.getScene().getWindow();
                        window.setScene(new Scene(root, 1300, 700));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                failedLogin();
            }
        }
    }

    // Method to show that the login is wrong
    private void failedLogin() {
        wrongInput.setVisible(true);
        usernameInput.setText("");
        passwordShownInput.setText("");
        passwordHiddenInput.setText("");
    }

    // Method to show that the login is empty
    private void emptyLogin() {
        wrongInput.setVisible(true);
        wrongInput.setText("Udfyld felterne");
    }

    // Method to toggle show/hide password
    public void togglePassword(MouseEvent mouseEvent) {
        if (passwordToggle.getImage().equals(closedEye)) {
            passwordToggle.setImage(openEye);
            passwordHiddenInput.setVisible(false);
            passwordShownInput.setVisible(true);
            passwordShownInput.setText(passwordHiddenInput.getText());
        } else {
            passwordToggle.setImage(closedEye);
            passwordHiddenInput.setVisible(true);
            passwordShownInput.setVisible(false);
            passwordHiddenInput.setText(passwordShownInput.getText());
        }
    }

    // Method to go back to the menu
    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menu.fxml"));
            Stage window = (Stage) backArrow.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
