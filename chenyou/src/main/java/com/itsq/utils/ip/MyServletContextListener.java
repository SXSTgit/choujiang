package com.itsq.utils.ip;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener()
public class MyServletContextListener implements ServletContextListener{
    private ServletContext sc;
    @Override
    //Application被初始化的时候创建
    public void contextInitialized(ServletContextEvent sce) {
        //创建一个Map，key为IP，value为该IP上所发出的会话的对象
        Map<String,List<HttpSession>> map = new HashMap<>();
        sc = sce.getServletContext();
        //将map放到全局域中
        sc.setAttribute("map",map);
    }
}
