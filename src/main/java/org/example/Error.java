package org.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Error {
    public static void getError(HttpServletRequest req, HttpServletResponse resp, String errorText, String path) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        req.setAttribute("error", errorText);
        requestDispatcher.forward(req, resp);
    }
}
