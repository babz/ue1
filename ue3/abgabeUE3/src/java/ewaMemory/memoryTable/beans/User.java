package ewaMemory.memoryTable.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="user")
@SessionScoped
public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String birthdate;
//    private UserSex sex;
    private String password;


    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String name) {
        firstname = name;
    }

    public String getLastName() {
        return firstname;
    }

    public void setLastName(String name) {
        lastname = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String bdate) {
        birthdate = bdate;
    }

//    public UserSex getUserSex() {
//        return sex;
//    }
//
//    public void setUserSex(UserSex sex){
//        this.sex = sex;
//    }

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
