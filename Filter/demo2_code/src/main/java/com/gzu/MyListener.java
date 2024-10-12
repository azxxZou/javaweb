package com.gzu;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setAttribute("totalVisitors", 0);
        sce.getServletContext().setAttribute("currentVisitors", 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        int totalVisitors = (int)se.getSession().getServletContext().getAttribute("totalVisitors");
        int currentVisitors = (int)se.getSession().getServletContext().getAttribute("currentVisitors");

        se.getSession().getServletContext().setAttribute("totalVisitors", totalVisitors + 1);
        se.getSession().getServletContext().setAttribute("currentVisitors", currentVisitors + 1);

        System.out.println("Total Visitors: " + totalVisitors);
        System.out.println("Current Visitors: " + currentVisitors);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int totalVisitors = (int)se.getSession().getServletContext().getAttribute("totalVisitors");
        int currentVisitors = (int)se.getSession().getServletContext().getAttribute("currentVisitors");

        se.getSession().getServletContext().setAttribute("totalVisitors", totalVisitors - 1);
        se.getSession().getServletContext().setAttribute("currentVisitors", currentVisitors - 1);

        System.out.println("Total Visitors: " + totalVisitors);
        System.out.println("Current Visitors: " + currentVisitors);
    }
}
