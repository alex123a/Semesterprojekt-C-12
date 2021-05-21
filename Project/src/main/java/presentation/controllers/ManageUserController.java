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
import java.io.IOException;
import java.util.List;

public class ManageUserController {

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
    private TextField removeUserRoleField;

    @FXML
    ImageView backButton;

    @FXML
    void addUser(ActionEvent event) {
        String userUsername = username.getText();
        String userPassword = password.getText();
        String userUserType = userType.getValue();
        boolean success = false;
        if(userUserType != null) {
            IUser user = (userUserType.equals("Systemadministrator")) ? new Systemadministrator(userUsername, userPassword) : new Producer(userUsername, userPassword);
            success = Repository.getInstance().domainFacade.addUser(user);
        }
        if (success) {
            username.clear();
            password.clear();
            userType.setValue("");
            addUserResult.setText("Brugeren: " + userUsername + " blev tilføjet!");
            addUserResult.setTextFill(Color.web("#4BB543"));
        } else {
            addUserResult.setText("Der skete en fejl, prøv igen. \nTjek at brugernavnet ikke allerede eksisterer!");
            addUserResult.setTextFill(Color.web("#FF0000"));
        }
        resetSearches();
    }

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
            changeUsername.clear();
            changePassword.clear();
            changeUsername.clear();
            changeUserResult.setText("Informationerne blev ændret");
            changeUserResult.setTextFill(Color.web("#4BB543"));
            searchUsernameEdit.setValue("");
        } else {
            changeUserResult.setText("Der skete en fejl, prøv igen. \nTjek at brugernavnet ikke allerede eksisterer!");
            changeUserResult.setTextFill(Color.web("#FF0000"));
        }
        resetSearches();
    }

    @FXML
    void deleteUser(ActionEvent event) {
        String removeUsername = Repository.getInstance().domainFacade.getInfoFromSearch(searchUsernameRemove.getValue(), "username");
        IUser tempUser = new User(removeUsername);
        IUser removeUser = Repository.getInstance().domainFacade.getUser(tempUser);
        boolean success = Repository.getInstance().domainFacade.deleteUser(removeUser);
        if(success) {
            searchUsernameRemove.setValue("");
            removeUserRoleField.clear();
            removeUserResult.setText("Brugeren: " +  removeUsername + " blev fjernet!");
            removeUserResult.setText("Brugeren: " +  removeUsername + " blev fjernet!");
            removeUserResult.setTextFill(Color.web("#4BB543"));
            removeUserBtn.setDisable(true);
        } else {
            removeUserResult.setText("Der skete en fejl, prøv igen. \nTjek at brugeren eksisterer! \n Du kan ikke fjerne dig selv!");
            removeUserResult.setTextFill(Color.web("#FF0000"));
            removeUserBtn.setDisable(false);
        }
        resetSearches();
    }

    @FXML
    void getRemoveUsers(MouseEvent event) {
        String removeUserRole = Repository.getInstance().domainFacade.getInfoFromSearch(searchUsernameRemove.getValue(), "role");
        removeUserRoleField.setText(removeUserRole);
        removeUserBtn.setDisable(removeUserRole.equals(""));
    }

    @FXML
    void userSearcher(KeyEvent event) {
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
        if (searchUsernameRemove.getEditor().getText().isEmpty()) {
            removeUserBtn.setDisable(true);
        }
    }

    private ObservableList<String> createList(List<IUser> list) {
        ObservableList<String> userInfo = FXCollections.observableArrayList();
        for(IUser user : list) {
            boolean isAdmin = user instanceof IAdministrator;
            String type = isAdmin ? "Systemadministrator" : "Producer";
            String text = "Brugernavn: " + user.getUsername() + " Rolle: " + type;
            userInfo.add(text);
        }
        return userInfo;
    }

    private void resetSearches() {
        searchUsernameEdit.setItems(createList(Repository.getInstance().domainFacade.getUsers()));
        searchUsernameRemove.setItems(createList(Repository.getInstance().domainFacade.getUsers()));
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
        resetSearches();
    }

}
