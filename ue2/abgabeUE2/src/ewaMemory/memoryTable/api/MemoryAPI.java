package ewaMemory.memoryTable.api;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ewaMemory.memoryTable.beans.MemoryCard;
import ewaMemory.memoryTable.beans.MemoryTable;

public class MemoryAPI {
	private static Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());
	//private Date time;
	
	public MemoryTable createMemoryTable(int memoryWidth, int memoryHeight) {
		log.info("creating new MemoryTable");
		MemoryTable memory = new MemoryTable();
		
		log.info("time starts");
		memory.setStartTime(new Date().getTime());
		
		//TODO fill memory according to width & height
		
		
		List<MemoryCard> row1cards = new LinkedList<MemoryCard>();
		List<MemoryCard> row2cards = new LinkedList<MemoryCard>();
		
		row1cards.add(new MemoryCard("img/cards/at.jpg"));
		row1cards.add(new MemoryCard("img/cards/cz.jpg"));
		row1cards.add(new MemoryCard("img/cards/de.jpg"));
		
		row2cards.add(new MemoryCard("img/cards/at.jpg"));
		row2cards.add(new MemoryCard("img/cards/de.jpg"));
		row2cards.add(new MemoryCard("img/cards/cz.jpg"));
		
		
		List<List<MemoryCard>> cards = new LinkedList<List<MemoryCard>>();
		Collections.shuffle(row1cards);
		cards.add(row1cards);
		Collections.shuffle(row2cards);
		cards.add(row2cards);
		
		memory.setCards(cards);
		
		
		return memory;
	}

	public void clickOnCard(MemoryTable memory, int click_x, int click_y) {
		log.info("clickOnCard at (x, y): (" + click_x + ", " + click_y + ")");
		MemoryCard card = memory.getRows().get(click_x).get(click_y);
		memory.setEndTime(new Date().getTime());
		
		memory.unrevealCardsToUnreveal();
		
		if(card.isVisible()) {
			return;
		}
		
		log.info("Card is not visible!");
		card.setVisible(true);
		MemoryCard predecessor = memory.getLastRevealedCard();
		
		if(predecessor == null) {
			card.setVisible(true);
			memory.setLastRevealedCard(card);
			return;
		} 

		memory.setLastRevealedCard(null);

		if(predecessor.getImagePath().equals(card.getImagePath())) {
			memory.incrementPoints();
			memory.incrementAttempts();
			memory.decrementRemainingPairs();
		} else {
			memory.addToCardsToUnreveal(predecessor);
			memory.addToCardsToUnreveal(card);
			memory.incrementAttempts();
		}
	}
}
