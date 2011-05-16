package ewaMemory.memoryTable.beans;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.api.UserNotRegisteredException;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import javax.faces.validator.ValidatorException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author forste
 */
@ManagedBean
@SessionScoped
public class UserCtrl {
    private static final Logger log = Logger.getLogger(UserCtrl.class.getSimpleName());
    
    @ManagedProperty(value="#{user}")
    private User user;
    @ManagedProperty(value="#{api}")
    private MemoryAPI api;

    private boolean displayPersData = false;

    //TODO save list of customers in class with application scope

    boolean loginfailed = false;

    public UserCtrl() {
         log.info("LoginCtrl created!");
    }

        //Login - check password
    public String login()
    {
        loginfailed = true;
        
        if(getUser() == null)
            return "/login.xhtml";
        
        log.info("login; user: "+getUser().toString());
        
        User registeredUser = getApi().loginUser(getUser().getUsername(), getUser().getPassword());
        if (registeredUser == null) {
            return "/login.xhtml";
        }
        
        loginfailed = false;

        MemoryTable table = getApi().createMemoryTable(user.getMemoryWidth(), user.getMemoryHeight()); // TODO take values from login/user
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("memory", table);

        return "/memoryTable.xhtml";
    }

    public String register() {
        log.info(user.toString());
        api.registerUser(user);

        return "/login.xhtml";
    }

    //Getter and Setter
    public User getCustomer() {
        return getUser();
    }

    public void setCustomer(User customer) {
        this.setUser(customer);
    }

    public Date getDatetime() {
        return new Date();
    }

    public boolean isLoginfailed() {
        return loginfailed;
    }

    public void setLoginfailed(boolean loginfailed) {
        this.loginfailed = loginfailed;
    }

    public MemoryAPI getApi() {
        return api;
    }

    public void setApi(MemoryAPI api) {
        this.api = api;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }


        /**
     * @return the displayPersData
     */
    public boolean isDisplayPersData() {
        return displayPersData;
    }

    /**
     * @param displayPersData the displayPersData to set
     */
    public void setDisplayPersData(boolean displayPersData) {
        this.displayPersData = displayPersData;
    }

    public void toggleDisplayPersData() {
        if(displayPersData)
            displayPersData = false;
        else
            displayPersData = true;
    }

    
}
