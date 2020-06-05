package com.itsq.service.resources.impl;

import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayerBoxArmsDtoUpd;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.mapper.PlayerBoxArmsMapper;
import com.itsq.pojo.entity.Players;
import com.itsq.pojo.entity.User;
import com.itsq.service.resources.ArmsService;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.service.resources.PlayersService;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.PagesUtil;
import com.itsq.utils.http.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
@Service
public class PlayerBoxArmsServiceImpl extends ServiceImpl<PlayerBoxArmsMapper, PlayerBoxArms> implements PlayerBoxArmsService {
    @Autowired
    private PlayersService playersService;
    @Autowired
    private ArmsService armsService;
    @Autowired
    private Client client;
    @Override
    public int addPlayerBoxArms(PlayerBoxArms playerBoxArms) {
        return super.baseMapper.insert(playerBoxArms);
    }

    @Override
    public PlayerBoxArms selectPlayerBoxArmsById(Integer playerId) {
        return super.baseMapper.selectById(playerId);
    }

    @Override
    @Transactional
    public int updatePlayerBoxArms(PlayerBoxArmsDtoUpd playerBoxArmsDtoUpd) {
        PlayerBoxArms playerBoxArms = BeanUtils.copyProperties(playerBoxArmsDtoUpd, PlayerBoxArms.class);
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 1) {//出售武器
            PlayerBoxArms playerBoxArms1 = selectPlayerBoxArmsById(playerBoxArms.getId());
            //查询用户余额
            Players players = playersService.selectPlayersById(playerBoxArms1.getPlayerId());
            if (players.getIsStatus() != null && players.getIsStatus() == 0) {
                Arms arms = armsService.selectArmsById(playerBoxArms1.getArmsId());
                players.setBalance(players.getBalance().add(arms.getPrice()));
                //添加余额
                playersService.updatePlayersBuId(players);
            }else{
                throw new APIException(ErrorEnum.YICHUSHOU_YUE);
            }
        }
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 2) {



        }

        int i = super.baseMapper.updateById(playerBoxArms);
        if(i<=0){
            throw new APIException(ErrorEnum.YICHUSHOU_YUE);
        }
        return 1;
    }

    @Override
    public PagesUtil<PlayerBoxArms> selectPlayerBoxArmsPage(PageParametersDto pageParametersDto) {
        PagesUtil<PlayerBoxArms> page = new PagesUtil();
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", (pageParametersDto.getPageNum() - 1) * pageParametersDto.getPageSize());
        params.put("pageSize", pageParametersDto.getPageSize());
        params.put("playerId", pageParametersDto.getPlayerId());

        if (pageParametersDto.getOrderByField() != null && pageParametersDto.getOrderByField().equals(1)) {
            params.put("sort", "pba.cre_date");
        } else {
            params.put("sort", "a.`price`");
        }
        List<PlayerBoxArms> vipPriceList = this.baseMapper.selectPlayerBoxArms(params);
        int vipPriceCount = this.baseMapper.selectPlayerBoxArmsCount(params);
        page.setPageIndex(pageParametersDto.getPageNum());
        page.setTotalPages(vipPriceCount, pageParametersDto.getPageSize());
        page.setList(vipPriceList);
        return page;
    }
}
