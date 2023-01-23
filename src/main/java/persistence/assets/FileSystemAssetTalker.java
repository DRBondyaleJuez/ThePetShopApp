package persistence.assets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private byte[] loadFileData(String path) {
        try {
            URI fileUri = getClass().getResource(path).toURI();
            Path completePath = Paths.get(fileUri);
            byte[] fileContent = Files.readAllBytes(completePath);
            return fileContent;
        } catch (URISyntaxException e) {
            // TODO log
            System.out.println("Unable to get resource URI");
            e.printStackTrace();
            return new byte[0];
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
