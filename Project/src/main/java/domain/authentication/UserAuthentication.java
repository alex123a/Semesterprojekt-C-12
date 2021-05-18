package domain.authentication;

import Interfaces.IUser;
import Interfaces.IUserAuthentication;

public class UserAuthentication implements IUserAuthentication {
    static final UserAuthentication userAuthentication = new UserAuthentication();

    @Override
    public boolean validateUser(IUser user) {
        //return PersistenceFacade.getInstance().getUser(user) instanceof SystemAdministrator;
        return true;
    }

    public static IUserAuthentication getInstance() {
        return userAuthentication;
    }
}
