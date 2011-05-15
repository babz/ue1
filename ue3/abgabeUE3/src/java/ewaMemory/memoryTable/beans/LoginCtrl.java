package ewaMemory.memoryTable.beans;

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
    
    @ManagedProperty(value="#{customer}")
    private User customer;
    @ManagedProperty(value = "true")
    private boolean displayonline;

    //TODO save list of customers in class with application scope

    boolean loginfailed = false;

    public LoginCtrl() {
    }

    //Getter and Setter
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
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

    public boolean isDisplayonline() {
        return displayonline;
    }

    public void setDisplayonline(boolean displayonline) {
        this.displayonline = displayonline;
    }


    public int getOnlineCustomers()
    {
        return new Random().nextInt(10) + 1;
    }

    //Login - check password
    public String login()
    {
        log.info("login; customer: "+customer.toString());
        if(customer.getPassword().equals("secret"))
        {
            loginfailed = false;
            return "/store_main.xhtml";
        }

        else
        {
            loginfailed = true;
            return "/login.xhtml";
        }
    }

    //Checks if the display checkbox changed
    public void displayChanged(ValueChangeEvent e){
        Boolean show = (Boolean) e.getNewValue();
        if(show != null)
            displayonline = show;

        FacesContext.getCurrentInstance().renderResponse();
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


}
