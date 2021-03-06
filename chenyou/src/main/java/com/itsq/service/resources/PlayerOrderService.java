package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.PlayerOrderDto;
import com.itsq.pojo.entity.PlayerOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-10
 */
public interface PlayerOrderService extends IService<PlayerOrder> {



    Page<PlayerOrder>    selectPagePlayerOrder(PlayerOrderDto playerOrderDto);

}
