package application.persistence.assets;

import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;

/**
 * Provides object of the class in charged of the interaction with the resource folder. In particular interactions with the assets folder. This
 * class implements FileSystemAssetTalker the interface providing the template for the abstract methods that require implementation
 * to fulfill this application's requirements.
 */
public class FileSystemAssetTalker implements  AssetTalker {


    @Override
    public byte[] getLogoImageData(LogoType logoType) {
        String path = "/assets/images/" + logoType.toString();
        return loadFileData(path);
    }

    @Override
    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        String path = "/assets/icons/eye/" + eyeIconType.toString();
        return loadFileData(path);
    }

    @Override
    public byte[] getDecorationImageData() {
        String path = "/assets/images/aiDecoration.png";

        return loadFileData(path);
    }

    // Process to collect assets from resource folder
    private byte[] loadFileData(String path) {
        try {
            InputStream currentInputStream = FileSystemAssetTalker.class.getResourceAsStream(path);
            byte[] fileContent = IOUtils.toByteArray(currentInputStream);

            return fileContent;
        } catch (IOException e) {
            // TODO: log
            System.out.println("Could not find " + path);
            e.printStackTrace();
            return new byte[0];
        }catch (NullPointerException e) {
            // TODO: log
            System.out.println("Could not find " + path);
            e.printStackTrace();
            return new byte[0];
        }
    }
}
