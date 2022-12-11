package utils;

public class EncryptionHandler {

    private final String key;

    public EncryptionHandler() {
        key = ""+1010110011+0110001011+0111111001;
    }

    //PROBABLY BOTH METHODS DO THE SAME BUT FOR THE SAKE OF COMPREHENSION THEY ARE GOING TO BE SEPARATED IN TWO METHODS
    public String encrypt(String textToEncrypt){

        //Turn string to corresponding byte array
        byte[] byteArrayToEncrypt = textToEncrypt.getBytes();
        byte[] keyByteArray = key.getBytes();
        byte[] encryptedByteArray = new byte[byteArrayToEncrypt.length];
        for (int i = 0; i < byteArrayToEncrypt.length; i++) {
            encryptedByteArray[i] = (byte) (byteArrayToEncrypt[i] ^ keyByteArray[i% keyByteArray.length]);
        }

        //Turn encrypted byte array to corresponding string
        String encryptedText= new String(encryptedByteArray);

        return encryptedText;
    }

    public String decrypt(String textToDecrypt){

        //Turn string to corresponding byte array
        byte[] byteArrayToDecrypt = textToDecrypt.getBytes();
        byte[] keyByteArray = key.getBytes();
        byte[] decryptedByteArray = new byte[byteArrayToDecrypt.length];
        for (int i = 0; i < byteArrayToDecrypt.length; i++) {
            decryptedByteArray[i] = (byte) (byteArrayToDecrypt[i] ^ keyByteArray[i% keyByteArray.length]);
        }

        //Turn decrypted byte array to corresponding string
        String decryptedText = new String(decryptedByteArray);
        return decryptedText;
    }

}
