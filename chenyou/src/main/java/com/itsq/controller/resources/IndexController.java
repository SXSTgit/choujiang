package com.itsq.controller.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
public class IndexController {
    @RequestMapping("/count")
    @ResponseBody
    public String number(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try{  //把sessionId记录在浏览器
            Cookie c = new Cookie("JSESSIONID", URLEncoder.encode(httpServletRequest.getSession().getId(), "utf-8"));
            c.setPath("/");
            //先设置cookie有效期为2天，不用担心，session不会保存2天
            c.setMaxAge( 48*60 * 60);
            httpServletResponse.addCookie(c);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(14);
        Object count=session.getServletContext().getAttribute("count");
        return "count : "+count;
    }

}
