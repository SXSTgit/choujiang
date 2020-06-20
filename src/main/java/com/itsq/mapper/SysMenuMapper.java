package com.itsq.mapper;

import com.itsq.pojo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author sunqi
 * @since 2020-04-08
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    @Update("UPDATE `sys_menu` SET `status` = -1 WHERE `parent_id` = #{parentId}")
    void updateStatusByMenuId(@Param("parentId") int parentId);

    @Update("UPDATE `sys_menu` SET `status` = -1 WHERE parent_id!=1 AND TYPE in(1,2) and parent_id=#{parentId}")
    void updateStatusByMenuIdTwo(@Param("parentId") int parentId);

    @Update("UPDATE `sys_menu` SET `status` = 1 WHERE parent_id!=1 AND TYPE in(1,2) and parent_id=#{parentId}")
    void updateStatusByMenuIdTwoQuXiao(@Param("parentId") int parentId);

    @Update("UPDATE `sys_menu` SET `status` = 1  WHERE `parent_id` = #{parentId}")
    void updateStatusByMenuIdQuXiao(@Param("parentId") int parentId);

    @Delete("DELETE  FROM `sys_menu` WHERE `menu_id` = #{menuId}")
    void deleteByMenuId(@Param("menuId") Long menuId);

    @Delete("DELETE  FROM `admin_menu_role` WHERE `sys_menu_id` = #{menuId}")
    void deleteByMenuIdRole(@Param("menuId") Long menuId);
}
