package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.RechargeRecordDto;
import com.itsq.pojo.entity.RechargeRecord;
import com.itsq.mapper.RechargeRecordMapper;
import com.itsq.service.resources.RechargeRecordService;
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
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {

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
            queryWrapper.like("tradeNo", rechargeRecordDto.getTradeNo());
        }

        queryWrapper.orderByDesc("cr_date");


        return super.baseMapper.selectMapsPage(page,queryWrapper);
    }
}
