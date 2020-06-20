package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.pojo.dto.BoxArmsDto;
import com.itsq.pojo.entity.Box;
import com.itsq.mapper.BoxMapper;
import com.itsq.pojo.entity.BoxArms;
import com.itsq.service.resources.BoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Service
public class BoxServiceImpl extends ServiceImpl<BoxMapper, Box> implements BoxService {

    @Override
    public Box selectBoxArms(BoxArms boxArms) {
        Map<String, Object> params = new HashMap<>();

        params.put("boxId",boxArms.getBoxId());

        return super.baseMapper.selectBoxArms(params);
    }

    @Override
    public List<Box> selectListBox() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("is_status",0);
        return super.baseMapper.selectList(queryWrapper);
    }

    @Override
    public int updateBoxUpdateById(Box box) {
        return super.baseMapper.updateById(box);
    }

    @Override
    public int selectBoxCount() {
        QueryWrapper queryWrapper=new QueryWrapper();
        return super.baseMapper.selectCount(queryWrapper);
    }
}
