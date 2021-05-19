package presentation.controllers;

import domain.DomainFacade;
import Interfaces.IUser;
import presentation.userManage.Producer;
import presentation.userManage.Systemadministrator;
import presentation.userManage.User;

public class ManageUserController {


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

}
