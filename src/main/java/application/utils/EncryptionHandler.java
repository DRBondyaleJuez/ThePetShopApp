package application.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Provides a series of tools to encrypt and decrypt Strings following a series of particular encryption parameters
 */
public class EncryptionHandler {

    private final String key;
    private final int saltSize;
    private final int transpositionValue;

    /**
     * This is the constructor where the encryption parameters are established. In this case these are retrieved from the
     * secrets.properties file in resources employing a PropertiesReader class for this purpose.
     */
    public EncryptionHandler() {
        key = PropertiesReader.getEncryptionKey();
        saltSize = PropertiesReader.getSaltSize();
        int initialSubstringPosition = PropertiesReader.getInitialSubstringPositionForTransposition();
        transpositionValue = calculateTranspositionValue((key.substring(initialSubstringPosition,initialSubstringPosition+6)));
    }

    //Methods to calculate transposition value from key
    private int calculateTranspositionValue(String keySubstring){
        byte[] key1ByteArray = keySubstring.getBytes();
        StringBuilder keyString = new StringBuilder();
        for (byte keybit: key1ByteArray) {
            keyString.append(keybit);
        }

        long newNumber = Long.parseLong(keyString.toString());
        return internalSumNumber(newNumber);
    }
    private static int internalSumNumber(long number) {
        long sum = 0;
        if(number == 0 || number == 1) { number += 22;}

        while(number > 10) {
            sum = 0;
            while (number > 0) {
                sum = sum + number % 10;
                number = number / 10;
            }
            number = sum;
        }

        if(sum == 1) sum = 11;

        return (int) sum;
    }

    //PROBABLY BOTH METHODS DO THE SAME BUT FOR THE SAKE OF COMPREHENSION THEY ARE GOING TO BE SEPARATED IN TWO METHODS

    public byte[] encrypt(String textToEncrypt){

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

        return encryptedByteArray;
    }

    private byte[] transposition(byte[] byteArrayToTranspose){

        int lengthOfTranspositionList = byteArrayToTranspose.length/transpositionValue;
       // int remainderOfTranspositionListLength = byteArrayToTranspose.length % transpositionValue; ------------------------------------------------

        ArrayList<Byte>[] transpositionListMatrix = new ArrayList[transpositionValue+1];
        for (int i = 0; i < transpositionListMatrix.length; i++) {
            transpositionListMatrix[i] = new ArrayList<>();
        }
        ArrayList<Byte> finalTransposedByteList = new ArrayList<>();
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

        byte[] transposedByteArray = new byte[byteArrayToTranspose.length];
        for (int i = 0; i < byteArrayToTranspose.length; i++) {
            transposedByteArray[i] = finalTransposedByteList.get(i);
        }

        return transposedByteArray;
    }

    public String decrypt(byte[] byteArrayToDecrypt ){

        //Turn string to corresponding byte array
        byte[] keyByteArray = key.getBytes();
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
