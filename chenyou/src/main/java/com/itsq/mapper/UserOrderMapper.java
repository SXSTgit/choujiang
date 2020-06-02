package com.itsq.mapper;

import com.itsq.pojo.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-22
 */
public interface UserOrderMapper extends BaseMapper<UserOrder> {



   int  selectSum(Map map);

}
