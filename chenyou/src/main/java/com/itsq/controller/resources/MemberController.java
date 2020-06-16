package com.itsq.controller.resources;


import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.service.resources.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/number")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "首页数据模块")
public class MemberController extends BaseController {

    @Autowired
    private PlayersService playersService ;
    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;
    @Autowired
    private  RechargeRecordService rechargeRecordService;
    @PostMapping("getCount")
    @ApiOperation(value = "首页数据", notes = "", httpMethod = "POST")
    public Response<Map<String ,Integer >> findAllUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){



        int playerCount = playersService.selectPlayerCount();
        int boxCount =   playerBoxArmsService.selectUpCount(0);
        int uPCount =    playerBoxArmsService.selectUpCount(1);
        Map<String ,Integer > map=new HashMap<>();
        map.put("login",Integer.valueOf(3+""));
       map.put("playerCount",playerCount);
        map.put("boxCount",boxCount);
        map.put("uPCount",uPCount);
        return Response.success(map);
    }


    @PostMapping("findAll")
    @ApiOperation(value = "后台首页数据", notes = "", httpMethod = "POST")
    public Response<Map<String ,Object >> findAll(){
       /* CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");
        String currentDate =   dateFormat.format( new Date() );


        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        String dBeforeDate =   dateFormat.format( dBefore );

        int playerCount = playersService.selectTodayAdd(currentDate);
        int playerCountz = playersService.selectTodayAdd(dBeforeDate);

        int boxCount =playerBoxArmsService.getUpCountData(currentDate,0);
        int boxCount2 =playerBoxArmsService.getUpCountData(currentDate,1);
        BigDecimal todayAllPrice = playerBoxArmsService.getTodayAllPrice(currentDate);
        int countRechargeRecord = rechargeRecordService.getCountRechargeRecord(currentDate);


        Map<String ,Object > map=new HashMap<>();
        map.put("todayzhuce",playerCount);
        map.put("tomorrowzhuce",playerCountz);
        map.put("box",boxCount);
        map.put("upArms",boxCount2);
        map.put("allmoney",todayAllPrice);
        map.put("allRechargeRecord",countRechargeRecord);
        return Response.success(map);
    }

}

