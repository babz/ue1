package ewaMemory.memoryTable.api;

public class FlagInfo {
    private final String imageName;
    private final String countryNameResourceBundleKey;

    public FlagInfo(String imageName, String countryNameResourceBundleKey) {
        this.imageName = imageName;
        this.countryNameResourceBundleKey = countryNameResourceBundleKey;
    }

    public String getImage() {
        return imageName;
    }

    public String getI18nKey() {
        return countryNameResourceBundleKey;
    }
}
