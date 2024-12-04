package org.example.Servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet(urlPatterns = "/pref")
public class LanguageServlet extends HttpServlet {
    private static final Set<String> LANGUAGES = Set.of("en", "zh");

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String language = req.getParameter("language");
        if (LANGUAGES.contains(language)) {
            //创建一个新的Cookie:
            Cookie cookie = new Cookie("language", language);
            //该Cookie生效的路径范围
            cookie.setPath("/");
            //该Cookie的有效期
            cookie.setMaxAge(8640000);
            //将该cookie添加到响应:
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/");
    }
}
