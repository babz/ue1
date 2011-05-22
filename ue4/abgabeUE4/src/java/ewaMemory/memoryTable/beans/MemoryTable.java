package ewaMemory.memoryTable.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemoryTable {

	private List<List<MemoryCard>> cards;
	private MemoryCard lastRevealedCard;

        private final static int MAXUSERS = 2;

	private int[] points = new int[MAXUSERS];
        private int[] attempts = new int[MAXUSERS];
	private int remainingPairs;
	private List<MemoryCard> cardsToUnreveal = new LinkedList<MemoryCard>();
	private long timeStart = (long) 0;
	private long timeEnd = (long) 0;

        private Map<String,Integer> usernameToId = new HashMap<String, Integer>();
        private Map<Integer, String> idToUsername = new HashMap<Integer, String>();

        private int turnId = 0;

    public MemoryTable(List<String> usernames) {
        for(int id=0; id<usernames.size(); id++) {
            usernameToId.put(usernames.get(id),id);
            idToUsername.put(id, usernames.get(id));
        }
    }

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

	public void incrementPoints(String username) {
            points[map(username)]++;
	}

	public int getPoints(String username) {
		return points[map(username)];
	}

	public void incrementAttempts(String username) {
		attempts[map(username)]++;
	}

	public int getAttempts(String username) {
		return attempts[map(username)];
	}

	public void decrementRemainingPairs() {
		remainingPairs--;
	}

	public int getRemainingPairs() {
		//number of all pairs; set only once
		if(sum(points) == 0) {
                    if(cards.isEmpty())
                        remainingPairs = 0;
                    else
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

    private int sum(int[] values) {
        int sum = 0;
        for(int i = 0; i<values.length; i++) {
            sum+=values[i];
        }
        return sum;
    }

    private int map(String username) {
        return usernameToId.get(username);
    }

    private String unmap(Integer id) {
        return idToUsername.get(id);
    }

    public boolean isUserTurn(String username) {
        return unmap(turnId).equals(username);
    }

    public String getOpponentUsername(String username) {
        for(String name : usernameToId.keySet()) {
            if(!name.equals(username))
                return name;
        }
        return null;
    }

    public void nextTurn() {
        turnId = (turnId+1) % 2;
    }
}
