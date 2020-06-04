package com.itsq.service.resources.impl;

import com.itsq.pojo.entity.Arms;
import com.itsq.mapper.ArmsMapper;
import com.itsq.service.resources.ArmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
