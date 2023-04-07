package org.example.Hibernate;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UsersDataSet {
    @Id
    @Column private String login;
    @Column private String password;
    @Column private String email;

    public UsersDataSet(String login, String password, String email) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public UsersDataSet() {

    }

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
