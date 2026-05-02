package elia.shapira.elimorse;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userMail;

    public User(int id, String userName, String userPassword, String userPhone, String userMail) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userMail = userMail;
    }

    public User(String userName, String userPassword, String userPhone, String userMail) {
        this(-1, userName, userPassword, userPhone, userMail);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userMail='" + userMail + '\'' +
                '}';
    }
}
