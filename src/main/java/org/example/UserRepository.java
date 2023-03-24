package org.example;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.util.HashMap;

public class UserRepository {
    //static private final ArrayList<User> userRepository;
//    static private final HashMap<String, User> userMap;

//    static {
//        //userRepository = new ArrayList<>();
//        userMap = new HashMap<>();
//    }
    public static void add(@NotNull User user) throws SQLException {
        //userRepository.add(user);
//        userMap.put(user.getLogin(), user);
        DBService.add(user);
    }

    public static boolean thisEmailIsUnique(@NotNull String email) throws SQLException {
        return DBService.thisEmailIsUnique(email);
    }
    public static boolean thisLoginIsUnique(@NotNull String login) throws SQLException {
        return DBService.thisLoginIsUnique(login);
    }
    public static User getUserByLogin(@NotNull String login) throws SQLException {
        return DBService.getUserByLogin(login);
    }
    public static User getUserByCookie(Cookie[] cookies) throws SQLException {
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
