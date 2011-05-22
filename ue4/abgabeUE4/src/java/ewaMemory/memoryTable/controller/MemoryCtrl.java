package ewaMemory.memoryTable.controller;

import ewaMemory.memoryTable.api.Game;
import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.User;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class MemoryCtrl {

    private static final Logger log = Logger.getLogger(MemoryCtrl.class.getSimpleName());
    @ManagedProperty("#{api}")
    private MemoryAPI memoryApi;
    @ManagedProperty("#{user}")
    private User user;
    
    private MemoryTable memoryTable;

    public MemoryCtrl() {
        log.info("MemoryCtrl created!");
    }

    public void cardClicked(int x, int y) {
//        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        int x = Integer.parseInt(requestMap.get(MemoryTableParams.X));
//        int y = Integer.parseInt(requestMap.get(MemoryTableParams.Y));

        log.info("(MemoryCtrl) cardClicked at (x,y): (" + x + "," + y + ")");
        if(getMemoryTable().isUserTurn(user.getUsername()))
            memoryApi.clickOnCard(getMemoryTable(), x, y, user.getUsername());
        

        //return "memoryTable.xhtml";
    }

    public String newGame() {
        log.info("starting new game. width:" + user.getMemoryWidth() + ", height: " + user.getMemoryHeight());

        memoryTable = memoryApi.getGame(user, this);


//       TODO do we need this (insert table in session ? )
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("memory", memoryTable);


        return "memoryTable.xhtml";
    }

    public int getOwnPoints() {
        log.info("getOwnPoints: user is:"+getUser().getUsername());
        if(getOpponentUsername() != null)
            log.info("-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPoints(getUser().getUsername());
    }

    public int getOwnAttempts() {
         log.info("getOwnAttempts: user is:"+getUser().getUsername());
        if(getOpponentUsername() != null)
            log.info("-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getAttempts(getUser().getUsername());
    }

    public int getOpponentPoints() {
        log.info("getOpponentPoints: user is:"+getUser().getUsername());
        if(getOpponentUsername() == null)
            return 0;
         log.info("-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPoints(getOpponentUsername());
    }

    public int getOpponentAttempts() {
        log.info("getOpponentAttempts: user is:"+getUser().getUsername());
        if(getOpponentUsername() == null)
            return 0;
        log.info("-  opponent is :" + getOpponentUsername());
        return getMemoryTable().getPoints(getOpponentUsername());
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
        return memoryTable.getOpponentUsername(user.getUsername());
    }
}
