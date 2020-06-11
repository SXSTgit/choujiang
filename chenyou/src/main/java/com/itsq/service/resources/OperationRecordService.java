package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.OperationRecordDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itsq.pojo.entity.OperationRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-11
 */
public interface OperationRecordService extends IService<OperationRecord> {

   Page selectOperationRecord(OperationRecordDto operationRecordDto);


   int addOperationRecord(OperationRecord operationRecord);


}
