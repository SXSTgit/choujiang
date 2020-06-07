package com.itsq.service.resources.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayerBoxArmsDtoUpd;
import com.itsq.pojo.dto.normalBuyParamV2DTO;
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
import com.itsq.utils.RandomUtil;
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

        Players players =null;
        Arms arms =null;
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 1) {//出售武器
            PlayerBoxArms playerBoxArms1 = selectPlayerBoxArmsById(playerBoxArms.getId());
            //查询用户余额
             players = playersService.selectPlayersById(playerBoxArms1.getPlayerId());
            if (playerBoxArms1.getIsStatus() != null && playerBoxArms1.getIsStatus() == 0) {
                 arms = armsService.selectArmsById(playerBoxArms1.getArmsId());
                players.setBalance(players.getBalance().add(arms.getPrice()));
                //添加余额
                playersService.updatePlayersBuId(players);
            }else{
                throw new APIException(ErrorEnum.YICHUSHOU_YUE);
            }
        }
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 2) {

            JSONObject jsonObject=new JSONObject();

            jsonObject.put("outTradeNo", RandomUtil.getRandom(32));
            jsonObject.put("tradeUrl",players.getSteamUrl());
            jsonObject.put("productId",arms.getProductId());
            String json = client.httpPostWithJSON("https://app.zbt.com/open/trade/v2/buy?app-key=0b791fef5d1cc463edda79924704e8a7&language=zh_CN", jsonObject);
            System.out.println(json);
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

    @Override
    public int selectUpCount(Integer type) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",type);
        return super.baseMapper.selectCount(queryWrapper);
    }
}
