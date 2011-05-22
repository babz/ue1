package ewaMemory.memoryTable.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MemoryTable {
    private static final Logger log = Logger.getLogger(MemoryTable.class.getSimpleName());
    private final static int MAXUSERS = 2;

    private List<List<MemoryCard>> cards;
    private MemoryCard lastRevealedCard;
    private int remainingPairs;
    private List<MemoryCard> cardsToUnreveal = new LinkedList<MemoryCard>();

    private String[] users = new String[MAXUSERS];
    private Map<String, Integer> points = new HashMap<String, Integer>();
    private Map<String, Integer> attempts = new HashMap<String, Integer>();
    private long timeStart = (long) 0;
    private Map<String, Long> usersPlayTimeInSeconds = new HashMap<String, Long>();;

    private int turnId = 0;
    
    private boolean gameHasStarted = false;
    private boolean gameOver;
    private String winner;
    private List<String> loosers = new ArrayList<String>();

    

    public MemoryTable(List<String> usernames) {
        int id = 0;
        for (String username : usernames) {
            points.put(username, 0);
            attempts.put(username, 0);
            usersPlayTimeInSeconds.put(username, 0L);
            users[id++] = username;
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

    public void setEndTime(long endTime) {
        log.fine("nextTurn");
        long playTime = (endTime - timeStart) / 1000;
        log.fine("playTime: " + playTime);
        long oldPlayTime = usersPlayTimeInSeconds.get(users[turnId]);
        log.fine("oldPlayTime: " + oldPlayTime);
        long newPlayTime = oldPlayTime + playTime;
        log.fine("newPlayTime: " + newPlayTime);
        usersPlayTimeInSeconds.put(users[turnId], newPlayTime);
        timeStart = endTime;
    }

    public void setLastRevealedCard(MemoryCard card) {
        lastRevealedCard = card;
    }

    public void incrementPoints(String username) {
        points.put(username, points.get(username) + 1);
    }

    public Integer getPoints(String username) {
        return points.get(username);
    }

    public void incrementAttempts(String username) {
        attempts.put(username, attempts.get(username) + 1);
    }

    public Integer getAttempts(String username) {
        return attempts.get(username);
    }

    public void decrementRemainingPairs() {
        remainingPairs--;
        if (remainingPairs == 0) {
            gameOver = true;
            String loosers = "";
            
            int heighestPoints = 0;
            for (String user : users) {
                if (points.get(user) > heighestPoints) {
                    winner = user;
                } else {
                    //TODO bug here
                    this.loosers.add(user);
                    loosers += user+",";
                }
            }
            log.info("Game over: winner :"+winner+", loosers:"+loosers);
        }
    }

    public int getRemainingPairs() {
        //number of all pairs; set only once
        if (sum(points.values()) == 0) {
            if (cards.isEmpty()) {
                remainingPairs = 0;
            } else {
                remainingPairs = cards.size() * cards.get(0).size() / 2;
            }
        }
        return remainingPairs;
    }

    public void addToCardsToUnreveal(MemoryCard card) {
        cardsToUnreveal.add(card);
    }

    public void unrevealCardsToUnreveal() {
        for (MemoryCard c : cardsToUnreveal) {
            c.setVisible(false);
        }
        cardsToUnreveal.clear();
    }

    public String getPlayTime(String username) {
        long playTimeInSeconds = usersPlayTimeInSeconds.get(username);
        if (playTimeInSeconds == 0L) {
            return "0:00";
        }
    
        long minutes = (long) ((int) playTimeInSeconds / 60);
        long seconds = playTimeInSeconds % 60;
        if (seconds < 10) {
            return minutes + ":0" + seconds;
        }
        return minutes + ":" + seconds;
    }

    private int sum(Collection<Integer> values) {
        int sum = 0;
        for (Integer n : values) {
            sum += n;
        }
        return sum;
    }

    public boolean isUserTurn(String username) {
        return users[turnId].equals(username);
    }

    public String[] getUsernames() {
        return users;
    }

    public void nextTurn() {
        turnId = (turnId + 1) % MAXUSERS;
    }

    public String getUsernameTurn() {
        return users[turnId];
    }

    public void startGame() {
        gameHasStarted = true;
        timeStart = new Date().getTime();
    }

    /**
     * @return the gameHasStarted
     */
    public boolean isGameHasStarted() {
        return gameHasStarted;
    }

    /**
     * @return the gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * @return the loosers
     */
    public List<String> getLoosers() {
        return new ArrayList<String>(loosers);
    }
}
