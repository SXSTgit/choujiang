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
    public Response<Map<String ,Integer >> findAllUser(){
       /* CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        int playerCount = playersService.selectPlayerCount();

        int boxCount =   playerBoxArmsService.selectUpCount(0);
        int uPCount =    playerBoxArmsService.selectUpCount(1);
        Map<String ,Integer > map=new HashMap<>();
        map.put("login",6);
       map.put("playerCount",playerCount);
        map.put("boxCount",boxCount);
        map.put("uPCount",uPCount);
        return Response.success(map);
    }

}

