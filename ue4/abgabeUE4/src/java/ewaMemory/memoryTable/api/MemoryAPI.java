package ewaMemory.memoryTable.api;

import FacebookConnector.Highscore;
import FacebookConnector.Score;
import ewaMemory.flagService.Flag;
import ewaMemory.flagService.FlagRequest;
import ewaMemory.flagService.FlagResponse;
import ewaMemory.flagService.FlagService;
import ewaMemory.flagService.FlagServiceException;
import ewaMemory.flagService.FlagServiceImplService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ewaMemory.memoryTable.beans.MemoryCard;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.Outcome;
import ewaMemory.memoryTable.beans.User;
import ewaMemory.memoryTable.controller.MemoryCtrl;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true, name = "api")
@ApplicationScoped
public class MemoryAPI {

    private static final Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());

    private Map<String, User> registeredUsers = new HashMap<String, User>();
//    private Map<String, User> onlineUsers = new HashMap<String, User>();
    private List<Game> waitingGames = new ArrayList<Game>();
    private FlagService flagService;

    public MemoryAPI() {
        FlagServiceImplService serviceImpl = new FlagServiceImplService();
        flagService = serviceImpl.getFlagServiceImplPort();

        createAndAddUser("Franz", "12345abc");
        createAndAddUser("Helga", "12345abc");
        createAndAddUser("Hubsi", "12345abc");
        createAndAddUser("Ylgal", "12345abc");
        log.info("MemoryAPI created!");
    }

    public void registerUser(User user) throws UsernameAlreadyRegisteredException {
        if (registeredUsers.containsKey(user.getUsername())) {
            throw new UsernameAlreadyRegisteredException();
        }

        user = new User(user);

        registeredUsers.put(user.getUsername(), user);
    }

    public boolean userExists(String name) {
        if (registeredUsers.get(name) == null) {
            return false;
        }
        return true;
    }

    public User loginUser(String name, String password) {
        User user;
        if ((user = registeredUsers.get(name)) == null) {
            return null;
        }

        log.info("registered user password: " + user.getPassword());
        log.info("new user password: " + password);

        if (!user.getPassword().equals(password)) {
            return null;
        }

//        onlineUsers.put(user.getUsername(), user);

        return user;
    }

    public void publishHighscores(MemoryTable memory) {
        Highscore highscore = new Highscore();

        //TODO cleanup this method

        System.out.println(memory.getAllHighscores());
        try {
            System.out.println(highscore.getHighScoreList());
        } catch (Exception ex) {
            // TODO handle exc
            Logger.getLogger(MemoryAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(Entry<String, Integer> scoreEntry : memory.getAllHighscores().entrySet()) {
            highscore.publishHighScoreResult(new Score(scoreEntry.getValue(), scoreEntry.getKey()));
        }
    }

    private MemoryTable createMemoryTable(int memoryWidth, int memoryHeight, String creatorName, String opponentName, String continent, String stacksize) {
        if (memoryHeight % 2 != 0 && memoryWidth % 2 != 0) {
            throw new IllegalArgumentException("both params odd!");
        }
        log.info("creating new MemoryTable, width: " + memoryWidth + ", height: " + memoryHeight);
        List<String> users = new ArrayList<String>();
        users.add(creatorName);
        users.add(opponentName);
        MemoryTable memory = new MemoryTable(users);

        List<FlagInfo> flags = getFlags(continent, stacksize);
        flags.addAll(getFlags(continent, stacksize));

        Collections.shuffle(flags);
        Iterator<FlagInfo> flagsIterator = flags.iterator();

        List<List<MemoryCard>> cards = new LinkedList<List<MemoryCard>>();

        for (int i = 0; i < memoryHeight; i++) {

            List<MemoryCard> cardRow = new LinkedList<MemoryCard>();

            for (int j = 0; j < memoryWidth; j++) {
                FlagInfo flag = flagsIterator.next();
                cardRow.add(new MemoryCard(flag.getImage(), flag.getCountryName()));
            }
            cards.add(cardRow);
        }

        memory.setCards(cards);

        return memory;
    }

    public void clickOnCard(MemoryTable memory, int click_x, int click_y, String username) {
        log.info("clickOnCard at (x, y): (" + click_x + ", " + click_y + ")");
        MemoryCard card = memory.getRows().get(click_x).get(click_y);

        memory.unrevealCardsToUnreveal();

        if (card.isVisible()) {
            return;
        }

        card.setVisible(true);
        MemoryCard predecessor = memory.getLastRevealedCard();

        //1st click

        if (predecessor == null) {
            card.setVisible(true);
            memory.setLastRevealedCard(card);
            return;
        }

        //2nd click

        memory.setEndTime(new Date().getTime());

        memory.setLastRevealedCard(null);

        if (predecessor.getImagePath().equals(card.getImagePath())) {
            memory.incrementPoints(username);
            memory.incrementAttempts(username);
            memory.decrementRemainingPairs();

            if (memory.getRemainingPairs() == 0) {
                // game is over
                memory.setGameOver(true);
                memory.calulateOutcome();
                publishHighscores(memory);
            }

        } else {
            memory.addToCardsToUnreveal(predecessor);
            memory.addToCardsToUnreveal(card);
            memory.incrementAttempts(username);
            memory.nextTurn();
        }
    }

    /**
     * @return the  online users. Changes to the returned list will not be reflected but changes to specific users will.
     */
//    public List<User> getOnlineUsers() {
//        return new ArrayList<User>(onlineUsers.values());
//    }

    /*
     *
     * Private Methods
     *
     */
    private void createAndAddUser(String name, String password) {
        log.info("Adding user " + name + ", password:" + password);
        User user = createUser(name, password);
        registeredUsers.put(user.getUsername(), user);
    }

    private User createUser(String name, String password) {
        User user;
        user = new User();
        user.setUsername(name);
        user.setPassword(password);
        return user;
    }

    /**
     * Returns a game object. Creator will be set to user who created the game, table will be set according to preferences of the creator.
     * @param user
     * @return
     */
    public MemoryTable getGame(User user, MemoryCtrl control) {
        Game game;
        MemoryTable table;
        if (waitingGames.isEmpty()) {
            table = createMemoryTable(user.getMemoryWidth(), user.getMemoryHeight(), user.getUsername(), "dummyOpponentUsername", defaultContinentIfNotSet(user.getContinent()), user.getStacksize());
            game = new Game(user, table, control);
            waitingGames.add(game);
        } else {
            game = waitingGames.remove(0);

            User creator = game.getCreator();
            log.info("creator: " + creator + ", user:" + user);

            table = createMemoryTable(creator.getMemoryWidth(), creator.getMemoryHeight(), creator.getUsername(), user.getUsername(), defaultContinentIfNotSet(creator.getContinent()), creator.getStacksize());
            game.getCreatorControl().setMemoryTable(table);
            table.startGame();
        }
        return table;
    }

    public List<String> getSupportedContinents() {
        ArrayList<String> alist = new ArrayList<String>();
        try {
            for (String s : flagService.getSupportedContinents().getItem()) {
                alist.add(s);
            }
        } catch (FlagServiceException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return alist;
    }

    public List<String> getSupportedGameSizes() {
        ArrayList<String> alist = new ArrayList<String>();
        try {
            for (String s : flagService.getSupportedGameSize().getItem()) {
                alist.add(s);
            }
        } catch (FlagServiceException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return alist;
    }

    private List<FlagInfo> getFlags(String continent, String currentGameSize) {
        ArrayList<FlagInfo> alist = new ArrayList<FlagInfo>();

        FlagRequest freq = new FlagRequest();
        freq.setContinent(continent);
        freq.setGameSize(currentGameSize);
        FlagResponse fres = null;
        try {
            fres = flagService.getFlags(freq);

            for (Flag f : fres.getFlags()) {
                alist.add(new FlagInfo(f.getUrl(), f.getCountry()));
            }
        } catch (FlagServiceException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return alist;
    }

    private String defaultContinentIfNotSet(String continent) {
        if (continent == null) {
            return getSupportedContinents().get(0);
        }
        return continent;
    }
}
