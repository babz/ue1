package ewaMemory.memoryTable.beans;

import ewaMemory.memoryTable.gui.MemoryTableParams;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import sun.util.logging.resources.logging;

@ManagedBean
@SessionScoped
public class MemoryCtrl {
    private static final Logger log = Logger.getLogger(MemoryCtrl.class.getSimpleName());

    public String cardClicked() {
        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int x = Integer.parseInt(requestMap.get(MemoryTableParams.X));
        int y = Integer.parseInt(requestMap.get(MemoryTableParams.Y));

        log.info("cardClicked at (x,y): (" + x + "," + y + ")");

        return "memoryTable.xhtml";
    }
}
