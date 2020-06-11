package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.PlayerOrderDto;
import com.itsq.pojo.entity.PlayerOrder;
import com.itsq.mapper.PlayerOrderMapper;
import com.itsq.service.resources.PlayerOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-10
 */
@Service
public class PlayerOrderServiceImpl extends ServiceImpl<PlayerOrderMapper, PlayerOrder> implements PlayerOrderService {

    @Override
    public Page<PlayerOrder> selectPagePlayerOrder(PlayerOrderDto playerOrderDto) {

        Page<PlayerOrder> playerOrderPage=new Page<>(playerOrderDto.getPageIndex(),playerOrderDto.getPageSize());
        QueryWrapper queryWrapper=new QueryWrapper();

        if(playerOrderDto.getNumber()!=null) {
            queryWrapper.like("number", playerOrderDto.getNumber());
        }
        if(playerOrderDto.getIsStatus()!=null) {
            queryWrapper.eq("is_status", playerOrderDto.getIsStatus());
        }
        queryWrapper.orderByDesc("cre_date");
        return super.baseMapper.selectPage(playerOrderPage,queryWrapper);


    }
}
