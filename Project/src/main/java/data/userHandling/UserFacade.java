package data.userHandling;

import Interfaces.IProduction;
import Interfaces.IUser;
import Interfaces.IUserFacade;

import java.util.List;

public class UserFacade implements IUserFacade {
    private static final UserFacade USERFACADE = new UserFacade();

    @Override
    public List<IUser> getUsers() {
        return UserManager.getInstance().getUsers();
    }

    @Override
    public boolean deleteUser(IUser user) {
        return UserManager.getInstance().deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        return UserManager.getInstance().editUser(user);
    }

    @Override
    public boolean addUser(IUser user) {
        return UserManager.getInstance().addUser(user);
    }

    @Override
    public IUser getUser(IUser user) {
        return UserManager.getInstance().getUser(user);
    }

    @Override
    public String getDatabasePassword(IUser user) {
        return UserManager.getInstance().getDatabasePassword(user);
    }

    public static UserFacade getInstance() {
        return USERFACADE;
    }
}
