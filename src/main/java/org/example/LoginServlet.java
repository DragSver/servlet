package org.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/authorization")
public class LoginServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userRepository.getUserByCookie(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/");
            return;
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.equals("") || password.equals(""))
            Error.getError(req, resp, "Нужно заполнить оба поля", "login.jsp");

        User user = userRepository.getUserByLogin(login);

        if (user == null)
            Error.getError(req, resp, "Пользователя с таким логином не существует", "login.jsp");
        else if (!user.thisPasswordCorrect(password))
            Error.getError(req, resp, "Введен неправильный пароль", "login.jsp");
        else
            resp.addCookie(new Cookie("login", login));

        resp.sendRedirect("/");
    }
}
