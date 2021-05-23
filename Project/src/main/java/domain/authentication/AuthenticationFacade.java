package domain.authentication;

import Interfaces.IAuthenticationHandler;
import Interfaces.IUser;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthenticationFacade implements IAuthenticationHandler {
    private static final AuthenticationFacade AUTHENTICATION_FACADE = new AuthenticationFacade();

    private AuthenticationFacade() {

    }

    public static IAuthenticationHandler getInstance() {return AUTHENTICATION_FACADE;}

    @Override
    public boolean login(IUser user) {
        return LoginAuthentication.getInstance().login(user);
    }

    @Override
    public String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return LoginAuthentication.getInstance().generateStrongPasswordHash(password);
    }

    @Override
    public boolean validateUser(IUser user) {
        return UserAuthentication.getInstance().validateUser(user);
    }
}
