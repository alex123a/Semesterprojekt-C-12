package presentation.controllers;

import domain.DomainFacade;
import Interfaces.IUser;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

public class ManageUserController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private ComboBox<String> userType;

    @FXML
    private TextField changeUsername;

    @FXML
    private TextField searchUsername;

    @FXML
    private TextField changePassword;

    @FXML
    private ComboBox<String> changeUserType;

    @FXML
    void addUser(ActionEvent event) {
        System.out.println("Test");
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        System.out.println(DomainFacade.getInstance().validateUser(currentUser));
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            String userUsername = username.getText();
            String userPassword = password.getText();
            String userUserType = userType.getValue().toString();
            System.out.println("Username: " + userUsername);
            System.out.println("Password: " + userPassword);
            System.out.println("Type: " + userUserType);
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {

    }

    @FXML
    void updateUser(ActionEvent event) {

    }

    private boolean createUser(String username, String password, String type) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if(DomainFacade.getInstance().validateUser(currentUser)) {
            IUser user = (type.equals("Systemadministrator")) ? new Systemadministrator(username, password) : new Producer(username, password);
            return DomainFacade.getInstance().addUser(user);
        }
        return false;
    }

    private IUser createTempUser(String username) {
        return new User(username);
    }

    private boolean changeUsername(String userUsername, String newUsername) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            IUser tempUser = createTempUser(userUsername);
            IUser user = DomainFacade.getInstance().getUser(tempUser);
            user.setUsername(newUsername);
            return DomainFacade.getInstance().editUser(user);
        }
        return false;
    }

    private boolean changePassword(String userUsername, String newPassword) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if(DomainFacade.getInstance().validateUser(currentUser))
        {
            IUser tempUser = createTempUser(userUsername);
            IUser user = DomainFacade.getInstance().getUser(tempUser);
            user.setPassword(newPassword);
            return DomainFacade.getInstance().editUser(user);
        }
        return false;
    }

    @FXML
    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Producer", "Systemadministrator");
        userType.setItems(roles);
        changeUserType.setItems(roles);
    }

}
