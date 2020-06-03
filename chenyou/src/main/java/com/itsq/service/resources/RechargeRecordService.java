package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.RechargeRecordDto;
import com.itsq.pojo.entity.RechargeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface RechargeRecordService extends IService<RechargeRecord> {




    int  addRechargeRecord(RechargeRecord rechargeRecord);

    Page<RechargeRecord> selectRechargeRecordDtoPage(RechargeRecordDto rechargeRecordDto);

}
