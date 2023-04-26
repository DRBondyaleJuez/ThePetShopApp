package application.persistence.assets;

/**
 * Provides objects of a class connecting the controller with the class in charged of interacting with the file system. It follows
 * a singleton architecture so there is only one possible instance of this class.
 */
public class AssetManager {

    private final AssetTalker assetTalker;
    private static AssetManager instance;

    //This is the private constructor where the FileSystemAssetTalker implementation is initialized for a particular table
    private AssetManager(){
        assetTalker = new FileSystemAssetTalker();
        instance = null;
    }

    /**
     * Following the singleton architecture this is the method to use an instance of this class which will always be the instance
     * assigned to the instance attribute except the first time when it is instantiated.
     * @return AssetManager object assigned to the attribute instance
     */
    public static AssetManager getInstance() {
        if(instance == null) {
            instance = new AssetManager();
        }

        return instance;
    }

    //GETTERS
    /**
     * Get the particular logo image from the file system at resources
     * @param logoType LogoType enum informing of the logo format to retrieve from the assets folder
     * @return byte array corresponding to the requested logo image
     */
    public byte[] getLogoImageData(LogoType logoType){
        return assetTalker.getLogoImageData(logoType);
    }

    /**
     * Get the particular ey icon from the file system at resources
     * @param eyeIconType EyeIconType enum informing of the type of icon (open or closed) to retrieve from the assets folder
     * @return byte array corresponding to the requested eye icon image
     */
    public byte[] getEyeIconImageData(EyeIconType eyeIconType){
        return assetTalker.getEyeIconImageData(eyeIconType);
    }

    /**
     * Get the decoration image from the file system at resources
     * @return byte array corresponding to the requested decoration image
     */
    public byte[] getDecorationImageData(){
        return assetTalker.getDecorationImageData();
    }

    /**
     * Get the image from resources used when the system is unable to retrieve an online image
     * @return byte array corresponding to the requested unavailable image place holder
     */
    public byte[] getUnavailableImage() { return assetTalker.getUnavailableImage(); }
}
