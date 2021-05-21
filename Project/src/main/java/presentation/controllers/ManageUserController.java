package presentation.controllers;

import Interfaces.IAdministrator;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import presentation.Repository;
import Interfaces.IUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageUserController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;

    @FXML
    private Button removeUserBtn;

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
    private ComboBox<String> searchUsernameEdit;

    @FXML
    private ComboBox<String> searchUsernameRemove;

    @FXML
    private TextField changePassword;

    @FXML
    private TextField removeSearchUsername;

    @FXML
    private TextField removeUserRoleField;

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
//    TODO SEARCH SHIT

    @FXML
    void updateUser(ActionEvent event) {
        String editUsername = Repository.getInstance().domainFacade.getInfoFromSearch(searchUsernameEdit.getValue(), "username");
        IUser tempUser = new User(editUsername);
        IUser user = Repository.getInstance().domainFacade.getUser(tempUser);
        if (!changeUsername.getText().equals("")) {
            user.setUsername(changeUsername.getText());
        }
        if (!changePassword.getText().equals("")) {
            user.setPassword(Repository.getInstance().domainFacade.generateStrongPasswordHash(changePassword.getText()));
        }
        boolean success = Repository.getInstance().domainFacade.editUser(user);

        if (success) {
            changeUsername.setText("");
            changePassword.setText("");
            changeUsername.setText("");
            changeUserResult.setText("Successfully changed the information");
            changeUserResult.setTextFill(Color.web("#4BB543"));
            searchUsernameEdit.setValue("");
        } else {
            changeUserResult.setText("Error: Something went wrong, try again. \nMake sure that the username does not already exist!");
            changeUserResult.setTextFill(Color.web("#FF0000"));
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        String removeUsername = Repository.getInstance().domainFacade.getInfoFromSearch(searchUsernameRemove.getValue(), "username");
        IUser tempUser = new User(removeUsername);
        IUser removeUser = Repository.getInstance().domainFacade.getUser(tempUser);
        boolean success = Repository.getInstance().domainFacade.deleteUser(removeUser);
        if(success) {
            searchUsernameRemove.setValue("");
            removeUserResult.setText("Successfully removed the user: " +  removeUsername);
            removeUserResult.setTextFill(Color.web("#4BB543"));
            removeUserBtn.setDisable(true);
        } else {
            removeUserResult.setText("Error: Something went wrong, try again. \nMake sure that the user exist!");
            removeUserResult.setTextFill(Color.web("#FF0000"));
            removeUserBtn.setDisable(false);
        }
    }

    @FXML
    void getRemoveUsers(MouseEvent event) {
        String removeUserRole = Repository.getInstance().domainFacade.getInfoFromSearch(searchUsernameRemove.getValue(), "role");
        removeUserRoleField.setText(removeUserRole);
        removeUserBtn.setDisable(false);
    }

    @FXML
    void userSearcher(KeyEvent event2) {
        List<IUser> matchedUsers;
        ObservableList<String> userInfo = FXCollections.observableArrayList();
        IUser tempUser;
        if (tab2.isSelected()) {
            String searchUsernameEditText = searchUsernameEdit.getEditor().getText();
            tempUser = new User(searchUsernameEditText);
            matchedUsers = Repository.getInstance().domainFacade.getUsersBySearch(tempUser);
            searchUsernameEdit.setItems(createList(matchedUsers));
        } else {
            String searchUsernameRemoveText = searchUsernameRemove.getEditor().getText();
            tempUser = new User(searchUsernameRemoveText);
            matchedUsers = Repository.getInstance().domainFacade.getUsersBySearch(tempUser);
            searchUsernameRemove.setItems(createList(matchedUsers));
        }
    }

    private ObservableList<String> createList(List<IUser> list) {
        ObservableList<String> userInfo = FXCollections.observableArrayList();
        for(IUser user : list) {
            boolean isAdmin = user instanceof IAdministrator;
            String type = isAdmin ? "Systemadministrator" : "Producer";
            String text = "Brugernavn: " + user.getUsername() + " Type: " + type;
            userInfo.add(text);
        }
        return userInfo;
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
        removeUserBtn.setDisable(true);
    }

}
