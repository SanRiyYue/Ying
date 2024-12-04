package org.example.Servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(urlPatterns = "/")
public class AllServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("X-Powered-By", "JavaEE Servlet");

        String user = (String) req.getSession().getAttribute("username");

        String language = parseLanguageFromCookie(req);

        PrintWriter pw = resp.getWriter();

        if (Objects.equals(language, "en")) {
            pw.write("<h1>Welcome " + (user != null ? user : "Guest") + "</h1>");
            if (user == null) {
                pw.write("<p> <a href=\"signin\"> Sign In</a></p>");
            } else {
                pw.write("<p> <a href=\"/signout\"> Sign Out</a></p>");
            }
        } else {
            pw.write("<h1>欢迎 " + (user != null ? user : "客人") + "</h1>");
            if (user == null) {
                pw.write("<p> <a href=\"signin\"> 登录</a></p>");
            } else {
                pw.write("<p> <a href=\"/signout\"> 登出</a></p>");
            }
        }
        pw.flush();
    }

    //读取Cookie
    private String parseLanguageFromCookie(HttpServletRequest req) {
        //获取请求所附带的所有Cookie
        Cookie[] cookies = req.getCookies();
        //如果获取到Cookie:
        if (cookies != null) {
            //循环每个Cookie:
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("language")) {
                    return cookie.getValue();
                }
            }
        }
        //返回默认值
        return "en";
    }

}
