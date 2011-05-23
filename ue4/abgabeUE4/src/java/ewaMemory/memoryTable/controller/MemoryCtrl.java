package ewaMemory.memoryTable.controller;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.Outcome;
import ewaMemory.memoryTable.beans.User;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.icefaces.application.PushRenderer;

@ManagedBean
@SessionScoped
public class MemoryCtrl {

    private static final Logger log = Logger.getLogger(MemoryCtrl.class.getSimpleName());
    @ManagedProperty("#{api}")
    private MemoryAPI memoryApi;
    @ManagedProperty("#{user}")
    private User user;

//    @ManagedProperty("#{pushManager}")
//    private PushManager pushManager;
    
    private MemoryTable memoryTable;
//    private final PortableRenderer renderer;

    public MemoryCtrl() {
        log.info("MemoryCtrl created!");
        PushRenderer.addCurrentSession("users");
    }

    public void cardClicked(int x, int y) {
        log.info("(MemoryCtrl) cardClicked at (x,y): (" + x + "," + y + ")");
        if(getMemoryTable().isUserTurn(user.getUsername()) && getMemoryTable().isGameHasStarted()) {
            memoryApi.clickOnCard(getMemoryTable(), x, y, user.getUsername());
            updateGame();
        }
    }

    public String newGame() {
        log.info("starting new game. width:" + user.getMemoryWidth() + ", height: " + user.getMemoryHeight());

        memoryTable = memoryApi.getGame(user, this);
        updateGame();

        return "memoryTable.xhtml";
    }

    public int getOwnPoints() {
        log.info("getOwnPoints: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPoints(getUser().getUsername());
    }

    public int getOwnAttempts() {
         log.info("getOwnAttempts: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getAttempts(getUser().getUsername());
    }

    public int getOpponentPoints() {
        log.info("getOpponentPoints: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPoints(getOpponentUsername());
    }

    public int getOpponentAttempts() {
        log.info("getOpponentAttempts: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getAttempts(getOpponentUsername());
    }

    public String getOwnPlayTime() {
        log.info("getOwnPlayTime: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPlayTime(getUser().getUsername());
    }

    public String getOpponentPlayTime() {
        log.info("getOpponentPlayTime: user is:"+getUser().getUsername()+"-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPlayTime(getOpponentUsername());
    }

    public MemoryAPI getMemoryApi() {
        return memoryApi;
    }

    public void setMemoryApi(MemoryAPI memoryApi) {
        this.memoryApi = memoryApi;
    }

    public MemoryTable getMemoryTable() {
        return memoryTable;
    }

    public void setMemoryTable(MemoryTable memoryTable) {
        this.memoryTable = memoryTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the opponent
     */
    public String getOpponentUsername() {
        String[] usernames = memoryTable.getUsernames();
        for(String uname : usernames) {
            if(!uname.equals(user.getUsername()))
                return uname;
        }
        return "no opponent";
    }

    private void updateGame() {
        PushRenderer.render("users");
    }

    public boolean isGameWon() {
        return compareOutcome(Outcome.WIN);
    }

    public boolean isGameLost() {
        return compareOutcome(Outcome.LOOSE);
    }

    public boolean isGameDraw() {
        return compareOutcome(Outcome.DRAW);
    }

    private boolean compareOutcome(Outcome testedOutCome) {
        Outcome outcome = memoryTable.getOutcome(getUser().getUsername());
        if (outcome == testedOutCome) {
            return true;
        }

        return false;
    }

//    /**
//     * @return the pushManager
//     */
//    public PushManager getPushManager() {
//        return pushManager;
//    }
//
//    /**
//     * @param pushManager the pushManager to set
//     */
//    public void setPushManager(PushManager pushManager) {
//        this.pushManager = pushManager;
//    }
}
