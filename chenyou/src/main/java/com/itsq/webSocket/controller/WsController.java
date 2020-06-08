package com.itsq.webSocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.common.redis.RedisUtils;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.itsq.service.resources.PlayersService;
import com.itsq.utils.PagesUtil;
import com.itsq.utils.RandomUtil;
import com.itsq.webSocket.model.RequestMessage;
import com.itsq.webSocket.model.ResponseMessage;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * WsController
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/2/28
 */
@RestController
@AllArgsConstructor
@CrossOrigin
public class WsController extends BaseController {

    private final SimpMessagingTemplate messagingTemplate;

    @Resource
    private RedisUtils redisUtil;
    @Autowired
    private PlayersService playersService ;
    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;



    @Autowired
    public WsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    @Scheduled(fixedRate = 1000*60*60*2)
    public void getCount() {
        Integer i=Integer.valueOf(redisUtil.get("count")+"");
        if(i>0){
            redisUtil.set("count",i-1+"");
        }
    };

    /**
     * 定时推送消息
     */
    @Scheduled(fixedRate = 1000)
    public void callback() {
      /*  // 发现消息
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
        Object count=session.getServletContext().getAttribute("count");

        if(count==null){
            count=0;
        }*/

        PageParametersDto pageParametersDto = new PageParametersDto();
        pageParametersDto.setPageNum(1);
        pageParametersDto.setPageSize(20);
        pageParametersDto.setOrderByField("1");
        PagesUtil<PlayerBoxArms> playerBoxArmsPagesUtil = playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto);
        pageParametersDto.setPageNum(1);

        pageParametersDto.setPageSize(1);
        pageParametersDto.setOrderByField("2");
        PagesUtil<PlayerBoxArms> playerBoxArmsPagesUtil1 = playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto);
        int playerCount = playersService.selectPlayerCount();
        int boxCount =   playerBoxArmsService.selectUpCount(0);
        int uPCount =    playerBoxArmsService.selectUpCount(1);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("login", redisUtil.get("count"));
        jsonObject.put("playerCount",playerCount);
        jsonObject.put("boxCount",boxCount);
        jsonObject.put("uPCount",uPCount);
        jsonObject.put("lunbo", playerBoxArmsPagesUtil);
        jsonObject.put("zuijia",  playerBoxArmsPagesUtil1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback",  jsonObject.toString());
    }
}
