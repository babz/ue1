package ewaMemory.memoryTable.api;

import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.User;
import ewaMemory.memoryTable.controller.MemoryCtrl;

public class Game {

    private User creator;
    private MemoryTable table;
    private final MemoryCtrl creatorControl;

    Game(User creator, MemoryTable table, MemoryCtrl control) {
                this.creator = creator;
                this.table = table;
                this.creatorControl = control;
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }
    /**
     * @return the table
     */
    public MemoryTable getTable() {
        return table;
    }

   public  void setMemoryTable(MemoryTable table) {
        this.table = table;
    }

    /**
     * @return the creatorControl
     */
    public MemoryCtrl getCreatorControl() {
        return creatorControl;
    }
}
