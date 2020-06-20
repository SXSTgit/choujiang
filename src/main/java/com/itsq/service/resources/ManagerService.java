package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.*;
import com.itsq.pojo.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
public interface ManagerService extends IService<Manager> {
    //根据ID查询管理员
    Optional<Manager> findManagerById(int userId);
    //管理员登录
    Manager login(ManagerLoginReqDto dto);
    //分页查询
    Page pageAdmins(FindPageManagerParmeters dto);
    //修改密码或者恢复初始密码
    void updatePassWord(updatePassWordDto updatePassWordDto);
    //修改管理员信息
    void updateManagerInfo(Manager manager);
    //新增管理员
    void saveManagerInfo(AddManagerReqDto addManagerReqDto);


}
