package com.itsq.mapper;

import com.itsq.pojo.entity.Manager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
public interface ManagerMapper extends BaseMapper<Manager> {
    //查询我拥有的权限
    @Select("SELECT sys_menu_id FROM `admin_menu_role` WHERE state=0 AND adminId=#{id} AND sys_menu_id IN(SELECT menu_id FROM sys_menu WHERE parent_id!=-1 AND TYPE=1)")
    List<Integer> listMyRoles(@Param("id")int id);
    //查询所有权限
    @Select("SELECT menu_id FROM sys_menu WHERE parent_id!=-1 AND TYPE=1 AND STATUS=1")
    List<Integer> listRoles();
}
