package ewaMemory.memoryTable.beans;

public class MemoryCard {

    private static final String CARD_BACKGROUND_PATH = "img/card_background.png";
    private final String imagePath;
    private final String countryName;

    public MemoryCard(String imagePath, String countryName) {
        this.imagePath = imagePath;
        this.countryName = countryName;
    }
    private boolean visible = false;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean value) {
        visible = value;
    }

    public String getImagePath() {
        if (visible) {
            return imagePath;
        }
        return CARD_BACKGROUND_PATH;
    }

    public String getAltTextI18nKey() {
        if (visible) {
            return countryName;
        }
        return "i18nUNKNOWN_COUNTRY";
    }
}
