package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.dto.PlayersDtoPage;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Players;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itsq.pojo.entity.UserOrder;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface PlayersService extends IService<Players> {

    /**
     * 注册
     *
     * @param players
     * @return
     */
    Players addPlayers(Players players);

    Players selectPlayers(String number);

    Players selectPlayersById(Integer id);

    /**
     * 修改用户信息
     *
     * @param players
     * @return
     */
    int updatePlayersBuId(Players players);

    int updatePlayers(String number, String pwd);

    /**
     * 登录
     *
     * @param number
     * @param pwd
     * @return
     */
    Players login(String number, String pwd);

    Page<Players> selectPlayersPage(PlayersDtoPage playersDtoPage);


    Players selectPlayerArms(PlayersDto playersDto);


    int selectTodayAdd(String  date);

    int selectPlayerCount();


    int updateMoneyById(PlayersDto playersDto);


    Players loginBySteam(String steamId);
}
