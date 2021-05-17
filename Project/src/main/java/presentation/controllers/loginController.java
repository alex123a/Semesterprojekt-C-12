package presentation.controllers;

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

import java.io.IOException;

public class loginController {
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

    @FXML
    public void initialize() {
        openEye = new Image(getClass().getResourceAsStream("/images/openEye.png"));
        closedEye = new Image(getClass().getResourceAsStream("/images/closedEye.png"));

        passwordToggle.setImage(closedEye);
    }

    public void loginClicked(MouseEvent mouseEvent) {
        wrongInput.setVisible(false);

        if(passwordToggle.getImage().equals(closedEye)) {
            if (usernameInput.getText().equals("") && passwordHiddenInput.getText().equals("")) {
                emptyLogin();
            }
        }
        else {
            if (usernameInput.getText().equals("") && passwordShownInput.getText().equals("")) {
                emptyLogin();
            }
        }

        // todo: Send login information to be checked
    }

    private void failedLogin() {
        wrongInput.setVisible(true);
        usernameInput.setText("");
        passwordShownInput.setText("");
        passwordHiddenInput.setText("");
    }

    private void emptyLogin() {
        wrongInput.setVisible(true);
        wrongInput.setText("Udfyld felterne");
    }

    public void togglePassword(MouseEvent mouseEvent) {
        if(passwordToggle.getImage().equals(closedEye)) {
            passwordToggle.setImage(openEye);
            passwordHiddenInput.setVisible(false);
            passwordShownInput.setVisible(true);
            passwordShownInput.setText(passwordHiddenInput.getText());
        }
        else {
            passwordToggle.setImage(closedEye);
            passwordHiddenInput.setVisible(true);
            passwordShownInput.setVisible(false);
            passwordHiddenInput.setText(passwordShownInput.getText());
        }
    }

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
