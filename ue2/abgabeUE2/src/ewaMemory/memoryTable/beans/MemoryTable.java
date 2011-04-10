package ewaMemory.memoryTable.beans;

import java.util.LinkedList;
import java.util.List;

public class MemoryTable {

	private List<List<MemoryCard>> cards;
	private MemoryCard lastRevealedCard;

	private int points;
	private int attempts;
	private int remainingPairs;
	private List<MemoryCard> cardsToUnreveal = new LinkedList<MemoryCard>();
	private long timeStart = (long) 0;
	private long timeEnd = (long) 0;

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

	public void incrementAttempts() {
		attempts++;
	}

	public int getAttempts() {
		return attempts;
	}

	public void decrementRemainingPairs() {
		remainingPairs--;
	}

	public int getRemainingPairs() {
		//number of all pairs; set only once
		if(getPoints() == 0) {
			remainingPairs = cards.size() * cards.get(0).size() / 2;
		}
		return remainingPairs;
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

	public void setStartTime(long startTime) {
		timeStart = startTime;
	}

	public void setEndTime(long endTime) {
		timeEnd = endTime;
	}

	public String getPlayTime(){
		if(timeEnd == (long) 0){
			return "0:00";
		}
		long playTimeInSeconds = (timeEnd - timeStart) / 1000;
		long minutes = (long) ((int)playTimeInSeconds / 60);
		long seconds = playTimeInSeconds % 60;
		if(seconds < 10) {
			return minutes + ":0" + seconds;
		}
		return minutes + ":" + seconds;
	}
}
