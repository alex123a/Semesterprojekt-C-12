package domain.authentication;

import Interfaces.IAdministrator;
import Interfaces.IUser;
import Interfaces.IUserAuthentication;
import data.userHandling.SystemAdministrator;

public class UserAuthentication implements IUserAuthentication {
    static final UserAuthentication userAuthentication = new UserAuthentication();

    @Override
    public boolean validateUser(IUser user) {
        return user instanceof IAdministrator;
    }

    public static IUserAuthentication getInstance() {
        return userAuthentication;
    }
}
