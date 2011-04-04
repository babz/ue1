package ewaMemory.memoryTable.beans;

import java.util.List;

public class MemoryTable {
	
	private List<List<MemoryCard>> cards;

	public List<List<MemoryCard>> getRows() {
		return cards;
	}

	public void setCards(List<List<MemoryCard>> memoryCards) {
		cards = memoryCards;
	}
}
