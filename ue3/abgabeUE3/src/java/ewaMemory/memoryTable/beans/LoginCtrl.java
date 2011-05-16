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

/**
 *
 * @author forste
 */
@ManagedBean
@SessionScoped
public class LoginCtrl {
    private static final Logger log = Logger.getLogger(LoginCtrl.class.getSimpleName());
    
    @ManagedProperty(value="#{user}")
    private User user;
    @ManagedProperty(value="#{api}")
    private MemoryAPI api;

    private boolean displayPersData = false;

    //TODO save list of customers in class with application scope

    boolean loginfailed = false;

    public LoginCtrl() {
         log.info("LoginCtrl created!");
    }

        //Login - check password
    public String login()
    {
        log.info("login; customer: "+getUser().toString());
        User registeredUser = null;
        try {
            registeredUser = getApi().getUserByName(getUser().getUsername());
        } catch (UserNotRegisteredException ex) {
            log.info(ex.getMessage());
        }

        if(getUser() == null)
            return "/login.xhtml";

        if(getUser().getPassword().equals(registeredUser.getPassword()))
        {
            loginfailed = false;
            return "/register.xhtml";
//            return "/memoryTable.xhtml"; TODO comment in
        }

        else
        {
            loginfailed = true;
            return "/login.xhtml";
        }
    }

    //Validation of the username
    public void validateUsername(FacesContext ctx, UIComponent component, Object value) throws ValidatorException
    {
        String username = (String)value;

        if(!username.equals("Markus") && !username.equals("Heidi"))
        {
            FacesMessage msg = new FacesMessage(
            FacesMessage.SEVERITY_WARN,"Wrong username!", null);
            throw new ValidatorException(msg);
        }
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
