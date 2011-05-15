package ewaMemory.memoryTable.beans;

public class MemoryCard {
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
		return imagePath;
	}
	
	public String getAltText() {
		return countryName;
        }

}
