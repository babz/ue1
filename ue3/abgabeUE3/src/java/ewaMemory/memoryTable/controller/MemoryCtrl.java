package ewaMemory.memoryTable.controller;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.gui.MemoryTableParams;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import sun.util.logging.resources.logging;

@ManagedBean
@SessionScoped
public class MemoryCtrl {
    private static final Logger log = Logger.getLogger(MemoryCtrl.class.getSimpleName());

    @ManagedProperty("#{api}")
    private MemoryAPI memoryApi;
    @ManagedProperty("#{memory}")
    private MemoryTable memoryTable;

    public void cardClicked(int x, int y) {
//        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        int x = Integer.parseInt(requestMap.get(MemoryTableParams.X));
//        int y = Integer.parseInt(requestMap.get(MemoryTableParams.Y));

        log.info("(MemoryCtrl) cardClicked at (x,y): (" + x + "," + y + ")");
        memoryApi.clickOnCard(memoryTable, x, y);

        //return "memoryTable.xhtml";
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
}
