package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.dto.BoxArmsSeachDto;
import com.itsq.pojo.entity.Arms;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface ArmsService extends IService<Arms> {
    Arms selectArmsById(Integer armsId);

    int addListArms(List<Arms> list);

    Page selectArmsPage(Integer pageIndex,Integer pageSize);

    List<Arms>  allPrice(Integer pid);

    List<Arms> selectArms(BoxArmsSeachDto boxArmsSeachDto);
}
