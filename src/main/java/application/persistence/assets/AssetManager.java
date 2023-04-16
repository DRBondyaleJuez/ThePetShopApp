package application.persistence.assets;

public class AssetManager {

    private final AssetTalker assetTalker;
    private static AssetManager instance;

    private AssetManager(){
        assetTalker = new FileSystemAssetTalker();
        instance = null;
    }

    public static AssetManager getInstance() {
        if(instance == null) {
            instance = new AssetManager();
        }

        return instance;
    }

    public byte[] getLogoImageData(LogoType logoType){
        return assetTalker.getLogoImageData(logoType);
    }

    public byte[] getEyeIconImageData(EyeIconType eyeIconType){
        return assetTalker.getEyeIconImageData(eyeIconType);
    }

    public byte[] getDecorationImageData(){
        return assetTalker.getDecorationImageData();
    }

}
