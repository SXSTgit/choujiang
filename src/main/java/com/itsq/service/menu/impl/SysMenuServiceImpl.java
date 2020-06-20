package com.itsq.service.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.common.constant.APIException;
import com.itsq.mapper.AdminMenuRoleMapper;
import com.itsq.pojo.dto.AddMenuOneDto;
import com.itsq.pojo.dto.AddMenuThreeDto;
import com.itsq.pojo.dto.AddMenuTwoDto;
import com.itsq.pojo.dto.UpdateMenuInfoDto;
import com.itsq.pojo.entity.AdminMenuRole;
import com.itsq.pojo.entity.SysMenu;
import com.itsq.mapper.SysMenuMapper;
import com.itsq.service.menu.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.CastUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-08
 */
@Service
@AllArgsConstructor
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private SysMenuMapper sysMenuMapper;
    private AdminMenuRoleMapper adminMenuRoleMapper;

    @Override
    public Response showAllMenu(Integer source, int adminId) {
//        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("source",source);
//        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);//查询所有菜单
        QueryWrapper<AdminMenuRole> adminMenuRoleQueryWrapper = new QueryWrapper<>();
        adminMenuRoleQueryWrapper.eq("adminId",adminId);
        List<AdminMenuRole> adminMenuRoles = adminMenuRoleMapper.selectList(adminMenuRoleQueryWrapper);//当前登录人所拥有的权限
        List<SysMenu> sysMenuList = new ArrayList<>();
        for(AdminMenuRole adminMenuRole:adminMenuRoles){
            Integer sysMenuId = adminMenuRole.getSysMenuId();
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("menu_id",sysMenuId);
            //wrapper.notIn("status",-1);
            SysMenu sysMenu = sysMenuMapper.selectOne(wrapper);
            try {
                if(sysMenu.getStatus()!=-1){
                    sysMenuList.add(sysMenu);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Response.success(sysMenuList);
//        return Response.success(sysMenus);
    }

    /**
     *
     * @param
     * @return java.util.List<com.itsq.pojo.entity.SysMenu>
     * @author sq
     * @creed: 我就试试咱也不知道成不成
     * @date 2020/4/12 16:07
     */
    @Override
    public List<SysMenu> mapMenu() {
        Map<String,Object> map = new HashMap<>();
        //查询所有一级菜单 路由
        List<SysMenu> list1 = super.lambdaQuery().eq(SysMenu::getParentId, -1).eq(SysMenu::getType,1).list();
        //查询所有二级菜单 Index.html
        List<SysMenu> list2 = super.lambdaQuery().notIn(SysMenu::getParentId, -1).eq(SysMenu::getType,1).list();
        //查询所有三级级菜单 按钮 或者不该叫做三级这个说法有问题个人感觉
        List<SysMenu> list3 = super.lambdaQuery().eq(SysMenu::getType,2).list();
        //思路将一级菜单想为主导者，将主导者赋值次导者，次导者在寻其子级
        List<SysMenu> listAll = new ArrayList<>();
        for (SysMenu sysMenu : list1) {
            List<SysMenu> listTwo = new ArrayList<>();//临时存储二级
            listAll.add(sysMenu);
            //取出list1中的menu_id，对比二级里面的parent_id如何相等则是自己的子级别
            Long menuId = sysMenu.getMenuId();//一级的ID
            for (SysMenu menu : list2) {
                List<SysMenu> listThree = new ArrayList<>();//临时存储按钮
                Long parentId = menu.getParentId();//二级的父ID
                if(parentId==menuId){//相等则添加进新的集合中
                    Long menuId1 = menu.getMenuId();
                for (SysMenu sysMenu1 : list3) {
                    long l = CastUtil.castLong(sysMenu1.getParenttwoId());
                    if(menuId1==l){
                        listThree.add(sysMenu1);
                    }
                }
                if(listThree.size()>0){
                    menu.setMenuListThree(listThree);
                }
                    listTwo.add(menu);
                }
                sysMenu.setMenuListTwo(listTwo);//在此之前遍历三级
            }
        }
        return listAll;
    }


    @Override
    public void saveMenuOne(AddMenuOneDto addMenuOneDto) {
        SysMenu sysMenu = BeanUtils.copyProperties(addMenuOneDto, SysMenu.class);
        sysMenu.setParentId(CastUtil.castLong(-1));
        sysMenu.setType(1);
        sysMenu.setStatus(1);
        sysMenu.setSource(1);
        super.baseMapper.insert(sysMenu);
    }

    @Override
    public void saveMenuTwo(AddMenuTwoDto addMenuTwoDto) {
        SysMenu sysMenu = BeanUtils.copyProperties(addMenuTwoDto, SysMenu.class);
        sysMenu.setType(1);
        sysMenu.setStatus(1);
        sysMenu.setSource(1);
        super.baseMapper.insert(sysMenu);
    }

    @Override
    public void saveMenuThree(AddMenuThreeDto addMenuThreeDto) {
        SysMenu sysMenu = BeanUtils.copyProperties(addMenuThreeDto, SysMenu.class);
        sysMenu.setType(2);
        sysMenu.setStatus(1);
        sysMenu.setSource(1);
        sysMenu.setParenttwoId(addMenuThreeDto.getParenttwoId());
        super.baseMapper.insert(sysMenu);
    }

    @Override
    public void updateMenuInfo(UpdateMenuInfoDto updateMenuInfoDto) {
        //修改 name 图标 By menuId
        SysMenu sysMenu = null;
        try {
            sysMenu = BeanUtils.copyProperties(updateMenuInfoDto, SysMenu.class);
        } catch (APIException e) {
            e.printStackTrace();
            log.error("SQL异常{}", e.getMessage());
        }
        super.baseMapper.updateById(sysMenu);
    }

    @Transactional
    @Override
    public void removeMenu(Long menuId) {
        sysMenuMapper.deleteByMenuId(menuId);
        //且删除admin_menu_role表中menuId
        sysMenuMapper.deleteByMenuIdRole(menuId);
    }

    @Transactional
    @Override
    public void updateMenuStatus(Long menuId,int status) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(menuId);
        sysMenu.setStatus(status);
        if(status==1){
            SysMenu one = super.lambdaQuery().eq(SysMenu::getMenuId, menuId).one();
            if(one.getType()==2){//三级
                SysMenu sysMenu1 = new SysMenu();
                sysMenu1.setMenuId(menuId);
                sysMenu1.setStatus(1);
                sysMenuMapper.updateById(sysMenu1);
            }else if(one.getType()==1 && one.getParentId()!=-1){//二级
                Long parentId = one.getParentId();
                sysMenuMapper.updateStatusByMenuIdTwoQuXiao(CastUtil.castInt(parentId));//二级
            }else if(one.getParentId()==-1){//一级
                super.baseMapper.updateById(sysMenu);//自身解冻
                Long parentId=menuId;
                sysMenuMapper.updateStatusByMenuIdQuXiao(CastUtil.castInt(parentId));//一级
            }
            //super.baseMapper.updateById(sysMenu);
        }else{
            //强制性 如果下面有则全部冻结
            //根据menuId对比parent_id相等则冻结
            super.baseMapper.updateById(sysMenu);//自身冻结
            //寻找子级别 UPDATE `sys_menu` SET STATUS=1 WHERE menu_id=#{parent_id}
            Long parentId=menuId;
            sysMenuMapper.updateStatusByMenuId(CastUtil.castInt(parentId));//一级
            //找到我自身的parent_id
            SysMenu one = super.lambdaQuery().eq(SysMenu::getMenuId, menuId).one();
            if(one.getType()==1 && one.getParentId()!=-1){
                parentId = one.getParentId();
                sysMenuMapper.updateStatusByMenuIdTwo(CastUtil.castInt(parentId));//二级
            }
            if(one.getType()==2){//三级
                SysMenu sysMenu1 = new SysMenu();
                sysMenu1.setMenuId(menuId);
                sysMenu1.setStatus(-1);
                sysMenuMapper.updateById(sysMenu1);
            }
        }
    }


}
