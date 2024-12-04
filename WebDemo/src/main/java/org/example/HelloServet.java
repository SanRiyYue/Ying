package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//WebServlet注解表示这是一个Servlet，并映射到地址/:
@WebServlet(urlPatterns = "/")
public class HelloServet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应类型
        response.setContentType("text/html");
        //获取输出流
        PrintWriter out = response.getWriter();


        //获取URL参数"name"
        String name = request.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "World";
        }
        //输出 “Hello, [name]!"
        out.write("<h1>Hello " + name + "!</h1>");

        //flush强制输出
        out.flush();
    }

}
