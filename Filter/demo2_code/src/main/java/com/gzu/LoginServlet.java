package com.gzu;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.*;

@WebServlet(urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求中的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = validateUser(username, password);

        if (isValidUser) {
            // 用户验证成功，设置session属性
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // 重定向到欢迎页面
            response.sendRedirect("welcome.html");
        } else {
            // 用户验证失败，重定向到登录页面并显示错误消息
            response.sendRedirect("login.html?error=true");
        }
    }

    private boolean validateUser(String username, String password) throws IOException {
        Path path = Paths.get("users.txt");
        String hashedPassword = hashPassword(password);

        return Files.lines(path).anyMatch(line -> line.startsWith(username + ":") && line.endsWith(hashedPassword));
    }

    private String hashPassword(String password) {
        // 使用一个加密算法BCrypt
        return new StringBuilder(password).reverse().toString();
    }
}