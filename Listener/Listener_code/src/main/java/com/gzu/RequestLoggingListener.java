package com.gzu;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@WebListener
public class RequestLoggingListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 记录请求开始时间
        sre.getServletRequest().setAttribute("startTime", System.currentTimeMillis());

        // 获取请求的相关信息
        ServletRequest request = sre.getServletRequest();
        String clientIp = request.getRemoteAddr();
        String method = request instanceof HttpServletRequest ? ((HttpServletRequest) request).getMethod() : "Unknown";
        String requestUri = request instanceof HttpServletRequest ? ((HttpServletRequest) request).getRequestURI() : "Unknown";
        String queryString = request.getParameterMap().isEmpty() ? "" : ((HttpServletRequest) request).getQueryString();
        String userAgent = request instanceof HttpServletRequest ? ((HttpServletRequest) request).getHeader("User-Agent") : "Unknown";

        // 打印请求的初始信息到控制台
        System.out.println("Request Initialized at " + new Date() + "\n" +
                " | Client IP: " + clientIp + "\n" +
                " | Method: " + method + "\n" +
                " | URI: " + requestUri + "\n" +
                " | Query String: " + queryString + "\n" +
                " | User-Agent: " + userAgent);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 获取结束时间和处理时间
        long startTime = (long) sre.getServletRequest().getAttribute("startTime");
        long processingTime = System.currentTimeMillis() - startTime;

        // 打印请求结束时间和处理时间到控制台
        System.out.println("Request Completed at " + new Date() +
                " | Processing Time: " + processingTime + " ms");
    }
}