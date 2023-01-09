package utils;

import java.util.ArrayList;
import java.util.Random;

public class EncryptionHandler {

    private final String key;
    private final int saltSize;
    private final int transpositionValue;

    public EncryptionHandler() {
        key = ""+1010110011+0110001011+0111111001;
        saltSize = 500;
        transpositionValue = Integer.parseInt(key.substring(0,3));
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

        int lengthOfTranspositionList = byteArrayToTranspose.length/transpositionValue;
       // int remainderOfTranspositionListLength = byteArrayToTranspose.length % transpositionValue; ------------------------------------------------

        ArrayList<Byte>[] transpositionListMatrix = new ArrayList[transpositionValue+1];
        for (int i = 0; i < transpositionListMatrix.length; i++) {
            transpositionListMatrix[i] = new ArrayList<>();
        }
        ArrayList<Byte> finalTransposedByteList = new ArrayList<>();
        byte[] transposedByteArray = new byte[byteArrayToTranspose.length];
        for (int i = 0; i < byteArrayToTranspose.length; i++) {
            if(i >= lengthOfTranspositionList * transpositionValue){
                transpositionListMatrix[transpositionValue].add(byteArrayToTranspose[i]);
            } else {
                transpositionListMatrix[i % transpositionValue].add(byteArrayToTranspose[i]);
            }
        }

        for (ArrayList<Byte> listMatrix : transpositionListMatrix) {
            finalTransposedByteList.addAll(listMatrix);
        }

        for (int i = 0; i < byteArrayToTranspose.length; i++) {
            transposedByteArray[i] = finalTransposedByteList.get(i);
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

        int lengthOfTranspositionList = byteArrayToDetranspose.length/transpositionValue;

        ArrayList<Byte> detransposedByteList = new ArrayList<>();

        byte[] detransposedByteArray = new byte[byteArrayToDetranspose.length];

        for (int i = 0; i < lengthOfTranspositionList; i++) {
                for (int j = 0; j < transpositionValue; j++) {
                    detransposedByteList.add(byteArrayToDetranspose[(lengthOfTranspositionList*j) + i]);
                }
        }
        for (int i = lengthOfTranspositionList * transpositionValue; i < byteArrayToDetranspose.length; i++) {
            detransposedByteList.add(byteArrayToDetranspose[i]);
        }

        for (int i = 0; i < byteArrayToDetranspose.length; i++) {
            detransposedByteArray[i] = detransposedByteList.get(i);
        }

        return detransposedByteArray;
        
    }

    private String getSaltString() {
        String saltChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!$&@_-()[].,*";
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
