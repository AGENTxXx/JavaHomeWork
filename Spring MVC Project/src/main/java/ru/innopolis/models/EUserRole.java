package ru.innopolis.models;

import javax.persistence.*;

/**
 * Created by Alexander Chuvashov on 12.12.2016.
 */

@Entity
@Table(name = "user_roles")
public class EUserRole {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="user_roles_id_seq",
            sequenceName="user_roles_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="user_roles_id_seq")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public EUserRole(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public EUserRole() {
        super();
    }
}
