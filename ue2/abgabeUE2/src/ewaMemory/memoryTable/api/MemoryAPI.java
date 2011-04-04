package ewaMemory.memoryTable.api;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ewaMemory.memoryTable.beans.MemoryCard;
import ewaMemory.memoryTable.beans.MemoryTable;

public class MemoryAPI {
	private static Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());
	
	
	public MemoryTable createMemoryTable(int memoryWidth, int memoryHeight) {
		log.info("creating new MemoryTable");
		MemoryTable memory = new MemoryTable();
		
		//TODO fill memory according to width & height
		
		
		List<MemoryCard> row1cards = new LinkedList<MemoryCard>();
		List<MemoryCard> row2cards = new LinkedList<MemoryCard>();
		
		row1cards.add(new MemoryCard());
		row1cards.add(new MemoryCard());
		row1cards.add(new MemoryCard());
		
		row2cards.add(new MemoryCard());
		row2cards.add(new MemoryCard());
		row2cards.add(new MemoryCard());
		
		
		List<List<MemoryCard>> cards = new LinkedList<List<MemoryCard>>();
		cards.add(row1cards);
		cards.add(row2cards);
		
		memory.setCards(cards);
		
		
		return memory;
	}

	public void clickOnCard(MemoryTable memory, int click_x, int click_y) {
		log.info("clickOnCard at (x, y): (" + click_x + ", " + click_y + ")");
		
		// TODO Auto-generated method stub
		
	}

}
