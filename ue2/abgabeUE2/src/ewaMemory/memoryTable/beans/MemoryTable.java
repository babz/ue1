package ewaMemory.memoryTable.beans;

import java.util.LinkedList;
import java.util.List;

public class MemoryTable {
	
	private List<List<MemoryCard>> cards;
	private MemoryCard lastRevealedCard;
	
	private int points;
	private List<MemoryCard> cardsToUnreveal = new LinkedList<MemoryCard>();
	

	public List<List<MemoryCard>> getRows() {
		return cards;
	}

	public void setCards(List<List<MemoryCard>> memoryCards) {
		cards = memoryCards;
	}

	public MemoryCard getLastRevealedCard() {
		return lastRevealedCard;
	}

	public void setLastRevealedCard(MemoryCard card) {
		lastRevealedCard = card;
	}

	public void incrementPoints() {
		points++;
	}
	
	public int getPoints() {
		return points;
	}

	public void addToCardsToUnreveal(MemoryCard card) {
		cardsToUnreveal.add(card);
	}
	
	public void unrevealCardsToUnreveal() {
		for(MemoryCard c : cardsToUnreveal) {
			c.setVisible(false);
		}
		cardsToUnreveal.clear();
	}
}
