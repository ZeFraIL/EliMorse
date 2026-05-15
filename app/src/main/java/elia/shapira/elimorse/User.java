package elia.shapira.elimorse;

import java.io.Serializable;

/**
 * Represents a user in the EliMorse application.
 * Stores user credentials and contact information.
 */
public class User implements Serializable {
    /** The unique identifier for the user in the database. */
    private int id;
    /** The user's chosen username. */
    private String userName;
    /** The user's password. */
    private String userPassword;
    /** The user's phone number. */
    private String userPhone;
    /** The user's email address. */
    private String userMail;

    /**
     * Constructs a new User with all fields.
     *
     * @param id           The unique identifier.
     * @param userName     The username.
     * @param userPassword The password.
     * @param userPhone    The phone number.
     * @param userMail     The email address.
     */
    public User(int id, String userName, String userPassword, String userPhone, String userMail) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userMail = userMail;
    }

    /**
     * Constructs a new User without an ID (useful for new registrations).
     *
     * @param userName     The username.
     * @param userPassword The password.
     * @param userPhone    The phone number.
     * @param userMail     The email address.
     */
    public User(String userName, String userPassword, String userPhone, String userMail) {
        this(-1, userName, userPassword, userPhone, userMail);
    }

    /**
     * Gets the user ID.
     * @return The user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user ID.
     * @param id The new user ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username.
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     * @param userName The new username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user password.
     * @return The password.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the user password.
     * @param userPassword The new password.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Gets the user phone number.
     * @return The phone number.
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * Sets the user phone number.
     * @param userPhone The new phone number.
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * Gets the user email address.
     * @return The email address.
     */
    public String getUserMail() {
        return userMail;
    }

    /**
     * Sets the user email address.
     * @param userMail The new email address.
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    /**
     * Returns a string representation of the User object.
     * @return A string containing user details (excluding password for security might be better, but keeping consistency).
     */
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
