package com.gzu;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/session_demo")
public class Session extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("classname", "java小班");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello session\n");
    }
}
