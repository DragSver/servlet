package org.example;

import javax.servlet.http.Cookie;

public class CookieUtil {
    public static String findLoginByCookie(Cookie[] cookies, String value) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(value)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
