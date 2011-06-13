package ewaMemory.memoryTable.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class User {

    private String username;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String password;
    private int memoryWidth = 4;
    private int memoryHeight = 4;
    private String gender; //f od m
    private String continent;

    public User(User user) {
        username = user.getUsername();
        firstname = user.getFirstname();
        lastname = user.getLastname();
        birthdate = user.getBirthdate();
        password = user.getPassword();
        memoryWidth = user.getMemoryWidth();
        memoryHeight = user.getMemoryHeight();
        continent = user.getContinent();
    }

    public User() {
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String g) {
        gender = g;
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

    @Override
    public String toString() {
        return "username: " + getUsername() + ", full name: " + getFullname() + ", birthdate: " + birthdate;
    }

    /**
     * @return the memoryWidth
     */
    public int getMemoryWidth() {
        return memoryWidth;
    }
//
//    /**
//     * @param memoryWidth the memoryWidth to set
//     */
//    public void setMemoryWidth(int memoryWidth) {
//        this.memoryWidth = memoryWidth;
//    }
//

    /**
     * @return the memoryHeight
     */
    public int getMemoryHeight() {
        return memoryHeight;
    }
//
//    /**
//     * @param memoryHeight the memoryHeight to set
//     */
//    public void setMemoryHeight(int memoryHeight) {
//        this.memoryHeight = memoryHeight;
//    }

    public String getStacksize() {
        return memoryWidth + "x" + memoryHeight;
    }

    public void setStacksize(String stacksize) {
        if (!stacksize.equals("__default")) {
            String[] parts = stacksize.split("x");
            memoryWidth = Integer.parseInt(parts[0]);
            memoryHeight = Integer.parseInt(parts[1]);
        }
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        if (!continent.equals("__default")) {
            this.continent = continent;
        }
    }
}
