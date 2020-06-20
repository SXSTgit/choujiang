package com.itsq.mapper;

import com.itsq.pojo.entity.Box;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface BoxMapper extends BaseMapper<Box> {
    Box  selectBoxArms(Map<String,Object> params);
}
