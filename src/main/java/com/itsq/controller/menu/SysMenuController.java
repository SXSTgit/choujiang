package com.itsq.controller.menu;


import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.AddMenuOneDto;
import com.itsq.pojo.dto.AddMenuThreeDto;
import com.itsq.pojo.dto.AddMenuTwoDto;
import com.itsq.pojo.dto.UpdateMenuInfoDto;
import com.itsq.service.menu.SysMenuService;
import com.itsq.token.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author sunqi
 * @since 2020-03-23
 */
@RestController
@RequestMapping("/sysMenu")
@CrossOrigin
@Api(tags = "系统菜单获取接口服务")
@AllArgsConstructor
public class SysMenuController extends BaseController {

    private SysMenuService sysMenuService;

    @RequestMapping(value = "getallmenu",method = RequestMethod.POST)
    @ApiOperation(value = "获取菜单接口", notes = "根据source参数获取", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "source", value = "菜单的级别", required = true, dataType = "Integer")
    })
    public Response getAllMenu(Integer source, int adminId){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return sysMenuService.showAllMenu(source,adminId);
    }


    @RequestMapping(value = "menuLevel",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统分级查询菜单", notes = "", httpMethod = "POST")
    public Response menuLevel(){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return Response.success(sysMenuService.mapMenu());
    }

    @RequestMapping(value = "saveMenuOne",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-新增路由一级菜单", notes = "", httpMethod = "POST")
    public Response saveMenuOne(@RequestBody AddMenuOneDto addMenuOneDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.saveMenuOne(addMenuOneDto);
        return Response.success();
    }


    @RequestMapping(value = "saveMenuTwo",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-新增路由二级 页面", notes = "", httpMethod = "POST")
    public Response saveMenuTwo(@RequestBody AddMenuTwoDto addMenuTwoDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.saveMenuTwo(addMenuTwoDto);
        return Response.success();
    }


    @RequestMapping(value = "saveMenuThree",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-新增路由三级 按钮", notes = "", httpMethod = "POST")
    public Response saveMenuThree(@RequestBody AddMenuThreeDto addMenuThreeDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.saveMenuThree(addMenuThreeDto);
        return Response.success();
    }

    @RequestMapping(value = "updateMenuInfoByMenuId",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-修改菜单根据MenuId", notes = "", httpMethod = "POST")
    public Response updateMenuInfoByMenuId(@RequestBody UpdateMenuInfoDto updateMenuInfoDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.updateMenuInfo(updateMenuInfoDto);
        return Response.success();
    }

    @RequestMapping(value = "updateMenuStatus",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-修改菜单状态根据MenuId", notes = "", httpMethod = "POST")
    public Response updateMenuStatus(Long menuId,int status){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.updateMenuStatus(menuId,status);
        return Response.success();
    }

    @RequestMapping(value = "removeMenu",method = RequestMethod.POST)
    @ApiOperation(value = "后台管理系统-根据MenuID删除", notes = "", httpMethod = "POST")
    public Response removeMenu(Long menuId){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        sysMenuService.removeMenu(menuId);
        return Response.success();
    }

}

