package domain.authentication;

import Interfaces.IAuthenticator;
import Interfaces.IUserAuthentication;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class AuthenticationHandler {
    private static IUserAuthentication userAuthentication = UserAuthentication.getInstance();
    private static IAuthenticator loginAuthentication = LoginAuthentication.getInstance();


    public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Explained in validate password except the salt is here random generated
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(salt) + toHex(hash) + "";
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        //random generated salt from the algorithm SHA1PRNG
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        // Changes a byte array to Hex
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    static boolean validatePassword(String parsedPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Checks if the stored password from database is there (The username has a password)
        if (storedPassword == null) {
            System.out.println("Not in the database");
            return false;
        }

        // Iterations is how its hashed when saved, Salt is always the first 32 chars
        int iterations = 1000;
        byte[] salt = fromHex(storedPassword.substring(0, 32));
        byte[] hash = fromHex(storedPassword.substring(32));

        // Convert the password characters to a PBE key by creating an instance of the appropriate secret-key factory, the factory is PBKDF2WithHmacSHA1
        // This creates a hash from the input that we can compare with the stored password
        PBEKeySpec spec = new PBEKeySpec(parsedPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        // Comparing the stored with the parsed password, if one of the chars is different the passwords are different
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            if (!(hash[i] == testHash[i])) {
                return false;
            }
        }
        return true;
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        // Changes hex to byte, byte is half the size therefore length divided 2
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static IAuthenticator getLoginInstance() {
        return loginAuthentication;
    }

    public static IUserAuthentication getUserInstance() {
        return userAuthentication;
    }
}
