package org.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainServlet extends HttpServlet {
    public SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a");



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        File file;
        if (path == null) {
            file = new File("D:\\My");
        }
        else {
            file = new File(path.replace("%20", " "));
        }
        if (file.isDirectory()) {
            getIsDirectory(file, req, resp);
        }
        else {
            download(file, resp);
        }
    }

    private void getIsDirectory(File file, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> elements = Arrays.asList(file.listFiles());
        String date = format.format(new Date());
        req.setAttribute("elements", elements);
        req.setAttribute("date", date);
        req.setAttribute("format", format);
        req.setAttribute("contextPath", req.getContextPath());
        req.setAttribute("path", file.getAbsolutePath());
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
        super.doPost(req, resp);
    }
}
