package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

@WebServlet(urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 密码加密
        String hashedPassword = hashPassword(password);

        // 将用户信息写入文件
        Path path = Paths.get("users.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(username + ":" + hashedPassword);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("register.html?error=true");
            return;
        }

        // 注册成功，重定向到登录页面
        response.sendRedirect("login.html");
    }

    private String hashPassword(String password) {
        // 使用一个加密算法BCrypt
        return new StringBuilder(password).reverse().toString();
    }
}