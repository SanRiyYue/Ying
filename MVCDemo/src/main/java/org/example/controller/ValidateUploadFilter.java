package org.example.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebFilter(urlPatterns = "/upload/")
public class ValidateUploadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //获取客户端传入的签名方法和签名:
        String digest = req.getHeader("Signature-Method");
        String signature = req.getHeader("Signature");
        if (digest == null || digest.isEmpty() || signature == null || signature.isEmpty()) {
            sendErrorPage(resp, "Missing signature");
            return;
        }

        //读取Request的Body并验证签名:
        MessageDigest md = getMessageDigest(digest);
        InputStream input = new DigestInputStream(req.getInputStream(), md);
        byte[] buffer = new byte[1024];
        for (;;) {
            int len = input.read(buffer);
            if (len == -1) {
                break;
            }
        }
        String actual = toHexString(md.digest());
        if (!actual.equals(signature)) {
            sendErrorPage(resp, "Invalid signature.");
            return;
        }
        //验证成功后继续处理:
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //将String 转换为 Hex string:
    private String toHexString(byte[] digest) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    //根据名称创建MessageDigest:
    private MessageDigest getMessageDigest(String digest) throws ServletException {
        try {
            return MessageDigest.getInstance(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException(e);
        }
    }


    //发送一个错误响应:
    private void sendErrorPage(HttpServletResponse resp, String missingSignature) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        PrintWriter out = resp.getWriter();
        out.write("<html><body><h1>");
        out.write(missingSignature);
        out.write("</h1></body></html>");
        out.flush();
    }
}
