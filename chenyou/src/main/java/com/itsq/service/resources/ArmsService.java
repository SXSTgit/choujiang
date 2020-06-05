package com.itsq.service.resources;

import com.itsq.pojo.entity.Arms;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
