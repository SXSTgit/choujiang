package com.itsq.service.resources.impl;

import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.mapper.PlayerBoxArmsMapper;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.PagesUtil;
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
 * @since 2020-06-04
 */
@Service
public class PlayerBoxArmsServiceImpl extends ServiceImpl<PlayerBoxArmsMapper, PlayerBoxArms> implements PlayerBoxArmsService {

    @Override
    public PagesUtil<PlayerBoxArms> selectPlayerBoxArmsPage(PageParametersDto pageParametersDto) {
        PagesUtil<PlayerBoxArms> page = new PagesUtil();
        Map<String,Object> params = new HashMap<>();
        params.put("pageIndex",(pageParametersDto.getPageNum()-1)*pageParametersDto.getPageSize());
        params.put("pageSize",pageParametersDto.getPageSize());
        List<PlayerBoxArms> vipPriceList = this.baseMapper.selectPlayerBoxArms(params);
        int vipPriceCount = this.baseMapper.selectPlayerBoxArmsCount(params);
        page.setPageIndex(pageParametersDto.getPageNum());
        page.setTotalPages(vipPriceCount,pageParametersDto.getPageSize());
        page.setList(vipPriceList);
        return page;
    }
}
