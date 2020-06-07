package com.itsq.service.resources;

import com.itsq.pojo.entity.BoxArms;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.pojo.entity.PlayersArms;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface PlayersArmsService extends IService<PlayersArms> {

    /**
     * 按比例随机抽取一项
     * @param list 奖品列表
     * @return 类型值
     */
    PlayerBoxArms ratioExtract(PlayerBoxArms playerBoxArms) ;
}
