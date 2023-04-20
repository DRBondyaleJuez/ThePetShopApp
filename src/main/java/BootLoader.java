import application.utils.PropertiesReader;

public class BootLoader {

    public static void bootApplicationProperties(){
        PropertiesReader.loadAllProperties();
    }

}
