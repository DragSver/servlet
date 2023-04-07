package org.example;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;

public class UserRepository {

    private DBService dbService = new DBService();

    public boolean add(@NotNull String login, @NotNull String password, @NotNull String email) {
        return dbService.addUser(login, password, email);
    }

    public boolean emailIsUnique(@NotNull String email) {
        return dbService.emailIsUnique(email);
    }
    public boolean loginIsUnique(@NotNull String login) {
        return dbService.loginIsUnique(login);
    }
    public User getUserByLogin(@NotNull String login) {
        return dbService.getUser(login);
    }
    public User getUserByCookie(Cookie[] cookies) {
        String login = CookieUtil.findLoginByCookie(cookies, "login");
        if (login != null) {
            User user = null;
            user = getUserByLogin(login);
            if (user != null) {
                return user;
            }
        }
        return null;
    }
}
