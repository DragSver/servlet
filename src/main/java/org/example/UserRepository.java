package org.example;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;
import java.util.HashMap;

public class UserRepository {
    //static private final ArrayList<User> userRepository;
    static private final HashMap<String, User> userMap;

    static {
        //userRepository = new ArrayList<>();
        userMap = new HashMap<>();
    }
    public static void add(@NotNull User user) {
        //userRepository.add(user);
        userMap.put(user.getLogin(), user);
    }
    public static boolean thisEmailIsUnique(@NotNull String email) {
        for (User user : userMap.values()) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    public static boolean thisLoginIsUnique(@NotNull String login) {
        return getUserByLogin(login) == null;
    }
    public static User getUserByLogin(String login) {
        return userMap.get(login);
    }
    public static User getUserByCookie(Cookie[] cookies) {
        String login = CookieUtil.findLoginByCookie(cookies, "login");
        if (login != null) {
            User user = getUserByLogin(login);
            if (user != null) {
                return user;
            }
        }
        return null;
    }
}
