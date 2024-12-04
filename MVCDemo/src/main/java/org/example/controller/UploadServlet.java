package org.example.controller;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/upload/file")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //读取Request Body:
        InputStream input = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for(;;) {
            int len = input.read(buffer);
            if (len == -1) {
                break;
            }
            baos.write(buffer, 0, len);
        }
        // TODO:写入文件:
        //显示上传结果:
        String uploadedText = baos.toString(StandardCharsets.UTF_8);
        PrintWriter out = response.getWriter();
        out.write("<h1>Uploaded File: </h1>");
        out.write("<pre><code>");
        out.write(uploadedText);
        out.write("</code></pre>");
        out.flush();
    }
}
