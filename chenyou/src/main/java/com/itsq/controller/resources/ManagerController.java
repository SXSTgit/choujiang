package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.*;
import com.itsq.pojo.entity.Manager;
import com.itsq.service.resources.ManagerService;
import com.itsq.token.AuthToken;
import com.itsq.token.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/manager")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "管理员模块")
public class ManagerController extends BaseController {

    private ManagerService managerService;

    @PostMapping("login")
    @ApiOperation(value = "管理员-登录", notes = "", httpMethod = "POST")
    public Response<LoginRespDto<Manager>> login(@RequestBody ManagerLoginReqDto dto){
        Manager manager = managerService.login(dto);
        String token =new AuthToken(manager.getId(),manager.getUserName()).token();
        if(manager!=null){
            return Response.success(new LoginRespDto(manager,token, EnumTokenType.MANAGER.getCode()));
        }else{
            return Response.fail(ErrorEnum.USER_INFO_ERROR);
        }
    }

    @PostMapping("pageAdmins")
    @ApiOperation(value = "管理员-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> login(@RequestBody FindPageManagerParmeters dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return Response.success( managerService.pageAdmins(dto));
    }

    @PostMapping("findManagerById")
    @ApiOperation(value = "管理员-查询管理员详细", notes = "", httpMethod = "POST")
    public Response<Optional<Manager>> findManagerById(int id){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        Optional<Manager> managerById = managerService.findManagerById(id);
        return Response.success(managerById);
    }

    @PostMapping("updatePassWord")
    @ApiOperation(value = "管理员-1.初始化密码 2.修改密码", notes = "", httpMethod = "POST")
    public Response updatePassWord(@RequestBody updatePassWordDto dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        managerService.updatePassWord(dto);
        return Response.success();
    }

    @PostMapping("updateManagerInfo")
    @ApiOperation(value = "管理员-修改管理员详情", notes = "", httpMethod = "POST")
    public Response updateManagerInfo(@RequestBody Manager manager){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        managerService.updateManagerInfo(manager);
        return Response.success();
    }

    @PostMapping("saveManagerInfo")
    @ApiOperation(value = "管理员-新增管理员", notes = "", httpMethod = "POST")
    public Response saveManagerInfo(@RequestBody AddManagerReqDto addManagerReqDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        managerService.saveManagerInfo(addManagerReqDto);
        return Response.success();
    }
}

