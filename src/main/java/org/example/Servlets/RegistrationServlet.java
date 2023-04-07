package org.example.Servlets;

import org.example.Error;
import org.example.Hibernate.UsersDataSet;
import org.example.UserRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsersDataSet user = userRepository.getUserByCookie(req.getCookies());
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

        UsersDataSet user = userRepository.getUserByLogin(login);

        if (user != null || !userRepository.emailIsUnique(email))
            Error.getError(req, resp, "Пользователь с такой почтой или логином уже существует", "registration.jsp");
        else if (!UsersDataSet.thesePasswordsMatch(firstPassword, secondPassword))
            Error.getError(req, resp, "Пароли не совпадают", "registration.jsp");
        else {
            new UsersDataSet(email, login, firstPassword);
            resp.addCookie(new Cookie("login", login));
        }

        resp.sendRedirect("/");
    }
}
