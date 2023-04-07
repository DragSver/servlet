package org.example.ORM;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UsersDataSet {
    @Id
    @Column private int id;
    @Column private String email;
    @Column private String login;
    @Column private String password;

    public UsersDataSet(int id, String email, String login, String password) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public int getId() {return id;}
    public String getEmail() {
        return email;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    public static boolean thesePasswordsMatch(@NotNull String firstPassword, @NotNull String secondPassword) {
        return firstPassword.equals(secondPassword);
    }
    public boolean thisPasswordCorrect(@NotNull String password) {
        return getPassword().equals(password);
    }
}
