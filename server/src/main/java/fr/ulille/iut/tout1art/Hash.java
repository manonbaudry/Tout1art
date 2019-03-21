package fr.ulille.iut.tout1art;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(toHash.getBytes());
        return new String(md.digest());
    }
}