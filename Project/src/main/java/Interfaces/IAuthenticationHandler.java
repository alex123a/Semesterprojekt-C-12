package Interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IAuthenticationHandler {
    String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
