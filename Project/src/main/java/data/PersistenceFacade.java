package data;

import Interfaces.IPersistenceFacade;
import Interfaces.IUser;
import data.userHandling.UserFacade;

import java.util.List;

public class PersistenceFacade implements IPersistenceFacade {
    @Override
    public List<IUser> getUsers() {
        return UserFacade.getInstance().getUsers();
    }

    @Override
    public boolean makeUserProducer(IUser user) {
        return UserFacade.getInstance().makeUserProducer(user);
    }

    @Override
    public boolean makeUserAdmin(IUser user) {
        return UserFacade.getInstance().makeUserAdmin(user);
    }

    @Override
    public boolean deleteUser(IUser user) {
        return UserFacade.getInstance().deleteUser(user);
    }

    @Override
    public boolean editUser(IUser user) {
        return UserFacade.getInstance().editUser(user);
    }

    @Override
    public String getDatabasePassword(String username) {
        return UserFacade.getInstance().getDatabasePassword(username);
    }
}
