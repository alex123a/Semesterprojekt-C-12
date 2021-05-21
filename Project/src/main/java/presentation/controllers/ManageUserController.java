package presentation.controllers;

import presentation.Repository;
import Interfaces.IUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

import java.io.IOException;

public class ManageUserController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label addUserResult;

    @FXML
    private Label changeUserResult;

    @FXML
    private Label removeUserResult;

    @FXML
    private ComboBox<String> userType;

    @FXML
    private TextField changeUsername;

    @FXML
    private TextField editSearchUsername;

    @FXML
    private TextField changePassword;

    @FXML
    private TextField removeSearchUsername;

    @FXML
    ImageView backButton;

    @FXML
    void addUser(ActionEvent event) {
        boolean success = false;
        String userUsername = username.getText();
        String userPassword = Repository.getInstance().domainFacade.generateStrongPasswordHash(password.getText());
        String userUserType = userType.getValue();
        IUser currentUser = Repository.getInstance().domainFacade.getCurrentUser();
        if (Repository.getInstance().domainFacade.validateUser(currentUser)) {
            IUser user = (userUserType.equals("Systemadministrator")) ? new Systemadministrator(userUsername, userPassword) : new Producer(userUsername, userPassword);
            success = Repository.getInstance().domainFacade.addUser(user);
        }
        if (success) {
            username.setText("");
            password.setText("");
            userType.setValue("");
            addUserResult.setText("Successfully added user: " + userUsername );
            addUserResult.setTextFill(Color.web("#4BB543"));
        } else {
            addUserResult.setText("Error: Something went wrong, try again. \nMake sure that the username does not already exist!");
            addUserResult.setTextFill(Color.web("#FF0000"));
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        String removeUsername = removeSearchUsername.getText();
        IUser currentUser = Repository.getInstance().domainFacade.getCurrentUser();
        boolean success = false;
        if (Repository.getInstance().domainFacade.validateUser(currentUser)) {
            IUser tempUser = new User(removeUsername);
            IUser removeUser = Repository.getInstance().domainFacade.getUser(tempUser);
            if (removeUser != null) {
                if (!removeUsername.equals("")) {
                    success = Repository.getInstance().domainFacade.deleteUser(removeUser);
                }
            }
        }
        if(success) {
            removeSearchUsername.setText("");
            removeUserResult.setText("Successfully removed the user: " +  removeUsername);
            removeUserResult.setTextFill(Color.web("#4BB543"));
        } else {
            removeUserResult.setText("Error: Something went wrong, try again. \nMake sure that the user exist!");
            removeUserResult.setTextFill(Color.web("#FF0000"));
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        boolean success = true;
        IUser currentUser = Repository.getInstance().domainFacade.getCurrentUser();
        if (Repository.getInstance().domainFacade.validateUser(currentUser)) {
            String searchedUser = editSearchUsername.getText();
            IUser tempUser = new User(searchedUser);
            IUser user = Repository.getInstance().domainFacade.getUser(tempUser);
            if (!changeUsername.getText().equals("")) {
                user.setUsername(changeUsername.getText());
                if(Repository.getInstance().domainFacade.getUser(user) != null){
                    success = false;
                } else {
                    success = Repository.getInstance().domainFacade.editUser(user);
                }

            }
            if (!changePassword.getText().equals("") && success) {
                user.setPassword(Repository.getInstance().domainFacade.generateStrongPasswordHash(changePassword.getText()));
                success = Repository.getInstance().domainFacade.editUser(user);
            }
        }
        if (success) {
            changeUsername.setText("");
            changePassword.setText("");
            editSearchUsername.setText("");
            changeUserResult.setText("Successfully changed the information");
            changeUserResult.setTextFill(Color.web("#4BB543"));
        } else {
            changeUserResult.setText("Error: Something went wrong, try again. \nMake sure that the username does not already exist!");
            changeUserResult.setTextFill(Color.web("#FF0000"));
        }
    }

    public void goBack(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/menuAdmin.fxml"));
            Stage window = (Stage) backButton.getScene().getWindow();
            window.setScene(new Scene(root, 1300, 700));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Producer", "Systemadministrator");
        userType.setItems(roles);
    }

}
