package org.example.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/user/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AuthFilter: check authentication");
        HttpServletRequest reqest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(reqest.getSession().getAttribute("user") == null) {
            System.out.println("AuthFilter: session does not exist");
            response.sendRedirect("/signin");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }  
}
