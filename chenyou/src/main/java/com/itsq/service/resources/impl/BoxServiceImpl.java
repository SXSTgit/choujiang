package com.itsq.service.resources.impl;

import com.itsq.pojo.entity.Box;
import com.itsq.mapper.BoxMapper;
import com.itsq.service.resources.BoxService;
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
public class BoxServiceImpl extends ServiceImpl<BoxMapper, Box> implements BoxService {

}
