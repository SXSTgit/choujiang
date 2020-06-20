package com.itsq.service.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.bean.Response;
import com.itsq.mapper.ManagerMapper;
import com.itsq.mapper.SysMenuMapper;
import com.itsq.pojo.entity.AdminMenuRole;
import com.itsq.mapper.AdminMenuRoleMapper;
import com.itsq.pojo.entity.Manager;
import com.itsq.pojo.entity.SysMenu;
import com.itsq.service.menu.AdminMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.CastUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-08
 */
@Service
@AllArgsConstructor
@Slf4j
public class AdminMenuRoleServiceImpl extends ServiceImpl<AdminMenuRoleMapper, AdminMenuRole> implements AdminMenuRoleService {
    private SysMenuMapper sysMenuMapper;
    private AdminMenuRoleMapper adminMenuRoleMapper;
    private ManagerMapper managerMapper;

    @Override
    public Response initManagerRole(int adminId) {
        //查询sys_menu 表中的所有Id
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("status",1);
            List<SysMenu> sysMenuList = sysMenuMapper.selectList(queryWrapper);
            AdminMenuRole adminMenuRole = new AdminMenuRole();
            adminMenuRole.setAdminId(adminId);
            adminMenuRole.setState(0);
            for(SysMenu sysMenu:sysMenuList){
                adminMenuRole.setSysMenuId(CastUtil.castInt(sysMenu.getMenuId()));
                //判断是否存在 1.state=0 2.sys_menu_id
                QueryWrapper<AdminMenuRole> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("adminId",adminMenuRole.getAdminId());
                queryWrapper1.eq("state",0);
                queryWrapper1.eq("sys_menu_id",adminMenuRole.getSysMenuId());
                AdminMenuRole adminMenuRole1 = adminMenuRoleMapper.selectOne(queryWrapper1);
                if(adminMenuRole1!=null){
                    log.info("已经存在啦");
                }else{
                    adminMenuRoleMapper.insert(adminMenuRole);
                }
            }
            return Response.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success("失败"+e);
        }
    }

    @Override
    public Response getManagerInfo(int id) {
        Manager manager = managerMapper.selectById(id);
        return Response.success(manager);
    }

    @Override
    public Response listManagerRoles(int id) {
        //找出总共权限有哪些
        List<Integer> list1 = managerMapper.listRoles();
        //查出此人拥有哪些权限
        List<Integer> list = managerMapper.listMyRoles(id);
        //最终可以添加的权限
        List<Integer> list3 = managerMapper.listRoles();
        //进行对比
        for(Integer integer:list1){
            for(Integer integer1:list){
                if(integer==integer1){
                    list3.remove(integer);
                }
            }
        }
        if(list3.size()==0){
            return Response.success("无可增加的权限");
        }
        //根据ID查询全部
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("menu_id",list3);
        List list2 = sysMenuMapper.selectList(queryWrapper);
        return Response.success(list2);
    }

    @Transactional
    @Override
    public Response saveManagerRoles(int adminId, int menuId) {
        AdminMenuRole adminMenuRole = new AdminMenuRole();
        adminMenuRole.setSysMenuId(menuId);
        adminMenuRole.setAdminId(adminId);
        adminMenuRoleMapper.insert(adminMenuRole);
        //还得再添加父类的那个
        //思路根据menuId找到parent_id 再次进行新增
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("menu_Id",menuId);
        SysMenu sysMenu = sysMenuMapper.selectOne(queryWrapper);
        Long parentId = sysMenu.getParentId();
        adminMenuRole.setSysMenuId(CastUtil.castInt(parentId));
        adminMenuRoleMapper.insert(adminMenuRole);
        return Response.success();
    }

    @Override
    public Response listManagerAddRoles(int id) {
        //查出此人拥有哪些权限
        List<Integer> list = managerMapper.listMyRoles(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("menu_id",list);
        List list2 = null;
        try {
            list2 = sysMenuMapper.selectList(queryWrapper);
            return Response.success(list2);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("异常或可能没有该权限");
        }

    }

    @Transactional
    @Override
    public Response removeManagerRoles(int adminId, int menuId,int parentId) {
        //根据管理员Id 以及权限ID删除 此人的权限
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("adminId",adminId);
        queryWrapper.eq("sys_menu_id",menuId);
        AdminMenuRole adminMenuRole = adminMenuRoleMapper.selectOne(queryWrapper);//本身
        //再找到父
//        QueryWrapper queryWrapper1 = new QueryWrapper();
//        queryWrapper1.eq("menu_Id",menuId);
//        SysMenu sysMenu = sysMenuMapper.selectOne(queryWrapper1);
//        queryWrapper.eq("sys_menu_id",sysMenu.getMenuId());//父级别的ID
//        AdminMenuRole adminMenuRole1 = adminMenuRoleMapper.selectOne(queryWrapper);//父
        //逻辑删除
        try {
            adminMenuRoleMapper.deleteById(adminMenuRole.getId());
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("adminId",adminId);
            queryWrapper1.eq("sys_menu_id",parentId);
            AdminMenuRole adminMenuRole1 = adminMenuRoleMapper.selectOne(queryWrapper1);
            adminMenuRoleMapper.deleteById(adminMenuRole1.getId());
            return Response.success("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("删除异常或可能没有该权限");
        }
    }
}
