package Interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IAuthenticator {

    boolean login(IUser user);
    String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
