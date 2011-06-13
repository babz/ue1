package ewaMemory.memoryTable.api;

public class FlagInfo {
    private final String imageName;
    private final String countryName;

    public FlagInfo(String imageName, String countryNameResourceBundleKey) {
        this.imageName = imageName;
        this.countryName = countryNameResourceBundleKey;
    }

    public String getImage() {
        return imageName;
    }

    public String getCountryName() {
        return countryName;
    }
}
