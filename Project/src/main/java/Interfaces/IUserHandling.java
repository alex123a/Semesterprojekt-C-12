package Interfaces;

import java.util.List;

public interface IUserHandling {

    List<IUser> getUsers();

    boolean deleteUser(IUser user);

    boolean editUser(IUser user);

    boolean addUser(IUser user);

    IUser getUser(IUser user);
    
    String getDatabasePassword(IUser user);

    List<IUser> getUsersBySearch(IUser user);
}
