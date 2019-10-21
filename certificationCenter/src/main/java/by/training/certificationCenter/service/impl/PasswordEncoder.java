package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.service.exception.ServiceException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncoder {
    /**
     * This value can be used to adjust the speed of the algorithm.
     */
    private static final int ITERATIONS = 10000;
    /**
     * This is the required output length of the hashed function.
     */
    private static final int KEY_LENGTH = 256;

    public static String encodePassword(
            final String login, final String password) throws ServiceException {
        byte[] salt = login.getBytes();
        char[] passwordChars = password.toCharArray();
        byte[] hashedBytes = hashPassword(passwordChars, salt);
        return Hex.encodeHexString(hashedBytes);
    }

    private static byte[] hashPassword(
            final char[] passwordChars, final byte[] salt)
            throws ServiceException {
        try {
            SecretKeyFactory skf = SecretKeyFactory.
                    getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(passwordChars, salt,
                    ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ServiceException("message.user.password.notStrong");
        }
    }
}
