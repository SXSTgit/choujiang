package com.itsq.utils.http;

import com.itsq.common.redis.RedisUtils;
import com.itsq.service.resources.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnLineCount implements HttpSessionListener {
    @Resource
    private RedisUtils redisUtil;
    @Autowired
    private PlayersService playersService ;

    public int count=0;//记录session的数量
    //监听session的创建,synchronized 防并发bug
    public synchronized void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("【HttpSessionListener监听器】count++  增加");
        int playerCount = playersService.selectPlayerCount();
        if(playerCount>=count){
            count++;
        }

        redisUtil.set("count",count+"");
        arg0.getSession().getServletContext().setAttribute("count", count);
 
    }
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent arg0) {//监听session的撤销
        System.out.println("【HttpSessionListener监听器】count--  减少");
        if(count>=0){
            count--;
        }

        redisUtil.set("count",count+"");
        arg0.getSession().getServletContext().setAttribute("count", count);
    }
}
