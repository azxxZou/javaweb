package com.gzu;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static jdk.jfr.internal.JVM.isExcluded;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    private static final String[] exclude_path = {"Login", "Register", "login.html", "register.html"};

    private boolean isExcluded(String requestPath) {
        for (String path : exclude_path) {
            if (requestPath.contains(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestPath = req.getRequestURI();                                   // 获取请求路径

        if (isExcluded(requestPath)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession(false);                         // 检查用户是否登录
            if (session != null && session.getAttribute("user") != null) {
                chain.doFilter(request, response);                                  // 用户登陆后进行下一步操作
            } else {
                System.out.println(req.getContextPath());
                res.sendRedirect("login.html");                                 // 用户未登录则重定向登录界面
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
