package com.itsq.service.resources.impl;

import com.itsq.pojo.entity.Players;
import com.itsq.mapper.PlayersMapper;
import com.itsq.service.resources.PlayersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Service
public class PlayersServiceImpl extends ServiceImpl<PlayersMapper, Players> implements PlayersService {

}
