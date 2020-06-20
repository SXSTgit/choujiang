package com.itsq.service.menu;

import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.AddMenuOneDto;
import com.itsq.pojo.dto.AddMenuThreeDto;
import com.itsq.pojo.dto.AddMenuTwoDto;
import com.itsq.pojo.dto.UpdateMenuInfoDto;
import com.itsq.pojo.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-08
 */
public interface SysMenuService extends IService<SysMenu> {
    Response showAllMenu(Integer source, int adminId);
    //分级查询菜单
    List<SysMenu> mapMenu();
    //新增一级菜单
    void saveMenuOne(AddMenuOneDto addMenuOneDto);
    //新增二级菜单
    void saveMenuTwo(AddMenuTwoDto addMenuTwoDto);
    //新增按钮
    void saveMenuThree(AddMenuThreeDto addMenuThreeDto);
    //修改Menu
    void updateMenuInfo(UpdateMenuInfoDto updateMenuInfoDto);
    //根据ID冻结
    void updateMenuStatus(Long menuId,int status);
    //根据MenuId删除 真删除非逻辑删除
    void removeMenu(Long menuId);
}
