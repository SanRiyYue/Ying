package org.example.controller;

import org.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class QuickController {

    @RequestMapping("/quick")
    public String quickMethod() {
        System.out.println("Quick Method running...");
        return "index.jsp";
    }

    @RequestMapping("/quick11")
    public void quickMethod11(String[] strs) throws IOException {
        System.out.println(Arrays.asList(strs));
    }

    @RequestMapping("/quick10")
    public void quickMethod10(User user) throws IOException {
        System.out.println(user);
    }

    public void quickMethod9(String username, int age) throws IOException {
        System.out.println(username);
        System.out.println(age);
    }
}
