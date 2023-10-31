package Testing;
// Assignment 1: AES (Advanced Encryption Standard) Block Cipher
// Name: Kline Borromeo



// import crypto package
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyAESapp {
    private static SecretKeySpec secretKey;
    private static byte[] key; // create a byte array to store the key

    // setkey
    public static void setkey(String myKey) {
        try {
            key = myKey.getBytes("UTF-8");
            /* Checksum: error detection method to make sure everything on one end is the same on the other end. e.g. bytes on one end is 5 so other end should be 5 */
            /* Hash Function: A function to produce checksum */ 
            /* Hash value (it is a numeric value of fixed length that uniquely identifies data ) */
            /* Message Digest: It is a fixed size numeric representstion of the contents of the message computed by who has the hash function */
            /* In Java, MessageDigest class provides functionality of a massage digest using algorithms such as SHA-1 or SHA-256 */
            /* SHA = Secure Hashing Algorithm  */

            // 1
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key); // using md, we create the digest which will become our key
            key = Arrays.copyOf(key, 16); // original, new length 16
            secretKey = new SecretKeySpec(key, "AES");

        }
        catch (NoSuchAlgorithmException e) {     }
        catch (UnsupportedEncodingException e) {   }
    }

    // 2: Encryption
    public static String encryptData(String strToEncrypt, String sec) {
        try {
            setkey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB,PKCS5Padding");

        }
        catch (Exception e) {

        }
        return null;
    }
    // 3: Decryption


}
