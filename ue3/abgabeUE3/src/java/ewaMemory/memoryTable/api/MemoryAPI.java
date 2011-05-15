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
import ewaMemory.memoryTable.beans.User;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(eager=true, name="api")
@ApplicationScoped
public class MemoryAPI {
	private static Logger log = Logger.getLogger(MemoryAPI.class.getSimpleName());
        private static String CARD_DIR_REL_TO_WEB_CONTENT = "img/cards";
	private List<String> allFlags;
        private Map<String, User> registeredUsers = new HashMap<String, User>() {};

//
//	public MemoryAPI(List<String> cardDecks) {
//		allFlags = cardDecks;
//	}
        public MemoryAPI() {
            createAndAddUser("Franz", "12345");
            createAndAddUser("Helga", "12345");
            createAndAddUser("Hubsi", "12345");
            createAndAddUser("Ylgal", "12345");
           log.info("MemoryAPI created!");
        }

        // START TESTCODE TODO REMOVE

        public void printSthing() {
            System.out.println("PRINTING SOMETHING!!");
            setTrigger(true);

            FacesContext.getCurrentInstance().renderResponse();
        }

        private boolean trigger = true;

          /**
         * @return the trigger
         */
        public boolean isTrigger() {
            return trigger;
        }

        /**
         * @param trigger the trigger to set
         */
        public void setTrigger(boolean trigger) {
            this.trigger = trigger;
        }

        // END TESTCODE TODO REMOVE

        private void createAndAddUser(String name, String password) {
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

        public void registerUser(User user) {
            //TODO check if user is present
            registeredUsers.put(user.getUsername(), user);
        }

        /**
         * Logs
         * @param login
         * @return
         * @throws UserNotRegisteredException
         * @throws UserAlreadyLoggedInException
         */
        public User getUserByName(String name) throws UserNotRegisteredException {
            User user;
            
            if((user = registeredUsers.get(name)) == null)
                throw new UserNotRegisteredException(name);

            /* TODO do we need this, comment in if we are able to log user out as well
            if(user.isOnline())
                throw new UserAlreadyLoggedInException(login.getName());

            user.setOnline(true); */
            
            return user;
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
				cardRow.add(new MemoryCard(CARD_DIR_REL_TO_WEB_CONTENT + "/" + flagsIterator.next(), ""));
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
