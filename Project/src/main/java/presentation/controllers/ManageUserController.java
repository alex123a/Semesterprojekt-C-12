package presentation.controllers;

import domain.DomainFacade;
import Interfaces.IUser;
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
    private TextField editSearchUsername;

    @FXML
    private TextField changePassword;

    @FXML
    private TextField removeSearchUsername;

    @FXML
    void addUser(ActionEvent event) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            String userUsername = username.getText();
            String userPassword = DomainFacade.getInstance().generateStrongPasswordHash(password.getText());
            String userUserType = userType.getValue();
            IUser user = (userUserType.equals("Systemadministrator")) ? new Systemadministrator(userUsername, userPassword) : new Producer(userUsername, userPassword);
            DomainFacade.getInstance().addUser(user);
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        boolean success = false;
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            String removeUsername = removeSearchUsername.getText();
            IUser tempUser = new User(removeUsername);
            IUser removeUser = DomainFacade.getInstance().getUser(tempUser);
            if (!removeUsername.equals("")) {
                success = DomainFacade.getInstance().deleteUser(removeUser);
            }
        }
        if(success) {
            System.out.println("Nice");
        } else {
            System.out.println("Sheisse");
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        IUser currentUser = DomainFacade.getInstance().getCurrentUser();
        if (DomainFacade.getInstance().validateUser(currentUser)) {
            String searchedUser = editSearchUsername.getText();
            //Temporary user to fetch the wanted user from the DB.
            IUser tempUser = new User(searchedUser);
            IUser user = DomainFacade.getInstance().getUser(tempUser);

            if (!changeUsername.getText().equals("")) {
                user.setUsername(changeUsername.getText());
            }
            if (!changePassword.getText().equals("")) {
                user.setPassword(DomainFacade.getInstance().generateStrongPasswordHash(changePassword.getText()));
            }
            DomainFacade.getInstance().editUser(user);
        }
    }

    @FXML
    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Producer", "Systemadministrator");
        userType.setItems(roles);
    }

}
