package domain.authentication;

import Interfaces.IAdministrator;
import Interfaces.IUser;
import Interfaces.IUserAuthentication;
import data.userHandling.SystemAdministrator;
import data.userHandling.User;

public class UserAuthentication implements IUserAuthentication {
    private static final UserAuthentication userAuthentication = new UserAuthentication();

    private UserAuthentication() {

    }

    @Override
    public boolean validateUser(IUser user) {
        return user instanceof IAdministrator;
    }

    public static IUserAuthentication getInstance() {
        return userAuthentication;
    }
}
