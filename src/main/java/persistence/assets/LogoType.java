package persistence.assets;

public enum LogoType {
    COLOR("colouredLogo.PNG"),BLACK("blackLogo.PNG"),HORIZONTAL("horizontalLogo.PNG"),H_TRANSPARENT("horizontalLogoTransparent.PNG");//,WHITE,MINI


    private final String pathDetail;

   LogoType(final String pathDetail){this.pathDetail = pathDetail;}

    @Override
    public String toString() {
        return pathDetail;
    }
}
