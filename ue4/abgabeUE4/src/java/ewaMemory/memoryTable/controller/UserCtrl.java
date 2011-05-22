package ewaMemory.memoryTable.controller;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.api.UsernameAlreadyRegisteredException;
import ewaMemory.memoryTable.beans.MemoryTable;
import ewaMemory.memoryTable.beans.User;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
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
    @ManagedProperty(value = "#{user}")
    private User user;
    @ManagedProperty(value = "#{api}")
    private MemoryAPI api;
    @ManagedProperty("#{memoryCtrl}")
    private MemoryCtrl memoryCtrl;

    public boolean displayPersData = false;
    //TODO save list of customers in class with application scope
    private boolean loginfailed = false;
    private UserDataValidator validator = new UserDataValidator();
    private boolean usernameAlreadyRegistered;

    

    public UserCtrl() {

        log.info("LoginCtrl created!");
    }

    //Login - check password
    public String login() {
        loginfailed = true;

        if (getUser() == null) {
            return "/login.xhtml";
        }

        log.info("login; user: " + getUser().toString() +", password: "+getUser().getPassword());

        User registeredUser = getApi().loginUser(getUser().getUsername(), getUser().getPassword());
        if (registeredUser == null) {
            return "/login.xhtml";
        }

        user = registeredUser;
        loginfailed = false;
        
        return memoryCtrl.newGame();
    }

    public String register() {
          if (getUser() == null) {
            return "/register.xhtml";
        }

          log.info("register: user: "+getUser().toString()+", password: "+getUser().getPassword());

        usernameAlreadyRegistered = true;
        try {
            api.registerUser(getUser());
        } catch (UsernameAlreadyRegisteredException ex) {
            Logger.getLogger(UserCtrl.class.getName()).log(Level.SEVERE, null, ex);
            return "/register.xhtml";
        }
          
        usernameAlreadyRegistered = false;

        return "/login.xhtml";
    }

    public void validateDate(FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {
        validator.validateDate(context, componentToValidate, value);
    }

     public void validateStacksize(FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {
         validator.validateStacksize(context, componentToValidate, value);
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


    public MemoryCtrl getMemoryCtrl() {
        return memoryCtrl;
    }

    public void setMemoryCtrl(MemoryCtrl memoryCtrl) {
        this.memoryCtrl = memoryCtrl;
    }

    /**
     * @return the displayPersData
     */
    public boolean isDisplayPersData() {
        log.info("isDisplayPersData: return "+displayPersData);
        return displayPersData;
    }

    /**
     * @param displayPersData the displayPersData to set
     */
    public boolean setDisplayPersData(boolean displayPersData) {
        log.info("setDisplayPersData: set to "+displayPersData);
        this.displayPersData = displayPersData;
        return displayPersData;
    }

    	//Checks if the displayPersData checkbox changed
	public void displayPersDataChanged(ValueChangeEvent e) {
		Boolean show = (Boolean) e.getNewValue();
		if (show != null) {
			displayPersData = show;
		}

		FacesContext.getCurrentInstance().renderResponse();
	}

    public void toggleDisplayPersData() {
        if (displayPersData) {
            displayPersData = false;
        } else {
            displayPersData = true;
        }
    }

    /**
     * @return the usernameAlreadyRegistered
     */
    public boolean isUsernameAlreadyRegistered() {
        return usernameAlreadyRegistered;
    }
}
