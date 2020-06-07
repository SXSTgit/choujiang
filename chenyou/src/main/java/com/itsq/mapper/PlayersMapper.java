package com.itsq.mapper;

import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Players;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface PlayersMapper extends BaseMapper<Players> {

    List<Arms> selectPlayerBox(Map<String,Object> params);

}
