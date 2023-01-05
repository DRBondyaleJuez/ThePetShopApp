package utils;

import java.util.Random;

public class EncryptionHandler {

    private final String key;
    private final int saltSize;
    private final int transpositionValue;

    public EncryptionHandler() {
        key = ""+1010110011+0110001011+0111111001;
        saltSize = 500;
        transpositionValue = 300;
    }


    //PROBABLY BOTH METHODS DO THE SAME BUT FOR THE SAKE OF COMPREHENSION THEY ARE GOING TO BE SEPARATED IN TWO METHODS
    public String encrypt(String textToEncrypt){

        //Generate salt string
        String randomSaltString = getSaltString();

        //Turn string to corresponding byte array
        byte[] keyByteArray = key.getBytes();
        String saltedTextToEncrypt = textToEncrypt + randomSaltString;
        byte[] saltedByteArrayToEncrypt = saltedTextToEncrypt.getBytes();

        //Transposing salted byte array
        byte[] transposedByteArray = transposition(saltedByteArrayToEncrypt);

        //XOR encryption
        byte[] encryptedByteArray = new byte[transposedByteArray.length];
        for (int i = 0; i < transposedByteArray.length; i++) {
            encryptedByteArray[i] = (byte) (transposedByteArray[i] ^ keyByteArray[i% keyByteArray.length]);
        }

        //Turn encrypted byte array to corresponding string

        return new String(encryptedByteArray);
    }

    private byte[] transposition(byte[] byteArrayToTranspose){

        byte[] transposedByteArray = new byte[byteArrayToTranspose.length];
        for (int i = 0; i < transposedByteArray.length; i++) {
            transposedByteArray[(i + transpositionValue) % transposedByteArray.length] = byteArrayToTranspose[i];
        }

        return transposedByteArray;
    }

    public String decrypt(String textToDecrypt){

        //Turn string to corresponding byte array
        byte[] keyByteArray = key.getBytes();
        byte[] byteArrayToDecrypt = textToDecrypt.getBytes();
        byte[] decryptedByteArray = new byte[byteArrayToDecrypt.length];
        for (int i = 0; i < byteArrayToDecrypt.length; i++) {
            decryptedByteArray[i] = (byte) (byteArrayToDecrypt[i] ^ keyByteArray[i% keyByteArray.length]);
        }
        byte[] detransposedByteArray = detransposition(decryptedByteArray);

        String decryptedSaltedString = new String(detransposedByteArray);

        String decryptedUnsaltedString = decryptedSaltedString.substring(0, decryptedSaltedString.length() - saltSize);

        //Turn decrypted byte array to corresponding string
        return new String(decryptedUnsaltedString);
    }
    
    private byte[] detransposition(byte[] byteArrayToDetranspose){

        byte[] detransposedByteArray = new byte[byteArrayToDetranspose.length];
        for (int i = 0; i < detransposedByteArray.length; i++) {
            detransposedByteArray[i] = byteArrayToDetranspose[(i + transpositionValue) % detransposedByteArray.length];
        }

        return detransposedByteArray;
        
    }

    private String getSaltString() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!$&@_-()[].,*";//1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < saltSize) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
