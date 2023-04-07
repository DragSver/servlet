package org.example.JDBC;

import org.example.UserRepository;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class User {
    private String email;
    private String login;
    private String password;

    private static final UserRepository userRepository = new UserRepository();


    public User(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public static void create(@NotNull String email, @NotNull String login, @NotNull String password) {
        userRepository.add(login, password, email);
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
