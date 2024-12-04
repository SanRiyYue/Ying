package org.example.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.bean.SignInBean;
import org.example.bean.User;
import org.example.framework.GetMapping;
import org.example.framework.ModelAndView;
import org.example.framework.PostMapping;


public class UserController {
    private Map<String, User> userDatabase = new HashMap<>() {
        {
            List<User> users = List.of( //
                    new User("bob@example.com", "bob123", "Bob", "This is bob."), new User("tom@example.com", "tomcat", "Tom", "This is tom."));
            users.forEach(user -> {
                put(user.email, user);
            });
        }
    };

    @GetMapping("/signin")
    public ModelAndView signin() {
        return new ModelAndView("/signin.html");
    }

    @PostMapping("/signin")
    public ModelAndView doSignin(SignInBean bean, HttpServletResponse response, HttpSession session) throws IOException {
        User user = userDatabase.get(bean.email);
        if (user == null || !user.password.equals(bean.password)) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"error\":\"Bad email or password\"}");
            out.flush();
        } else {
            session.setAttribute("user", user);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"result\":true}");
            out.flush();
        }
        return null;
    }

    @GetMapping("/signout")
    public ModelAndView signout(HttpSession session) {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/user/profile")
    public ModelAndView profile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("/profile.html", "user", user);
    }

}
