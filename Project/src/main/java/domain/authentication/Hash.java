package domain.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hash {
    public static void main(String[] args) {
        try {
            System.out.println(AuthenticationHandler.generateStrongPasswordHash("badehotellet123"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
