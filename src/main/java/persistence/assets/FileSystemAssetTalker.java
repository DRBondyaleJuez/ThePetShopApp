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
        String path = "/assets/images/";
        switch(logoType){
            case COLOR:
                path = path + "colouredLogo.PNG";
                break;
            case BLACK:
                path = path + "blackLogo.PNG";
                break;
            case HORIZONTAL:
                path = path + "horizontalLogo.PNG";
                break;
            default:
                //TODO: log
                System.out.println("Somehow not available logo type: " + logoType);
                break;
        }

        return loadFileData(path);
    }

    @Override
    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        String path = "/assets/icons/eye/";
        switch(eyeIconType){
            case OPEN:
                path = path + "openEye.PNG";
                break;
            case CLOSED:
                path = path + "closeEye.PNG";
                break;
            default:
                //TODO: log
                System.out.println("Somehow not available eye icon type: " + eyeIconType);
                break;
        }
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
