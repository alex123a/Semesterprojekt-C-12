package domain.authentication;

import Interfaces.IUser;
import Interfaces.IUserAuthentication;

public class UserAuthentication implements IUserAuthentication {

    @Override
    public boolean validateUser(IUser user) {
        return false;
    }

}
