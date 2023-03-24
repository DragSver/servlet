package org.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (DBService.connection == null) DBService.getConnection();
            User user = UserRepository.getUserByCookie(req.getCookies());
            if (user != null) {
                resp.sendRedirect("/");
                return;
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (DBService.connection == null) DBService.getConnection();
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            if (login.equals("") || password.equals("")) {
                Error.getError(req, resp, "Нужно заполнить оба поля", "login.jsp");
            }
            User user = UserRepository.getUserByLogin(login);
            if (user == null) {
                Error.getError(req, resp, "Пользователя с таким логином не существует", "login.jsp");
            }
            if (!user.thisPasswordCorrect(password)) {
                Error.getError(req, resp, "Введен неправильный пароль", "login.jsp");
            }

            resp.addCookie(new Cookie("login", login));
            resp.sendRedirect("/");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
