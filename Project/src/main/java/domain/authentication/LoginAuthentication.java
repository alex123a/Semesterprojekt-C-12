package domain.authentication;

import Interfaces.IAuthenticator;
import Interfaces.IUser;
import data.PersistenceFacade;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static domain.authentication.AuthenticationHandler.validatePassword;

public class LoginAuthentication implements IAuthenticator {
    private final static LoginAuthentication login = new LoginAuthentication();

    @Override
    public boolean login(IUser user) {
        try {
            return validatePassword(user.getPassword(),PersistenceFacade.getInstance().getDatabasePassword(user));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static LoginAuthentication getInstance() {
        return login;
    }
}
