package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@WebServlet(name = "comment", value = "/comment")
public class CommentServlet extends HttpServlet {

    //记录评论词汇的IP
    private HashSet<String> hashSet = new HashSet<>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String comment = (String) request.getAttribute("comment");
        if (message.equals(comment)) {
            System.out.println("没有敏感词汇...");
            request.setAttribute("name", "good boy: ");
        } else {
            String localAddr = request.getRemoteAddr();
            System.out.println(localAddr);
            hashSet.add(localAddr);

            request.setAttribute("name", "bad boy: ");
        }
        request.getRequestDispatcher("/comment.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
