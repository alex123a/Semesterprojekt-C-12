package domain.authentication;

import Interfaces.IUser;
import Interfaces.IUserAuthentication;
import data.userHandling.SystemAdministrator;

public class UserAuthentication implements IUserAuthentication {
    static final UserAuthentication userAuthentication = new UserAuthentication();

    @Override
    public boolean validateUser(IUser user) {
        return user instanceof SystemAdministrator;
    }

    public static IUserAuthentication getInstance() {
        return userAuthentication;
    }
}
