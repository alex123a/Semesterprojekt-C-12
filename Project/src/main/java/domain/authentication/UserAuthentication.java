package domain.authentication;

import Interfaces.IUser;
import Interfaces.IUserAuthentication;
import data.userHandling.SystemAdministrator;

public class UserAuthentication implements IUserAuthentication {
    @Override
    public boolean validateUser(IUser user) {
        return user instanceof SystemAdministrator;
    }
}
