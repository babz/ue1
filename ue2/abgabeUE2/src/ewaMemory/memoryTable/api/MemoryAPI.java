package ewaMemory.memoryTable.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ewaMemory.memoryTable.beans.MemoryCard;
import ewaMemory.memoryTable.beans.MemoryTable;

public class MemoryAPI {
	private static Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());
	private static String CARD_DIR_REL_TO_WEB_CONTENT = "img/cards";
	private List<String> allFlags;
	
	public MemoryAPI(List<String> cardDecks) {
		allFlags = cardDecks;
	}
	
	public MemoryTable createMemoryTable(int memoryWidth, int memoryHeight) {
		if(memoryHeight % 2 != 0 && memoryWidth % 2 != 0){
			throw new IllegalArgumentException("both params odd!");
		}
		log.info("creating new MemoryTable");
		MemoryTable memory = new MemoryTable();
		
		log.info("time starts");
		memory.setStartTime(new Date().getTime());
		
		List<String> flags = new ArrayList<String>();

		Collections.shuffle(allFlags);
		
		int neededFlags = memoryHeight * memoryWidth / 2;
		for(int i = 0; i < neededFlags; i++) {
			flags.add(allFlags.get(i % allFlags.size())); // get flags and reuse, if not enough unique flags exist
			flags.add(allFlags.get(i % allFlags.size()));
		}
		
		Collections.shuffle(flags);
		Iterator<String> flagsIterator = flags.iterator();
		
		List<List<MemoryCard>> cards = new LinkedList<List<MemoryCard>>();
		
		for(int i = 0; i < memoryHeight; i++) {
		
			List<MemoryCard> cardRow = new LinkedList<MemoryCard>();
			
			for(int j = 0; j < memoryWidth; j++) {
				cardRow.add(new MemoryCard(CARD_DIR_REL_TO_WEB_CONTENT + File.separatorChar + flagsIterator.next()));
			}
			cards.add(cardRow);
		}
		
		memory.setCards(cards);
		
		return memory;
	}

	public void clickOnCard(MemoryTable memory, int click_x, int click_y) {
		log.info("clickOnCard at (x, y): (" + click_x + ", " + click_y + ")");
		MemoryCard card = memory.getRows().get(click_x).get(click_y);
		
		log.info("timer refresh");
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
