// CA4005 Assignment 1, Symmetric File Encryption Using Password and Salt
// Name: Kline Borromeo
// ID: 19372763

/****************************************************************************
 * Declaration of Non-Plagiarism:                                           *
 *    A declaration that this is solely my own work                         *
 *    (except elements that are explicitly attributed to another source).   *
 ****************************************************************************/

// Details:
//    The code performs some cryptographic operations like password-based key derivation, AES encryption, and RSA encryption.
//    It generates a random salt and initialization vector (IV)
//    Reads a Java class file ("Assignment1.class"), adds padding, and encrypts it using AES in CBC mode.
//    It RSA-encrypts a password and writes the results to various output files.
//      - GK = Geoff's Key
//      - Block Cipher AES (Advanced Ecryption Standard)
//      - 128 bit key, CBC mode, no padding
//      - Encrypt and decrypt a message using the above parameters
//
// References:
//     CA4005 Study Notes Week 3: Symmetric Cryptography
//     SimpliLearn - https://www.simplilearn.com/tutorials/java-tutorial/stringbuilder-in-java
//     Java AES Cryptography Example | Java Encryption Decryption Example | JCA and JCE Example - https://www.youtube.com/watch?v=a3ZdcDvUwdY
//
// Updates:
//    26/10/2023: Added bytesToHex method
//    27/10/2023: Fixing AES no spaces issue
//    28/10/2023: bytesToHex Working
//    30/10/2023: Commenting, Check Output and Refactoring 
//

/* 
    To Compile + Run:
        (compile)
        javac Assignment1.java
   
        (run)
        java Assignment1.java 
        
        (run + write to Encryption.txt, with terminal output)
        java Assignment1 Assignment1.class > Encryption.txt

        (to remove Files Generated)
        rm Encryption.txt IV.txt Password_raw.txt Password.txt Salt.txt Assignment1.class
*/

// Import necessary packages
import java.security.SecureRandom;
import java.security.MessageDigest;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileOutputStream;
import java.math.BigInteger;

public class Assignment1 {
    // Geoff's Key = (GK)
    static private String GK = "c406136c640a665900a9df4df63a84fc855927b729a3a106fb3f379e8e4190ebba442f67b93402e535b18a5777e6490e67dbee954bb02175e43b6481e7563d3f9ff338f07950d1553ee6c343d3f8148f71b4d2df8da7efb39f846ac07c865201fbb35ea4d71dc5f858d9d41aaa856d50dc2d2732582f80e7d38c32aba87ba9";

    // Method to convert bytes to hexadecimal
    static private String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(); // used to create a mutable, or in other words, a modifiable succession of characters
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Method to encode a string in UTF-8 format
    static private byte[] utf8Encoder(String password) {
        try {
            return password.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to generate random 128-bit data
    static private byte[] random128bit() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16]; // 16 * 8 = 128 bits
        random.nextBytes(bytes);
        return bytes;
    }

    // Method to append a Password and a Salt
    static private byte[] appendPasswordAndSalt(byte[] password, byte[] salt) {
        byte[] tmp = new byte[password.length + salt.length];
        for (int i = 0; i < password.length; i++) {
            tmp[i] = password[i];
        }
        for (int i = 0; i < salt.length; i++) {
            tmp[i + password.length] = salt[i];
        }
        return tmp;
    }

    // Method to compute the SHA-256 hash of a byte array
    static private byte[] sha256(byte[] passAndSalt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (int i = 1; i < 200; i++)
                passAndSalt = md.digest(passAndSalt);
            return passAndSalt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to read a file and convert it to a byte array
    private static byte[] readFileToByteArray(String file_name) {
        try {
            Path file_path = Paths.get(file_name);
            return Files.readAllBytes(file_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to add padding to a byte array
    private static byte[] addPadding(byte[] file, int blockSizeInBits) {
        int blockSizeInBytes = blockSizeInBits / 8;
        int leftOver = file.length % blockSizeInBytes;
        byte[] padded_array = new byte[file.length + (blockSizeInBytes - leftOver)];
        padded_array[file.length] = (byte) 0x80;
        for (int i = 0; i < file.length; i++) {
            padded_array[i] = file[i];
        }
        return padded_array;
    }

    // Method to encrypt using AES in CBC mode
    private static byte[] encrypt_AES_CBC(byte[] hashed_pass, byte[] iv, byte[] file) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec keySpec = new SecretKeySpec(hashed_pass, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted_file = cipher.doFinal(file);
            return encrypted_file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to perform modular exponentiation
    private static BigInteger modularExp(BigInteger p, String exponent, BigInteger modulus) {
        BigInteger encrypted = BigInteger.ONE;
        for (int i = 0; i < exponent.length(); i++) {
            if (exponent.charAt(i) == '1')
                encrypted = encrypted.multiply(p).mod(modulus);
            p = p.multiply(p).mod(modulus);
        }
        return encrypted;
    }

    // Method to write salt to a file
    private static void writeSaltToFile(byte[] salt, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(salt);
            System.out.println("Salt has been written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to write IV to a file
    private static void writeIVToFile(byte[] iv, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(iv);
            System.out.println("IV has been written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to write RSA-encrypted password to a file
    private static void writeRSAEncryptedPasswordToFile(byte[] rsa_encrypted, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(rsa_encrypted);
            System.out.println("RSA-encrypted password has been written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to write the password to a file
    private static void writePasswordToFile(byte[] password, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(password);
            System.out.println("Password has been written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to write the AES-encrypted file to a file
    private static void writeAESFileToFile(byte[] encrypted_file, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            // Convert the AES-encrypted bytes to a hexadecimal string
            String hexString = byteToHex(encrypted_file);

            // Write the hexadecimal string to the file
            fos.write(hexString.getBytes("UTF-8"));

            System.out.println("AES-encrypted class file has been written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String password = "Ultr45tr0NGP@551$345$#$#2156";
        byte[] encoded_pass = utf8Encoder(password);
        System.out.println("Pass = " + password);

        byte[] salt = random128bit();
        System.out.println();
        System.out.println("Salt = " + byteToHex(salt));

        // Write the salt to a file
        writeSaltToFile(salt, "Salt.txt");

        byte[] hashed_pass = sha256(appendPasswordAndSalt(encoded_pass, salt));

        byte[] iv = random128bit();
        System.out.println();
        System.out.println("IV   = " + byteToHex(iv));

        // Write the IV to a file
        writeIVToFile(iv, "IV.txt");

        // Read your class file (Assignment1.class) into a byte array
        byte[] file = readFileToByteArray("Assignment1.class");

        byte[] padded_file = addPadding(file, 128);

        byte[] encrypted_file = encrypt_AES_CBC(hashed_pass, iv, padded_file);

        // Write the AES-encrypted content to a file
        writeAESFileToFile(encrypted_file, "Encryption.txt");

        BigInteger p = new BigInteger(encoded_pass);
        BigInteger e = new BigInteger("65537", 10);
        BigInteger n = new BigInteger(GK, 16);

        byte[] rsa_encrypted = modularExp(p, Integer.toBinaryString(65537), n).toByteArray();
        System.out.println();
        System.out.println("RSA_encrypted_pass:");
        System.out.println(byteToHex(rsa_encrypted));

        // Write the RSA-encrypted password to a file
        writeRSAEncryptedPasswordToFile(rsa_encrypted, "Password.txt");

        // Write the password to a file
        writePasswordToFile(encoded_pass, "Password_raw.txt");
    }
}