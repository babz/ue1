package ewaMemory.memoryTable.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ewaMemory.memoryTable.beans.MemoryCard;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.User;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true, name = "api")
@ApplicationScoped
public class MemoryAPI {

    private static final Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());
    private static String CARD_DIR_REL_TO_WEB_CONTENT = "img/cards";
    private List<FlagInfo> allFlags = new LinkedList<FlagInfo>();
    private Map<String, User> registeredUsers = new HashMap<String, User>();

    public MemoryAPI() {
        // build flags
        allFlags.add(new FlagInfo("at.jpg", "i18nAT"));
        allFlags.add(new FlagInfo("cz.jpg", "i18nCZ"));
        allFlags.add(new FlagInfo("de.jpg", "i18nDE"));
        allFlags.add(new FlagInfo("dk.jpg", "i18nDK"));
        allFlags.add(new FlagInfo("es.jpg", "i18nES"));
        allFlags.add(new FlagInfo("fi.jpg", "i18nFI"));
        allFlags.add(new FlagInfo("fr.jpg", "i18nFR"));
        allFlags.add(new FlagInfo("gr.jpg", "i18nGR"));
        allFlags.add(new FlagInfo("it.jpg", "i18nIT"));
        allFlags.add(new FlagInfo("jp.jpg", "i18nJP"));
        allFlags.add(new FlagInfo("kr.jpg", "i18nKR"));
        allFlags.add(new FlagInfo("no.jpg", "i18nNO"));
        allFlags.add(new FlagInfo("pt.jpg", "i18nPT"));
        allFlags.add(new FlagInfo("ro.jpg", "i18nRO"));
        allFlags.add(new FlagInfo("se.jpg", "i18nSE"));
        allFlags.add(new FlagInfo("tr.jpg", "i18nTR"));
        allFlags.add(new FlagInfo("uk.jpg", "i18nUK"));
        allFlags.add(new FlagInfo("us.jpg", "i18nUS"));

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

        return user;
    }

    public MemoryTable createMemoryTable(int memoryWidth, int memoryHeight) {
        if (memoryHeight % 2 != 0 && memoryWidth % 2 != 0) {
            throw new IllegalArgumentException("both params odd!");
        }
        log.info("creating new MemoryTable, width: " + memoryWidth + ", height: " + memoryHeight);
        MemoryTable memory = new MemoryTable();

        log.info("time starts");
        memory.setStartTime(new Date().getTime());

        List<FlagInfo> flags = new ArrayList<FlagInfo>();

        Collections.shuffle(allFlags);

        int neededFlags = memoryHeight * memoryWidth / 2;
        for (int i = 0; i < neededFlags; i++) {
            FlagInfo newFlag = allFlags.get(i % allFlags.size());  // get flags and reuse, if not enough unique flags exist
            flags.add(newFlag);
            flags.add(newFlag);
        }

        Collections.shuffle(flags);
        Iterator<FlagInfo> flagsIterator = flags.iterator();

        List<List<MemoryCard>> cards = new LinkedList<List<MemoryCard>>();

        for (int i = 0; i < memoryHeight; i++) {

            List<MemoryCard> cardRow = new LinkedList<MemoryCard>();

            for (int j = 0; j < memoryWidth; j++) {
                FlagInfo flag = flagsIterator.next();
                cardRow.add(new MemoryCard(CARD_DIR_REL_TO_WEB_CONTENT + "/" + flag.getImage(), flag.getI18nKey()));
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

        if (card.isVisible()) {
            return;
        }

        log.info("Card is not visible!");
        card.setVisible(true);
        MemoryCard predecessor = memory.getLastRevealedCard();

        if (predecessor == null) {
            card.setVisible(true);
            memory.setLastRevealedCard(card);
            return;
        }

        memory.setLastRevealedCard(null);

        if (predecessor.getImagePath().equals(card.getImagePath())) {
            memory.incrementPoints();
            memory.incrementAttempts();
            memory.decrementRemainingPairs();
        } else {
            memory.addToCardsToUnreveal(predecessor);
            memory.addToCardsToUnreveal(card);
            memory.incrementAttempts();
        }
    }
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
}
