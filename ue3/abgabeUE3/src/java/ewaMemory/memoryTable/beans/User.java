package ewaMemory.memoryTable.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;


@ManagedBean(name="user")
@SessionScoped
public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String password;


    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        firstname = name;
    }

    public String getLastname() {
        return firstname;
    }

    public void setLastname(String name) {
        lastname = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String bdate) {
        birthdate = bdate;
    }

    public SelectItem getFemaleItem() {
        return new SelectItem("w", "Weiblich");
    }

    public SelectItem getMaleItem() {
       return new SelectItem("m", "Männlich");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
}
