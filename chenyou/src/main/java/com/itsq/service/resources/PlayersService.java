package com.itsq.service.resources;

import com.itsq.pojo.entity.Players;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface PlayersService extends IService<Players> {

    /**
     *
     *注册
     * @param players
     * @return
     */
    Players  addPlayers(Players players);

    Players  selectPlayers(String number );

    /**
     * 修改用户信息
     * @param players
     * @return
     */
    int updatePlayersBuId(Players players);

    int updatePlayers(String number ,String pwd);
    /**
     * 登录
     * @param number
     * @param pwd
     * @return
     */
    Players  login(String number ,String pwd);


}
