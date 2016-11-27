package ru.innopolis.modal;

/**
 * Created by Alexander Chuvashov on 24.11.2016.
 */

/*Класс сущности "Пользователь"*/
public class User {
    String id;
    String firstname;
    String lastname;
    String login;
    String email;
    boolean locked;

    public String getEmail() {
        return email;
    }

    public String getId() {
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

    public boolean isLocked() {
        return locked;
    }

    public User(String id, String firstname, String lastname, String email, String login, boolean locked) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.locked = locked;
    }
}
