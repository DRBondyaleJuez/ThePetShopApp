package application.persistence.assets;

/**
 * Provides the abstract methods necessary for a proper use by calls from the AssetManager Class basically for performing
 * the appropriate operations in the resources/assets folder. The class which implements this interface will need to implement the methods.
 */
public interface AssetTalker {

    /**
     * Get the particular logo image from the file system at resources
     * @param logoType LogoType enum informing of the logo format to retrieve from the assets folder
     * @return byte array corresponding to the requested logo image
     */
    byte[] getLogoImageData(LogoType logoType);

    /**
     * Get the particular ey icon from the file system at resources
     * @param eyeIconType EyeIconType enum informing of the type of icon (open or closed) to retrieve from the assets folder
     * @return byte array corresponding to the requested eye icon image
     */
    byte[] getEyeIconImageData(EyeIconType eyeIconType);

    /**
     * Get the decoration image from the file system at resources
     * @return byte array corresponding to the requested decoration image
     */
    byte[] getDecorationImageData();

    /**
     * Get the image from resources used when the system is unable to retrieve an online image
     * @return byte array corresponding to the requested unavailable image place holder
     */
    byte[] getUnavailableImage();
}
