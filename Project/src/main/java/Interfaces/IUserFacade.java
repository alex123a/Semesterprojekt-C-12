package Interfaces;

import java.util.List;

public interface IUserFacade {

    List<IUser> getUsers();

    boolean makeUserProducer(IUser user);

    boolean makeUserAdmin(IUser user);

    boolean deleteUser(IUser user);

    boolean editUser(IUser user);

    IUser getUser(IUser user);

    String getDatabasePassword(IUser user);
}
