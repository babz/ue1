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
		String country = imagePath.substring(10, 12);
		if(country.equals("at")){
			return "&Ouml;sterreich";
		}
		if(country.equals("cz")){
			return "Tschechien";
		}
		if(country.equals("de")){
			return "Deutschland";
		}
		return "unbekanntes Land";
	}

}
