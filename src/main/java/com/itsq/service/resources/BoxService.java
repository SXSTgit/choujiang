package com.itsq.service.resources;

import com.itsq.pojo.dto.BoxArmsDto;
import com.itsq.pojo.entity.Box;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itsq.pojo.entity.BoxArms;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
public interface BoxService extends IService<Box> {
    Box selectBoxArms(BoxArms boxArms);

    List<Box>  selectListBox();

    int updateBoxUpdateById(Box box);

    int selectBoxCount();
}
