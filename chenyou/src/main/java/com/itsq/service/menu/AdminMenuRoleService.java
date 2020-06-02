package com.itsq.service.menu;

import com.itsq.common.bean.Response;
import com.itsq.pojo.entity.AdminMenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-08
 */
public interface AdminMenuRoleService extends IService<AdminMenuRole> {
    //初始化顶尖管理员的所有权限
    Response initManagerRole(int adminId);
    //查询管理员详情
    Response getManagerInfo(int id);
    //查询此人可以添加的权限有哪些
    Response listManagerRoles(int id);
    //添加权限
    Response saveManagerRoles(int adminId,int menuId);
    //查询此人拥有的权限
    Response listManagerAddRoles(int id);
    //删除权限
    Response removeManagerRoles(int adminId,int menuId,int parentId);
}
