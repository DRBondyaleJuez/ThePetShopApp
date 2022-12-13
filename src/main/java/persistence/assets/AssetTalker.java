package persistence.assets;

import persistence.assets.EyeIconType;
import persistence.assets.LogoType;

public interface AssetTalker {

    byte[] getLogoImageData(LogoType logoType);

    byte[] getEyeIconImageData(EyeIconType eyeIconType);

    byte[] getDecorationImageData();

}
