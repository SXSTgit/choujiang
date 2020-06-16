package com.itsq.utils.ip;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "LogoutServlet",urlPatterns = "/logoutServlet")
public class LogoutServlet extends HttpServlet {

    private HttpSession session;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从域中获取一个session，设置为false 如果域中存在一个session，则直接获取，如果不存在，则返回一个空的session
        session = request.getSession(false);
        if (session != null){
            //使session失效
            session.invalidate();
            //失效后，需要进行的操作，List链表中需要减去，用到了Session域监听器
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
