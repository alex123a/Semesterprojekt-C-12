package data.userHandling;

import Interfaces.IUser;
import Interfaces.IUserFacade;

import java.util.List;

public class UserFacade implements IUserFacade {

    @Override
    public List<IUser> getUsers() {
        return UserManager.getInstance().getUsers();
    }

    @Override
    public boolean makeUserProducer(IUser user) {
        return UserManager.getInstance().makeUserProducer(user);
    }

    @Override
    public boolean makeUserAdmin(IUser user) {
        return UserManager.getInstance().makeUserAdmin(user);
    }

    @Override
    public boolean deleteUser(IUser user) {
        return UserManager.getInstance().deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        return UserManager.getInstance().editUser(user);
    }
}
