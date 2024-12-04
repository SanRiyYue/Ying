package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(servletNames = {"comment"}, initParams = {@WebInitParam(name = "sensitiveWord", value = "妈")})
public class CommentFilter implements Filter {

    private List<String> sensitiveWords = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        //得到敏感词汇
        String word = filterConfig.getInitParameter("sensitiveWord");
        //加入集合
        sensitiveWords.add(word);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置编码
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        //得到评论
        String message = servletRequest.getParameter("message");
        for (String sensitiveWord : sensitiveWords) {
            if (message.contains(sensitiveWord)) {

                message = message.replace(sensitiveWord, "**");
            }
        }

        servletRequest.setAttribute("comment", message);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
