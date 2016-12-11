package ru.innopolis.models;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */

/*Модель пользователя*/
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String login;
    private String password;
    private boolean isLocked;

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public User(String login) {
        this.login = login;
    }

    public User(int id, String firstname, String lastname, String email, String login, boolean isLocked) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.isLocked = isLocked;
    }

    public User(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(int id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String firstname, String lastname, String email, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", isLocked=" + isLocked +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
