package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.dto.PlayersDtoPage;
import com.itsq.pojo.entity.*;
import com.itsq.mapper.PlayersMapper;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.itsq.service.resources.PlayersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.DateWhere;
import com.itsq.utils.MD5;
import com.itsq.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Service
public class
PlayersServiceImpl extends ServiceImpl<PlayersMapper, Players> implements PlayersService {
@Autowired
   private  PlayerBoxArmsService playerBoxArmsService;

    @Override
    public Players addPlayers(Players players) {

        Players players1 = selectPlayers(players.getNumber());

        if (players1 != null) {
            throw new APIException(ErrorEnum.PLAYER_PHONE);
        }
        players.setName(players.getNumber());
        players.setPwd(MD5.getMd5(players.getPwd(), 32));
        super.baseMapper.insert(players);
        return players;
    }

    @Override
    public Players selectPlayers(String number) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("number", number);
        return super.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Players selectPlayersById(Integer id) {
        return super.baseMapper.selectById(id);
    }

    @Override
    public int updatePlayersBuId(Players players) {
       /* Players players1 = selectPlayersById(players.getId());

        players.setBalance(players1.getBalance().add(players.getBalance()));*/

        return super.baseMapper.updateById(players);
    }

    @Override
    public int updatePlayers(String number, String pwd) {
        Players players = selectPlayers(number);

        if (players == null) {
            throw new APIException(ErrorEnum.USER_NOT_EXITES);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        players.setPwd(MD5.getMd5(pwd, 32));
        queryWrapper.eq("number", players.getNumber());

        return super.baseMapper.update(players, queryWrapper);
    }

    @Override
    public Players login(String number, String pwd) {

        Optional<Players> u = super.lambdaQuery().eq(Players::getNumber, number).eq(Players::getPwd, MD5.getMd5(pwd, 32)).oneOpt();
        if (u.isPresent()) {
            return u.get();
        }
        throw new APIException(ErrorEnum.USER_NOT_EXITES);
    }

    @Override
    public Page<Players> selectPlayersPage(PlayersDtoPage playersDtoPage) {

        Page<Players> page = new Page<>(playersDtoPage.getPageIndex(), playersDtoPage.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper();


        Page<Players>  page1 = super.baseMapper.selectPage(page, queryWrapper);
        List<Players> records = page1.getRecords();

        for (Players record : records) {

            record.setBoxCount(   playerBoxArmsService.getPlayersCount(record.getId(),0));
            record.setUpCount( playerBoxArmsService.getPlayersCount(record.getId(),1));
        }

        return page1;
    }

    @Override
    public Players selectPlayerArms(PlayersDto playersDto) {


        PagesUtil<PlayerBoxArms> page = new PagesUtil();
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", (playersDto.getPageIndex() - 1) * playersDto.getPageSize());
        params.put("pageSize", playersDto.getPageSize());

        params.put("playerId", playersDto.getId());
        if (playersDto.getIsStatus() != null) {
            params.put("isStatus", playersDto.getIsStatus());
        }
        Players players = super.baseMapper.selectPlayerBox(params);
        int vipPriceCount = this.baseMapper.selectPlayerBoxCount(params);
        page.setPageIndex(playersDto.getPageIndex());
        page.setTotalPages(vipPriceCount, playersDto.getPageSize());
        players.setTotalPages(page.getTotalPages());
        return players;
    }

    @Override
    public int selectTodayAdd(String date) {

        QueryWrapper queryWrapper = new QueryWrapper();

        if (date != null && !"".equals(date)) {
            Map<String, Object> where = DateWhere.where(date);
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("cre_date", tomorrow);
        }

        return super.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int selectPlayerCount() {
QueryWrapper queryWrapper=new QueryWrapper();
        return super.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int updateMoneyById(PlayersDto playersDto) {
        Players p= selectPlayersById(playersDto.getId());
if(playersDto.getUsStatus()==1){
    p.setBalance(p.getBalance().add(playersDto.getAmount()));

}else{
    p.setBalance(p.getBalance().subtract(playersDto.getAmount()));
}

        return super.baseMapper.updateById(p);
    }

    @Override
    public Players loginBySteam(String steamId) {
        return null;
    }


}
