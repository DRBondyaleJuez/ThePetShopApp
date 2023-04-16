package application.persistence.assets;

public interface AssetTalker {

    byte[] getLogoImageData(LogoType logoType);

    byte[] getEyeIconImageData(EyeIconType eyeIconType);

    byte[] getDecorationImageData();

}
