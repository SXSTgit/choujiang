package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.UserOrderPageDto;
import com.itsq.pojo.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-22
 */
public interface UserOrderService extends IService<UserOrder> {


   int  addUserOrder (UserOrder userOrder);

  Page<UserOrder> selectUserOrderPage(UserOrderPageDto userOrderPageDto);

}
