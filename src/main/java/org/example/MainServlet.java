package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainServlet extends HttpServlet {
    public SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (DBService.connection == null) DBService.getConnection();
            User user = UserRepository.getUserByCookie(req.getCookies());
            if (user == null) {
                resp.sendRedirect("/login");
                return;
            }
            String path = req.getParameter("path");
            File file;
            if (path == null || !path.contains("D:/My/" + user.getLogin())) {
                file = new File("D:\\My\\" + user.getLogin());
                file.mkdir();
            } else {
                file = new File(path.replace("%20", " "));
            }
            if (file.isDirectory()) {
                getIsDirectory(file, user, req, resp);
            } else {
                download(file, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getIsDirectory(File file, User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> fileList = Arrays.asList(file.listFiles());
        String date = format.format(new Date());
        req.setAttribute("format", format);
        req.setAttribute("date", date);
        req.setAttribute("path", file.getAbsolutePath());
        req.setAttribute("contextPath", req.getContextPath());
        req.setAttribute("fileList", fileList);
        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("explore.jsp");
        requestDispatcher.forward(req, resp);
    }
    private void download(File file, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());
        try (InputStream in = new FileInputStream(file); OutputStream out = resp.getOutputStream()){
            byte[] buffer = new byte[1048];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (DBService.connection == null) DBService.getConnection();
            Cookie[] cookies = req.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            resp.sendRedirect("/login");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
