package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userRepository.getUserByCookie(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/");
            return;
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String firstPassword = req.getParameter("firstPassword");
        String secondPassword = req.getParameter("secondPassword");

        if (login.equals("") || email.equals("") || firstPassword.equals("") || secondPassword.equals("")) {
            Error.getError(req, resp, "Нужно заполнить все поля", "registration.jsp");
        }

        User user = userRepository.getUserByLogin(login);

        if (user != null || !userRepository.emailIsUnique(email))
            Error.getError(req, resp, "Пользователь с такой почтой или логином уже существует", "registration.jsp");
        else if (!User.thesePasswordsMatch(firstPassword, secondPassword))
            Error.getError(req, resp, "Пароли не совпадают", "registration.jsp");
        else {
            User.create(email, login, firstPassword);
            resp.addCookie(new Cookie("login", login));
        }

        resp.sendRedirect("/");
    }
}
