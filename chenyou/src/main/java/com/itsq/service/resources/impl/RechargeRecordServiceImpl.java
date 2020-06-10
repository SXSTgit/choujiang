package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.RechargeRecordDto;
import com.itsq.pojo.entity.Players;
import com.itsq.pojo.entity.RechargeRecord;
import com.itsq.mapper.RechargeRecordMapper;
import com.itsq.service.resources.PlayersService;
import com.itsq.service.resources.RechargeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.DateWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
@Service
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {
    @Autowired
    private PlayersService playersService;
    @Override
    public int addRechargeRecord(RechargeRecord rechargeRecord) {
        return super.baseMapper.insert(rechargeRecord);
    }

    @Override
    public Page<RechargeRecord> selectRechargeRecordDtoPage(RechargeRecordDto rechargeRecordDto) {


        Page page=new Page(rechargeRecordDto.getPageNum(),rechargeRecordDto.getPageSize());

        QueryWrapper queryWrapper=new QueryWrapper();

        if(rechargeRecordDto.getType()!=null) {
            queryWrapper.eq("type", rechargeRecordDto.getType());
        }

        if(rechargeRecordDto.getPlayersId()!=null) {
            queryWrapper.eq("players_id", rechargeRecordDto.getPlayersId());
        }

        if(rechargeRecordDto.getTradeNo()!=null) {
            queryWrapper.like("trade_no", rechargeRecordDto.getTradeNo());
        }

        queryWrapper.orderByDesc("cr_date");


        return super.baseMapper.selectMapsPage(page,queryWrapper);
    }

    @Override
    public RechargeRecord selectRechargeRecord(String order) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("trade_no",order);
        return super.baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public int updateRechargeRecord(RechargeRecord rechargeRecord) {
        Players players =new Players();
        players.setId(rechargeRecord.getPlayersId());
        Players players1 = playersService.selectPlayersById(rechargeRecord.getPlayersId());

        players.setBalance(rechargeRecord.getAmount().add(players1.getBalance()));
        int i = playersService.updatePlayersBuId(players);
        if(1<=0){
            throw new APIException(ErrorEnum.XIUGAI_YUE);
        }
        int i1 = super.baseMapper.updateById(rechargeRecord);
        if(i1<=0){
            throw new APIException(ErrorEnum.XIUGAI_YUE);
        }
        return 1;
    }

    @Override
    public int getCountRechargeRecord(String data) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if (data != null && !"".equals(data)) {
            Map<String, Object> where = DateWhere.where(data);
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("cre_date", tomorrow);
        }
        return super.baseMapper.selectCount(queryWrapper);
    }


}
