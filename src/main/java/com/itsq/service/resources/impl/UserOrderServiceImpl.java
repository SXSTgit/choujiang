package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.UserOrderPageDto;
import com.itsq.pojo.entity.UserOrder;
import com.itsq.mapper.UserOrderMapper;
import com.itsq.service.resources.UserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-22
 */
@Service
public class
UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {

    @Override
    public int addUserOrder(UserOrder userOrder) {
        return super.baseMapper.insert(userOrder);
    }

    @Override
    public Page<UserOrder> selectUserOrderPage(UserOrderPageDto userOrderPageDto) {
        Page<UserOrder>  page=new Page<>(userOrderPageDto.getPageIndex(),userOrderPageDto.getPageSize());
        QueryWrapper queryWrapper=new QueryWrapper();
        if(userOrderPageDto.getManagerId()!=null) {
            queryWrapper.eq("manager_id", userOrderPageDto.getManagerId());
        }

        return super.baseMapper.selectPage(page,queryWrapper);
    }
}
