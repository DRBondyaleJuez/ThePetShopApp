package application.persistence.assets;

public enum EyeIconType {
    OPEN("openEye.PNG"),CLOSED("closeEye.PNG");

    private final String pathDetail;

   EyeIconType(final String pathDetail){this.pathDetail = pathDetail;}

    @Override
    public String toString() {
        return pathDetail;
    }
}
