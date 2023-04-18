package org.example;

import org.example.Hibernate.DBService;
import org.example.Hibernate.UsersDataSet;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;

public class UserRepository {

    private final DBService dbService = new DBService();

    public boolean add(@NotNull String login, @NotNull String password, @NotNull String email) {
        return dbService.addUser(login, password, email);
    }

    public boolean emailIsUnique(@NotNull String email) {
        return dbService.emailIsUnique(email);
    }
    public boolean loginIsUnique(@NotNull String login) {
        return dbService.loginIsUnique(login);
    }
    public UsersDataSet getUserByLogin(@NotNull String login) {
        return dbService.getUser(login);
    }
    public UsersDataSet getUserByCookie(Cookie[] cookies) {
        String login = CookieUtil.findLoginByCookie(cookies, "login");
        if (login != null) {
            UsersDataSet user = null;
            user = getUserByLogin(login);
            if (user != null) {
                return user;
            }
        }
        return null;
    }
}
