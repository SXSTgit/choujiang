package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.common.redis.RedisUtils;

import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.AddUserDto;
import com.itsq.pojo.dto.LoginRespDto;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.dto.PlayersDtoPage;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Players;
import com.itsq.pojo.entity.User;
import com.itsq.service.resources.PlayersService;
import com.itsq.token.AuthToken;
import com.itsq.token.CurrentUser;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.MD5;
import com.itsq.utils.RandomUtil;
import com.itsq.utils.SQSendMailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/players")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "players模块")
public class PlayersController extends BaseController {

    @Autowired
    private PlayersService playersService;

    @Resource
    private RedisUtils redisUtil;
    @PostMapping("login")
    @ApiOperation(value = "用户-登录", notes = "", httpMethod = "POST")
    public Response<LoginRespDto<Players>> login(@RequestBody PlayersDto playersDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){


        Players u = this.playersService.login(playersDto.getNumber(), playersDto.getPwd());
        String authToken = new AuthToken(u.getId(),u.getName()).token();
        return Response.success(new LoginRespDto<>(u,authToken, EnumTokenType.BEARER.getCode()));
    }

    @PostMapping("getCode")
    @ApiOperation(value = "获取验证码", notes = "", httpMethod = "POST")
    public Response getCode(@RequestBody PlayersDto playersDto){
        String code = RandomUtil.getRandom(6);
        String myEmailAccount = "1085432162@qq.com";
        String myEmailPassword = "bjzfrimcvbgdfggd";
        String myEmailSMTPHost = "smtp.qq.com";
        String[] toMailAccountList = new String[]{playersDto.getNumber()};
        SQSendMailUtil.sendMail(myEmailAccount, myEmailPassword, "", toMailAccountList, "注册", myEmailSMTPHost, "注册验证码", "亲爱的用户：您的本次操作的验证码为："+code+"为了您帐号的安全，千万不要告诉别人哦!");


        redisUtil.set(playersDto.getNumber(),60,code);
        return Response.success();
    }

    @PostMapping("register")
    @ApiOperation(value = "用户-注册", notes = "", httpMethod = "POST")
    public Response<Players> register(@RequestBody PlayersDto playersDto){

        if(playersDto.getCode() ==null){
            return Response.fail("请输入验证码!");
        }


        String code2=(String)redisUtil.get(playersDto.getNumber());
        if(!playersDto.getCode().equals(code2)){
            return Response.fail("验证码错误!");
        }

        Players players = BeanUtils.copyProperties(playersDto, Players.class);


        Players players1 = this.playersService.addPlayers(players);
        return Response.success(players1);
    }


    @PostMapping("updatePlayers")
    @ApiOperation(value = "用户-忘记密码", notes = "", httpMethod = "POST")
    public Response updatePlayers(@RequestBody PlayersDto playersDto){
        if(playersDto.getCode() ==null){
            return Response.fail("请输入验证码!");
        }
        String code2=(String)redisUtil.get(playersDto.getNumber());
        if(!playersDto.getCode().equals(code2)){
            return Response.fail("验证码错误!");
        }
       this.playersService.updatePlayers(playersDto.getNumber(),playersDto.getPwd());
        return Response.success();
    }

    @PostMapping("updatePlayersById")
    @ApiOperation(value = "用户-修改信息", notes = "", httpMethod = "POST")
    public Response updatePlayersById(@RequestBody Players players){
        this.playersService.updatePlayersBuId(players);
        return Response.success();
    }

    @PostMapping("selectPlayersPage")
    @ApiOperation(value = "会员-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> selectPlayersPage(@RequestBody PlayersDtoPage playersDtoPage){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( playersService.selectPlayersPage(playersDtoPage));
    }

    @PostMapping("selectPlayerArms")
    @ApiOperation(value = "会员-背包", notes = "", httpMethod = "POST")
    public Response<Players> selectPlayerArms(@RequestBody PlayersDto playersDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( playersService.selectPlayerArms(playersDto));
    }

}

