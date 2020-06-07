package com.itsq.service.resources.impl;

import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.entity.Arms;
import com.itsq.mapper.ArmsMapper;
import com.itsq.service.resources.ArmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Service
@AllArgsConstructor
@Slf4j
public class ArmsServiceImpl extends ServiceImpl<ArmsMapper, Arms> implements ArmsService {

    @Override
    public Arms selectArmsById(Integer armsId) {
        return super.baseMapper.selectById(armsId);
    }

    @Override
    @Transactional
    public int addListArms(List<Arms> list) {

        for (Arms arms : list) {
            int insert = super.baseMapper.insert(arms);
            if(insert<=0){
                throw new APIException(ErrorEnum.DARUWUQI_SHB);
            }
        }
        return 1;
    }
}
