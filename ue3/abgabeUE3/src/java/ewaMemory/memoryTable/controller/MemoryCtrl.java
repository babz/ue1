package ewaMemory.memoryTable.controller;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.User;
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
    @ManagedProperty("#{memory}")
    private MemoryTable memoryTable;
    @ManagedProperty("#{user}")
    private User user;

    public MemoryCtrl() {
        log.info("MemoryCtrl created!");
    }

    public void cardClicked(int x, int y) {
//        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        int x = Integer.parseInt(requestMap.get(MemoryTableParams.X));
//        int y = Integer.parseInt(requestMap.get(MemoryTableParams.Y));

        log.info("(MemoryCtrl) cardClicked at (x,y): (" + x + "," + y + ")");
        memoryApi.clickOnCard(memoryTable, x, y);

        //return "memoryTable.xhtml";
    }

    public String newGame() {
        log.info("starting new game. width:"+user.getMemoryWidth()+", height: "+user.getMemoryHeight());

        MemoryTable table = memoryApi.createMemoryTable(user.getMemoryWidth(), user.getMemoryHeight());
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("memory", table);
        memoryTable = table;

        return "memoryTable.xhtml";
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
}
