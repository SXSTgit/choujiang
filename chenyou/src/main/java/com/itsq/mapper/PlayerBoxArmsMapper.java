package com.itsq.mapper;

import com.itsq.pojo.entity.PlayerBoxArms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
public interface PlayerBoxArmsMapper extends BaseMapper<PlayerBoxArms> {


  List<PlayerBoxArms> selectPlayerBoxArms(Map<String,Object> params);


  int selectPlayerBoxArmsCount(Map<String,Object> params);
}
