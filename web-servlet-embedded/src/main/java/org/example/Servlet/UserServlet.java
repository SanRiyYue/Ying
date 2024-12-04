package org.example.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //假装从数据库中读取
        School school = new School("No.1 Middle School", "101 South Street");
        User user = new User(123, "Bob", school);
        //放入Request中:
        req.setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/user.jsp").forward(req, resp);
    }
}
