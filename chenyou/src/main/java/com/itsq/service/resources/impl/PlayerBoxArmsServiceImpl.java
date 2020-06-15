package com.itsq.service.resources.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayerBoxArmsDtoUpd;
import com.itsq.pojo.dto.PlayersSellDto;
import com.itsq.pojo.entity.*;
import com.itsq.mapper.PlayerBoxArmsMapper;
import com.itsq.pojo.vo.ArmsVo;
import com.itsq.service.resources.ArmsService;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.service.resources.PlayerOrderService;
import com.itsq.service.resources.PlayersService;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.DateWhere;
import com.itsq.utils.PagesUtil;
import com.itsq.utils.RandomUtil;
import com.itsq.utils.http.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private PlayerOrderService playerOrderService;

    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;
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
        PlayerBoxArms playerBoxArms1 = selectPlayerBoxArmsById(playerBoxArms.getId());
        //查询用户余额
        Players players  = playersService.selectPlayersById(playerBoxArms1.getPlayerId());

        Arms  arms = armsService.selectArmsById(playerBoxArms1.getArmsId());
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 1) {//出售武器
            if (playerBoxArms1.getIsStatus() != null && playerBoxArms1.getIsStatus() == 0) {
                players.setBalance(players.getBalance().add(arms.getPrice()));
                //添加余额
                playerBoxArms1.setIsStatus(1);
                playersService.updatePlayersBuId(players);
            }else{
                throw new APIException(ErrorEnum.YICHUSHOU_YUE);
            }
        }
        if (playerBoxArmsDtoUpd.getIsStatus() != null && playerBoxArmsDtoUpd.getIsStatus() == 2) {//取回武器

            JSONObject jsonObject=new JSONObject();
            Map<String,Object> param=new HashMap<>();
            String number=RandomUtil.getRandom(32);
            jsonObject.put("outTradeNo",number);
            jsonObject.put("tradeUrl",players.getSteamUrl());
            jsonObject.put("itemId",arms.getProductId());//arms.getProductId()
            jsonObject.put("maxPrice",arms.getPrice());//可接受价格 arms.getPrice()
            jsonObject.put("delivery",2);//自动发货
            String json = client.httpPostWithJSON("https://app.zbt.com/open/trade/v2/quick-buy?app-key=0b791fef5d1cc463edda79924704e8a7&language=zh_CN", jsonObject);
            System.out.println(json);
            Object succesResponse = JSON.parse(json);
            Map map = (Map)succesResponse;
            Object succesResponse1 = JSON.parse(map.get("data")+"");
            Map map1 = (Map)succesResponse1;
            PlayerOrder playerOrder=new PlayerOrder();
            playerOrder.setArmsId(arms.getId());
            playerOrder.setBuyPrice(new BigDecimal(map1.get("buyPrice")+""));
            if((boolean)map.get("success")){
                playerBoxArms1.setIsStatus(2);
                playerOrder.setIsStatus(0);
                playerOrder.setNumber(map1.get("orderId")+"");
                playerOrder.setPlayerId(players.getId());
                playerOrderService.save(playerOrder);
                int i = super.baseMapper.updateById(playerBoxArms);
                if(i<=0){
                    throw new APIException(ErrorEnum.YICHUSHOU_YUE);
                }
            }else{
                playerOrder.setIsStatus(1);
                playerOrder.setNumber(map1.get("orderId")+"");
                playerOrder.setPlayerId(players.getId());
                playerOrderService.save(playerOrder);
                return 2;
            }
        }
