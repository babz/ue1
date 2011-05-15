/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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


@ManagedBean
@SessionScoped
public class RegistrationCtrl {

    private static final Logger log = Logger.getLogger(RegistrationCtrl.class.getSimpleName());

    @ManagedProperty(value="#{user}")
    private User user;
    @ManagedProperty(value="#{api}")
    private MemoryAPI api;

    //TODO save list of customers in class with application scope

    boolean loginfailed = false;

    public RegistrationCtrl() {
         log.info("RegistrationCtrl created!");
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

    //Validation of the username
    //throws ValidatorException if username already in use
    public void validateUsername(FacesContext ctx, UIComponent component, Object value) throws ValidatorException
    {
        String username = (String)value;

        if(getApi().userExists(username))
        {
            FacesMessage msg = new FacesMessage(
            FacesMessage.SEVERITY_WARN,"Username already in use!", null);
            throw new ValidatorException(msg);
        }
    }
}
