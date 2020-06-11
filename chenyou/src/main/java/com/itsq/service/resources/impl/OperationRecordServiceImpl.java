package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.OperationRecordDto;
import com.itsq.pojo.entity.Manager;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.mapper.OperationRecordMapper;
import com.itsq.pojo.entity.Players;
import com.itsq.service.resources.ManagerService;
import com.itsq.service.resources.OperationRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.service.resources.PlayersService;
import com.itsq.utils.DateWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-11
 */
@Service
public class OperationRecordServiceImpl extends ServiceImpl<OperationRecordMapper, OperationRecord> implements OperationRecordService {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private PlayersService playersService;

    @Override
    public Page selectOperationRecord(OperationRecordDto operationRecordDto) {
        Page<OperationRecord> operationRecordPage = new Page<>(operationRecordDto.getPageIndex(), operationRecordDto.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", operationRecordDto.getType());
        if (operationRecordDto.getDate() != null && !"".equals(operationRecordDto.getDate())) {
            Map<String, Object> where = DateWhere.where(operationRecordDto.getDate());
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("cre_date", tomorrow);
        }
        Page<OperationRecord> page = super.baseMapper.selectPage(operationRecordPage, queryWrapper);
       /* if (operationRecordDto.getType() == 0) {
            for (OperationRecord record : page.getRecords()) {
                Optional<Manager> managerById = managerService.findManagerById(record.getMangerId());
                record.setMangerName(managerById.get().getUserName());
            }
        }
        if (operationRecordDto.getType() == 1) {
            for (OperationRecord record : page.getRecords()) {
                Players players = playersService.selectPlayersById(record.getMangerId());
                record.setMangerName(players.getName());
            }
        }
*/
        return page;
    }

    @Override
    public int addOperationRecord(OperationRecord operationRecord) {
        if (operationRecord.getMangerId() == 0) {
            Optional<Manager> managerById = managerService.findManagerById(operationRecord.getMangerId());
            operationRecord.setMangerName(managerById.get().getUserName());
        }
        if (operationRecord.getMangerId() == 1) {
            Players players = playersService.selectPlayersById(operationRecord.getMangerId());
            operationRecord.setMangerName(players.getName());
        }
        return super.baseMapper.insert(operationRecord);
    }
}