super.baseMapper.updateById(playerBoxArms1);

        return 1;
    }

    @Override
    public PagesUtil<PlayerBoxArms> selectPlayerBoxArmsPage(PageParametersDto pageParametersDto) {
        PagesUtil<PlayerBoxArms> page = new PagesUtil();
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", (pageParametersDto.getPageNum() - 1) * pageParametersDto.getPageSize());
        params.put("pageSize", pageParametersDto.getPageSize());
       if(pageParametersDto.getPlayerId()!=null&&pageParametersDto.getPlayerId()>0){
           params.put("playerId", pageParametersDto.getPlayerId());
       }
        if(pageParametersDto.getType()!=null){
            params.put("type", pageParametersDto.getType());
        }
        if (pageParametersDto.getOrderByField() != null && pageParametersDto.getOrderByField().equals("1")) {
            params.put("sort", "1");
        } else {
            params.put("sort", "2");
        }
        List<PlayerBoxArms> vipPriceList = this.baseMapper.selectPlayerBoxArms(params);
        int vipPriceCount = this.baseMapper.selectPlayerBoxArmsCount(params);
        page.setPageIndex(pageParametersDto.getPageNum());
        page.setTotalPages(vipPriceCount, pageParametersDto.getPageSize());
        page.setList(vipPriceList);
        return page;
    }


    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal("1.01");


        System.out.println(bigDecimal.add(new BigDecimal("1.02")));
    }
    @Override
    @Transactional
    public int sellArms(PlayersSellDto playersSellDto) {
        BigDecimal bigDecimal=new BigDecimal(0);

        for (Integer pbaIdInteger : playersSellDto.getPbaIdIntegers()) {
            PlayerBoxArms playerBoxArms = playerBoxArmsService.selectPlayerBoxArmsById(pbaIdInteger);
            Arms arms=armsService.selectArmsById(playerBoxArms.getArmsId());
            bigDecimal=  bigDecimal.add(arms.getPrice());
            PlayerBoxArmsDtoUpd playerBoxArmsDtoUpd=new PlayerBoxArmsDtoUpd();
            playerBoxArmsDtoUpd.setId(pbaIdInteger);
            playerBoxArmsDtoUpd.setIsStatus(1);
            playerBoxArmsService.updatePlayerBoxArms(playerBoxArmsDtoUpd);
        }
        //查询用户余额
        Players players  = playersService.selectPlayersById(playersSellDto.getId());
        players.setBalance(players.getBalance().add(bigDecimal));
        //添加余额
        int i = playersService.updatePlayersBuId(players);
        return i ;
    }

    @Override
    public int selectUpCount(Integer type) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",type);
        return super.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int getUpCountData(String data ,Integer type) {

        QueryWrapper queryWrapper=new QueryWrapper();
        if (data != null && !"".equals(data)) {
            Map<String, Object> where = DateWhere.where(data);
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("cre_date", tomorrow);
        }
        queryWrapper.eq("type",type);
        return super.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public PagesUtil<ArmsVo> selectUpRecord(PageParametersDto pageParametersDto) {
        PagesUtil<ArmsVo> page = new PagesUtil();
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", (pageParametersDto.getPageNum() - 1) * pageParametersDto.getPageSize());
        params.put("pageSize", pageParametersDto.getPageSize());
        if(pageParametersDto.getPlayerId()!=null&&pageParametersDto.getPlayerId()>0){
            params.put("playerId", pageParametersDto.getPlayerId());
        }
        List<ArmsVo> vipPriceList = this.baseMapper.selectUpStatus(params);
        int vipPriceCount = this.baseMapper.selectUpStatusCount(params);
        page.setPageIndex(pageParametersDto.getPageNum());
        page.setTotalPages(vipPriceCount, pageParametersDto.getPageSize());
        page.setList(vipPriceList);
        return page;
    }

    @Override
    public BigDecimal getTodayAllPrice(String date) {

        QueryWrapper queryWrapper=new QueryWrapper();

        if (date != null && !"".equals(date)) {
            Map<String, Object> where = DateWhere.where(date);
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("cre_date", tomorrow);
        }

        List<PlayerBoxArms> list = super.baseMapper.selectList(queryWrapper);

        BigDecimal bigDecimal=new BigDecimal(0);
        for (PlayerBoxArms o : list) {
            Arms arms = armsService.selectArmsById(o.getArmsId());
            bigDecimal=bigDecimal.add(arms.getPrice());
        }

        /*Map<String, Object> params = new HashMap<>();
        params.put("creDate",date);
        BigDecimal v =new BigDecimal(0);
        BigDecimal v1 = this.baseMapper.getTodayAllPrice(params).add(v);*/
        return bigDecimal;
    }

    @Override
    public int getPlayersCount(Integer playerId, Integer type) {

        QueryWrapper queryWrapper=new QueryWrapper();

        queryWrapper.eq("player_id",playerId);
        queryWrapper.eq("type",type);
        return super.baseMapper.selectCount(queryWrapper);
    }
}
