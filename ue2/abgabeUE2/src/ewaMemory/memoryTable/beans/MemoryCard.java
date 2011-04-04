package ewaMemory.memoryTable.beans;

public class MemoryCard {

	
	private boolean visible = false;

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean value) {
		visible = value;
	}
	
	public String getImagePath() {
		return "img/cards/at.jpg";
	}
	
	public String getAltText() {
		return "&Ouml;sterreich";
	}

}
