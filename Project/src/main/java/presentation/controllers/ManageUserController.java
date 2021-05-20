package presentation.controllers;

import domain.DomainFacade;
import Interfaces.IUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

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
    void addUser(ActionEvent event) {
        boolean success = false;
        String userUsername = username.getText();
        String userPassword = DomainFacade.getInstance().generateStrongPasswordHash(password.getText());
        String userUserType = userType.getValue();
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            IUser user = (userUserType.equals("Systemadministrator")) ? new Systemadministrator(userUsername, userPassword) : new Producer(userUsername, userPassword);
            success = DomainFacade.getInstance().addUser(user);
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
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        boolean success = false;
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            IUser tempUser = new User(removeUsername);
            IUser removeUser = DomainFacade.getInstance().getUser(tempUser);
            if (removeUser != null) {
                if (!removeUsername.equals("")) {
                    success = DomainFacade.getInstance().deleteUser(removeUser);
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
        boolean success = false;
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            String searchedUser = editSearchUsername.getText();
            IUser tempUser = new User(searchedUser);
            IUser user = DomainFacade.getInstance().getUser(tempUser);

            if (!changeUsername.getText().equals("")) {
                user.setUsername(changeUsername.getText());
            }
            if (!changePassword.getText().equals("")) {
                user.setPassword(DomainFacade.getInstance().generateStrongPasswordHash(changePassword.getText()));
            }
            success = DomainFacade.getInstance().editUser(user);
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

    @FXML
    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Producer", "Systemadministrator");
        userType.setItems(roles);
    }

}
