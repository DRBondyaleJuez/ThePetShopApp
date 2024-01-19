package application.persistence.assets;

import application.core.ThePetShopAppLauncher;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Provides object of the class in charged of the interaction with the resource folder. In particular interactions with the assets folder. This
 * class implements FileSystemAssetTalker the interface providing the template for the abstract methods that require implementation
 * to fulfill this application's requirements.
 */
public class FileSystemAssetTalker implements  AssetTalker {

    private static Logger logger = LogManager.getLogger(FileSystemAssetTalker.class);

    @Override
    public byte[] getLogoImageData(LogoType logoType) {
        String path = "/assets/images/" + logoType.toString();
        return loadFileDataWithAlternative(path);
    }

    @Override
    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        String path = "/assets/icons/eye/" + eyeIconType.toString();
        return loadFileDataWithAlternative(path);
    }

    @Override
    public byte[] getDecorationImageData() {
        String path = "/assets/images/aiDecoration.png";

        return loadFileDataWithAlternative(path);
    }

    @Override
    public byte[] getUnavailableImage() {
        String path = "/assets/images/imageNotAvailable.png";

        return loadFileData(path);
    }

    private byte[] loadFileDataWithAlternative(String path){
        byte[] pathImageByteArray = loadFileData(path);
        if(Arrays.equals(pathImageByteArray, new byte[0])){
            return getUnavailableImage();
        }
        return pathImageByteArray;
    }

    // Process to collect assets from resource folder
    private byte[] loadFileData(String path) {
        try {
            InputStream currentInputStream = FileSystemAssetTalker.class.getResourceAsStream(path);
            if(currentInputStream == null ){
                throw new IOException();
            }

            return IOUtils.toByteArray(currentInputStream);
        } catch (Exception exception) {
            // ---- LOG ----
            logger.warn("The asset in file path (" + path + ") could not be loaded. ERROR:\n ", exception );
            return new byte[0];
        }
    }
}
