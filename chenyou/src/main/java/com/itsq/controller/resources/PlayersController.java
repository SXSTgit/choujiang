package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.common.redis.RedisUtils;

import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.LoginRespDto;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.dto.PlayersDtoPage;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.pojo.entity.Players;
import com.itsq.service.resources.OperationRecordService;
import com.itsq.service.resources.PlayersService;
import com.itsq.token.AuthToken;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.RandomUtil;
import com.itsq.utils.SQSendMailUtil;
import com.itsq.utils.http.Client;
import io.swagger.annotations.Api;
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
    @Autowired
    private OperationRecordService operationRecordService;
    @Resource
    private RedisUtils redisUtil;
    @Autowired
    private Client client;


    @PostMapping("login")
    @ApiOperation(value = "用户-登录", notes = "", httpMethod = "POST")
    public Response<LoginRespDto<Players>> login(@RequestBody PlayersDto playersDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Players u = this.playersService.login(playersDto.getNumber(), playersDto.getPwd());

        operationRecordService.addOperationRecord(new OperationRecord(u.getId(),"用户登录","登陆成功","/players/login",1,client.getAddress(httpServletRequest.getRemoteAddr())));

        String authToken = new AuthToken(u.getId(), u.getName()).token();
        return Response.success(new LoginRespDto<>(u, authToken, EnumTokenType.BEARER.getCode()));
    }

    @PostMapping("steamLogin")
    @ApiOperation(value = "用户-steam登录", notes = "", httpMethod = "POST")
    public Response<LoginRespDto<Players>> loginBySteam(@RequestBody PlayersDto playersDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Players u = this.playersService.loginBySteam(playersDto);
        operationRecordService.addOperationRecord(new OperationRecord(u.getId(),"用户steam登录","登陆成功","/players/steamLogin",1,client.getAddress(httpServletRequest.getRemoteAddr())));
        String authToken = new AuthToken(u.getId(), u.getName()).token();
        return Response.success(new LoginRespDto<>(u, authToken, EnumTokenType.BEARER.getCode()));
    }

    @PostMapping("getCode")
    @ApiOperation(value = "获取验证码", notes = "", httpMethod = "POST")
    public Response getCode(@RequestBody PlayersDto playersDto) {
        String code = RandomUtil.getRandom(6);
        String myEmailAccount = "register@boxgo.cc";
        String myEmailPassword = "duobao@2020";
        String myEmailSMTPHost = "smtpout.asia.secureserver.net";
        String[] toMailAccountList = new String[]{playersDto.getNumber()};
        String smtpPort="465";
        SQSendMailUtil.sendMail(myEmailAccount, myEmailPassword, "", toMailAccountList, "注册", myEmailSMTPHost, "注册验证码", "亲爱的用户：您的本次操作的验证码为：" + code + "为了您帐号的安全，千万不要告诉别人哦!",smtpPort);


        redisUtil.set(playersDto.getNumber(), 60, code);
        return Response.success();
    }

    @PostMapping("register")
    @ApiOperation(value = "用户-注册", notes = "", httpMethod = "POST")
    public Response<Players> register(@RequestBody PlayersDto playersDto) {

        if (playersDto.getCode() == null) {
            return Response.fail("请输入验证码!");
        }


        String code2 = (String) redisUtil.get(playersDto.getNumber());
        if (!playersDto.getCode().equals(code2)) {
            return Response.fail("验证码错误!");
        }

        Players players = BeanUtils.copyProperties(playersDto, Players.class);


        Players players1 = this.playersService.addPlayers(players);
        operationRecordService.addOperationRecord(new OperationRecord(players1.getId(),"用户注册","注册成功","/players/register",1,client.getAddress(request.getRemoteAddr())));

        return Response.success(players1);
    }


    @PostMapping("updatePlayers")
    @ApiOperation(value = "用户-忘记密码", notes = "", httpMethod = "POST")
    public Response updatePlayers(@RequestBody PlayersDto playersDto,HttpServletRequest request) {
        if (playersDto.getCode() == null) {
            return Response.fail("请输入验证码!");
        }
        String code2 = (String) redisUtil.get(playersDto.getNumber());
        if (!playersDto.getCode().equals(code2)) {
            return Response.fail("验证码错误!");
        }
        this.playersService.updatePlayers(playersDto.getNumber(), playersDto.getPwd());
        operationRecordService.addOperationRecord(new OperationRecord(playersDto.getId(),"用户修改密码","修改成功","/players/updatePlayers",1,client.getAddress(request.getRemoteAddr())));

        return Response.success();
    }

    @PostMapping("updatePlayersById")
    @ApiOperation(value = "用户-修改信息", notes = "", httpMethod = "POST")
    public Response updatePlayersById(@RequestBody Players players,HttpServletRequest request) {
        this.playersService.updatePlayersBuId(players);
        operationRecordService.addOperationRecord(new OperationRecord(players.getId(),"用户修改个人信息","修改成功","/players/updatePlayersById",1,client.getAddress(request.getRemoteAddr())));

        return Response.success();
    }

    @PostMapping("updateMoneyById")
    @ApiOperation(value = "管理员-修改用余额", notes = "", httpMethod = "POST")
    public Response updateMoneyById(@RequestBody PlayersDto playersDto,HttpServletRequest request) {
        this.playersService.updateMoneyById(playersDto);
        if(playersDto.getUsStatus()==1){
            operationRecordService.addOperationRecord(new OperationRecord(playersDto.getManagrId(), "添加余额", "为用户"+playersDto.getId()+"加余额"+playersDto.getAmount(), "/players/updateMoneyById", 0, client.getAddress(request.getRemoteAddr())));
        }else if(playersDto.getUsStatus()==0){
            operationRecordService.addOperationRecord(new OperationRecord(playersDto.getManagrId(), "减余额", "为用户"+playersDto.getId()+"减余额"+playersDto.getAmount(), "/players/updateMoneyById", 0, client.getAddress(request.getRemoteAddr())));

        }
        return Response.success();
    }

    @PostMapping("selectPlayersPage")
    @ApiOperation(value = "会员-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> selectPlayersPage(@RequestBody PlayersDtoPage playersDtoPage) {
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success(playersService.selectPlayersPage(playersDtoPage));
    }

    @PostMapping("selectPlayerArms")
    @ApiOperation(value = "会员-背包", notes = "", httpMethod = "POST")
    public Response<Players> selectPlayerArms(@RequestBody PlayersDto playersDto) {
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success(playersService.selectPlayerArms(playersDto));
    }

}

