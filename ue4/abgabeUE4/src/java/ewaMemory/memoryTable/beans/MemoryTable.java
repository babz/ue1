package ewaMemory.memoryTable.beans;

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
    private Map<String, Outcome> outcome;
    private final int CARD_WIDTH = 110;
    private final int TABLE_PADDING = 25;

    

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
            String strLoosers = "";
            String strWinners = "";

            //TODO this is the only part in this class which makes us of the game having only two players

            outcome = new HashMap<String,Outcome>();

            if(points.get(users[0]) > points.get(users[1])) {
                outcome.put(users[0], Outcome.WIN);
                outcome.put(users[1], Outcome.LOOSE);
            } else if(points.get(users[0]) == points.get(users[1])) {
                outcome.put(users[0], Outcome.DRAW);
                outcome.put(users[1], Outcome.DRAW);
            } else {
                outcome.put(users[0], Outcome.LOOSE);
                outcome.put(users[1], Outcome.WIN);
            }

            log.info("Game over - outcome: "+users[0]+":"+outcome.get(users[0])+"; "+users[1]+":"+outcome.get(users[1]));
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

    public int getPlayTimeInSeconds(String username) {
        return usersPlayTimeInSeconds.get(username).intValue();
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

    public Outcome getOutcome(String username) {
        return outcome.get(username);
    }

    public int getHighscore(String username) {
         // x = 300 - (sec / pairs)
        if(getPoints(username) < 1) {
            return 0;
        }
        return 300 - (getPlayTimeInSeconds(username) / getPoints(username));
    }

    public int getDisplayWidth() {
        return CARD_WIDTH * cards.size() + 2 * TABLE_PADDING;
    }
}
