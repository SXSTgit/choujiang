package com.itsq.service.resources;

import com.itsq.pojo.dto.AddUserDto;
import com.itsq.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
public interface UserService extends IService<User> {
    //注册
    public User register(AddUserDto addUserDto);
    //登录
    public User login(String userName,String password);
    //分页查询用户
    public List<User> getPageUser(User user);
}
