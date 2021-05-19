package domain.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class Test {
    private static Connection connection = null;

    public static void main(String[] args) {
        try {
            System.out.println(AuthenticationHandler.generateStrongPasswordHash("admin"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
