package com.itsq.service.resources.impl;

import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.AddUserDto;
import com.itsq.pojo.entity.User;
import com.itsq.mapper.UserMapper;
import com.itsq.service.resources.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.MD5;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User register(AddUserDto addUserDto) {
        addUserDto.setPassword(MD5.getMd5(addUserDto.getPassword(),32));
        User user = BeanUtils.copyProperties(addUserDto, User.class);
        super.baseMapper.insert(user);
        return user;
    }

    @Override
    public User login(String userName, String password) {
        Optional<User> u = super.lambdaQuery().eq(User::getUserName,userName).eq(User::getPassword,password).oneOpt();
        if (u.isPresent()) {
            return u.get();
        }
        throw new APIException(ErrorEnum.USER_NOT_EXITES);
    }

    @Override
    public List<User> getPageUser(User user) {
        return super.baseMapper.selectList(null);
    }
}
