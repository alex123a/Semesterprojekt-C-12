package domain.authentication;

import Interfaces.IAuthenticator;
import Interfaces.IUser;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import domain.DomainFacade;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginAuthentication implements IAuthenticator {
    private final static LoginAuthentication login = new LoginAuthentication();

    private LoginAuthentication() {

    }

    @Override
    public boolean login(IUser user) {
        return user.getPassword().equals(DomainFacade.getInstance().getDatabasePassword(user));
    }

    private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        // Changes hex to byte, byte is half the size therefore length divided 2
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    @Override
    public String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Iterations is how many times the hash is iterated through the PBE key, salt is randomly generated from getSalt()
        // High iterations secures the password, salt secure uniqueness, because the hash is not unique in itself.
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        // Convert the password characters to a PBE key by creating an instance of the appropriate secret-key factory, the factory is PBKDF2WithHmacSHA1
        // This creates a hash from the input that we can compare with the stored password
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(salt) + toHex(hash) + "";
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        //random generated salt from the algorithm SHA1PRNG
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        // Changes a byte array to Hex
        BigInteger bigInt = new BigInteger(1, array);
        String hex = bigInt.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static LoginAuthentication getInstance() {
        return login;
    }
}
