package com.itsq.service.resources;

import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itsq.utils.PagesUtil;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
public interface PlayerBoxArmsService extends IService<PlayerBoxArms> {



    PagesUtil<PlayerBoxArms> selectPlayerBoxArmsPage(PageParametersDto pageParametersDto);
}
