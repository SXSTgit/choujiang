package com.itsq.controller.menu;


import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.service.menu.AdminMenuRoleService;
import com.itsq.token.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunqi
 * @since 2020-03-25
 */
@RestController
@CrossOrigin
@Api(tags = "权限管理模块")
@RequestMapping("/adminMenuRole")
@AllArgsConstructor
public class AdminMenuRoleController extends BaseController {

    private AdminMenuRoleService adminMenuRoleService;

    @PostMapping(value = "initAdminRole")
    @ApiOperation(value = "管理员管理-加载用户所有权限", notes = "", httpMethod = "POST")
    public Response initAdminRole(int adminId) {
        return adminMenuRoleService.initManagerRole(adminId);
    }

    @PostMapping(value = "listAdminRoles")
    @ApiOperation(value = "管理员管理-该管理员可以添加的权限", notes = "", httpMethod = "POST")
    public Response listAdminRoles(int adminId) {
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return adminMenuRoleService.listManagerRoles(adminId);
    }

    @PostMapping(value = "saveAdminRoles")
    @ApiOperation(value = "管理员管理-添加权限", notes = "", httpMethod = "POST")
    public Response saveAdminRoles(int adminId, int menuId) {
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return adminMenuRoleService.saveManagerRoles(adminId,menuId);
    }

    @PostMapping(value = "listAdminAddRoles")
    @ApiOperation(value = "管理员管理-查看已经拥有的权限", notes = "", httpMethod = "POST")
    public Response listAdminAddRoles(int adminId) {
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return adminMenuRoleService.listManagerAddRoles(adminId);
    }

    @PostMapping(value = "removeAdminRoles")
    @ApiOperation(value = "管理员管理-删除权限", notes = "", httpMethod = "POST")
    public Response removeAdminRoles(int adminId, int menuId,int parentId) {
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return adminMenuRoleService.removeManagerRoles(adminId,menuId,parentId);
    }
}

