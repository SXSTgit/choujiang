package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.FindPageManagerParmeters;
import com.itsq.pojo.dto.MemberPageDto;
import com.itsq.pojo.entity.Member;
import com.itsq.pojo.entity.User;
import com.itsq.service.resources.*;
import com.itsq.token.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("getCount")
    @ApiOperation(value = "首页数据", notes = "", httpMethod = "POST")
    public Response<Map<String ,Integer >> findAllUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
       /* CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/

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
        }
        int playerCount = playersService.selectPlayerCount();
        int boxCount =   playerBoxArmsService.selectUpCount(0);
        int uPCount =    playerBoxArmsService.selectUpCount(1);
        Map<String ,Integer > map=new HashMap<>();
        map.put("login",Integer.valueOf(count+""));
       map.put("playerCount",playerCount);
        map.put("boxCount",boxCount);
        map.put("uPCount",uPCount);
        return Response.success(map);
    }

}

