package com.itsq.controller.resources;


import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.AddUserDto;
import com.itsq.pojo.dto.LoginRespDto;
import com.itsq.pojo.entity.User;
import com.itsq.service.resources.UserService;
import com.itsq.token.AuthToken;
import com.itsq.token.CurrentUser;
import com.itsq.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "用户模块")
public class UserController extends BaseController {

    private UserService userService;

    @PostMapping("login")
    @ApiOperation(value = "用户-登录", notes = "", httpMethod = "POST")
    public Response<LoginRespDto<User>> login(String userName, String password){
        User u = this.userService.login(userName, MD5.getMd5(password,32));
        String authToken = new AuthToken(u.getId(),u.getUserName()).token();
        return Response.success(new LoginRespDto<>(u,authToken, EnumTokenType.BEARER.getCode()));
    }
    @PostMapping("register")
    @ApiOperation(value = "用户-注册", notes = "", httpMethod = "POST")
    public Response<User> register(@RequestBody AddUserDto addUserDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        User u= this.userService.register(addUserDto);
        return Response.success(u);
    }

    @PostMapping("findAllUser")
    @ApiOperation(value = "用户-QUANB", notes = "", httpMethod = "POST")
    public Response<List<User>> findAllUser(){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        List<User> pageUser = this.userService.getPageUser(null);
        return Response.success(pageUser);
    }
}

