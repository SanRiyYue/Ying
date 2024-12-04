package org.example.framework;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 用以处理静态文件
 */

@WebServlet(urlPatterns = {"/favicon.ico", "/static/*"})
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext context = getServletContext();
        //RequestURI包含ContextPath，需去掉
        String utlPath = req.getRequestURI().substring(context.getContextPath().length());
        //获取真实文件路径
        String filePath = context.getRealPath(utlPath);
        if (filePath == null) {
            //无法获取到路径
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Path path = Paths.get(filePath);
        if (!path.toFile().isFile()) {
            //文件不存在
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //根据文件名猜测Content-Type:
        String mime = Files.probeContentType(path);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        resp.setContentType(mime);
        //读取文件并写入Response:
        OutputStream output = resp.getOutputStream();
        try (InputStream input = new BufferedInputStream(new FileInputStream(filePath))) {
            input.transferTo(output);
        }
        output.flush();
    }
}
