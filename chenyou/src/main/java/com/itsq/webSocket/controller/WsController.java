package com.itsq.webSocket.controller;

import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.itsq.service.resources.PlayersService;
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
        playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto);
        pageParametersDto.setPageNum(1);
        pageParametersDto.setPageSize(1);
        pageParametersDto.setOrderByField("2");

        int playerCount = playersService.selectPlayerCount();
        int boxCount =   playerBoxArmsService.selectUpCount(0);
        int uPCount =    playerBoxArmsService.selectUpCount(1);
        Map<String ,Object > map=new HashMap<>();
        map.put("login", RandomUtil.getRandom(2));
        map.put("playerCount",playerCount);
        map.put("boxCount",boxCount);
        map.put("uPCount",uPCount);
        map.put("lunbo", playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto).toString());
        map.put("zuijia",  playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto).toString());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + map.toString());
    }
}
