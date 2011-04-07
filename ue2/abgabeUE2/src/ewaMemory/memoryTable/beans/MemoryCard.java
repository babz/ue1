package ewaMemory.memoryTable.beans;

public class MemoryCard {
	private final String imagePath;
	
	public MemoryCard(String imagePath) {
		this.imagePath = imagePath;
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
		return "&Ouml;sterreich";
	}

}
