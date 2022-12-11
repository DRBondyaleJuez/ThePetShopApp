package utils;

import java.util.UUID;

public class EncryptionHandler {

    private final String key;

    public EncryptionHandler() {
        key = ""+1010110011+0110001011+0111111001;
    }

    //PROBABLY BOTH METHODS DO THE SAME BUT FOR THE SAKE OF COMPREHENSION THEY ARE GOING TO BE SEPARATED IN TWO METHODS
    public String encrypt(String textToEncrypt){

        //Turn string to corresponding byte array
        byte[] keyByteArray = key.getBytes();
        byte[] byteArrayToEncrypt = textToEncrypt.getBytes();
        byte[] encryptedByteArray = new byte[byteArrayToEncrypt.length];
        for (int i = 0; i < byteArrayToEncrypt.length; i++) {
            encryptedByteArray[i] = (byte) (byteArrayToEncrypt[i] ^ keyByteArray[i% keyByteArray.length]);
        }

        //Turn encrypted byte array to corresponding string

        return new String(encryptedByteArray);
    }

    public String decrypt(String textToDecrypt){

        //Turn string to corresponding byte array
        byte[] keyByteArray = key.getBytes();
        byte[] byteArrayToDecrypt = textToDecrypt.getBytes();
        byte[] decryptedByteArray = new byte[byteArrayToDecrypt.length];
        for (int i = 0; i < byteArrayToDecrypt.length; i++) {
            decryptedByteArray[i] = (byte) (byteArrayToDecrypt[i] ^ keyByteArray[i% keyByteArray.length]);
        }

        //Turn decrypted byte array to corresponding string
        return new String(decryptedByteArray);
    }

}
