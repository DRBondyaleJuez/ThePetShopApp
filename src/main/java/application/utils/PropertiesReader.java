package application.utils;

import java.io.*;
import java.util.HashMap;

/**
 * Provides of an intermediary to interpret and use the secret.properties file. The needed properties are stored in a map accessible through getters.
 */
public class PropertiesReader {

    private static final String  urlSource = "/secrets.properties2";
    private static final HashMap<String,String> propertiesMap = new HashMap<>();

    /**
     * Static method to collect and store in the map attribute all the relevant parameter from the  secrets.properties file
     */
    public static void loadAllProperties(){
        loadDBUser();
        loadDBPassword();
        loadEncryptionKey();
        loadSaltSize();
        loadInitialSubstringPositionForTransposition();
    }

    //GETTERS FROM PROPERTY MAP
    public static String getDBPassword(){
        return propertiesMap.get("dbPassword");
    }
    public static String getDBUser(){
        return propertiesMap.get("dbUser");
    }
    public static String getEncryptionKey(){
        return propertiesMap.get("encryptionKey");
    }
    public static int getSaltSize(){
        return Integer.parseInt(propertiesMap.get("saltSize"));
    }
    public static int getInitialSubstringPositionForTransposition(){
        return Integer.parseInt(propertiesMap.get("initialSubstringPositionForTransposition"));
    }

    //Individual LOADERS

    private static void loadDBPassword(){

        InputStream secretsStream = PropertiesReader.class.getResourceAsStream(urlSource);
        if(secretsStream == null) return;

        try {
            BufferedReader secretsReader = new BufferedReader(new InputStreamReader(secretsStream));
            String currentString;

            while ((currentString = secretsReader.readLine()) != null) {

                if(currentString.contains("DBPassword")){
                    String secretDBPassword = currentString.replace("DBPassword=","");
                    propertiesMap.put("dbPassword",secretDBPassword);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDBUser() {

        InputStream secretsStream = PropertiesReader.class.getResourceAsStream(urlSource);
        if(secretsStream == null) return;

        try {
            BufferedReader secretsReader = new BufferedReader(new InputStreamReader(secretsStream));
            String currentString;

            while ((currentString = secretsReader.readLine()) != null) {

                if(currentString.contains("DBUser")){
                    String secretDBUser = currentString.replace("DBUser=","");
                    propertiesMap.put("dbUser",secretDBUser);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadEncryptionKey() {

        InputStream secretsStream = PropertiesReader.class.getResourceAsStream(urlSource);
        if(secretsStream == null) return;

        try {
            BufferedReader secretsReader = new BufferedReader(new InputStreamReader(secretsStream));
            String currentString;

            while ((currentString = secretsReader.readLine()) != null) {

                if(currentString.contains("encryptionKey")){
                    String secretEncryptionKey = currentString.replace("encryptionKey=","");
                    propertiesMap.put("encryptionKey",secretEncryptionKey);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void loadSaltSize() {

        InputStream secretsStream = PropertiesReader.class.getResourceAsStream(urlSource);
        if(secretsStream == null) return;

        try {
            BufferedReader secretsReader = new BufferedReader(new InputStreamReader(secretsStream));
            String currentString;

            while ((currentString = secretsReader.readLine()) != null) {

                if(currentString.contains("saltSize")){
                    String secretSaltSize = currentString.replace("saltSize=","");
                    propertiesMap.put("saltSize",secretSaltSize);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadInitialSubstringPositionForTransposition() {

        InputStream secretsStream = PropertiesReader.class.getResourceAsStream(urlSource);
        if(secretsStream == null) return;

        try {
            BufferedReader secretsReader = new BufferedReader(new InputStreamReader(secretsStream));
            String currentString;

            while ((currentString = secretsReader.readLine()) != null) {

                if(currentString.contains("initialSubstringPositionForTransposition")){
                    String secretInitialSubstringPositionForTransposition = currentString.replace("initialSubstringPositionForTransposition=","");
                    propertiesMap.put("initialSubstringPositionForTransposition",secretInitialSubstringPositionForTransposition);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
