package org.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.getUserByCookie(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/");
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String firstPassword = req.getParameter("firstPassword");
        String secondPassword = req.getParameter("secondPassword");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("registration.jsp");

        if (login.equals("") || email.equals("") || firstPassword.equals("") || secondPassword.equals("")) {
            Error.getError(req, resp, "Нужно заполнить все поля", "registration.jsp");
        }
        User user = UserRepository.getUserByLogin(login);
        if (user != null || !UserRepository.thisEmailIsUnique(email)) {
            Error.getError(req, resp, "Пользователь с такой почтой или логином уже существует", "registration.jsp");
        }
        if (!User.thesePasswordsMatch(firstPassword, secondPassword)) {
            Error.getError(req, resp, "Пароли не совпадают", "registration.jsp");
        }
        User.create(email, login, firstPassword);
        resp.addCookie(new Cookie("login", login));
        resp.sendRedirect("/");
    }
}
