package com.itsq.controller.resources;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.BoxArmsDto;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Box;
import com.itsq.pojo.entity.BoxArms;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.service.resources.ArmsService;
import com.itsq.service.resources.BoxArmsService;
import com.itsq.service.resources.OperationRecordService;
import com.itsq.token.CurrentUser;
import com.itsq.utils.Base64Util;
import com.itsq.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/boxArms")
@CrossOrigin
@Api(tags = "获取箱子管理武器相关接口")
@AllArgsConstructor
public class BoxArmsController extends BaseController {
    private BoxArmsService boxArmsService;
    private ArmsService armsService;
    @Autowired
    private OperationRecordService operationRecordService;
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "获取全部武器", notes = "", httpMethod = "POST")
    public Response getAllInfo(@RequestBody BoxArms boxArms){




        QueryWrapper queryWrapper=new QueryWrapper();
        if(boxArms.getBoxId()!=null) {
            queryWrapper.eq("box_id",boxArms.getBoxId());
        }
        List<BoxArms> list=boxArmsService.list(queryWrapper);
        List list1=new ArrayList();
        for(BoxArms boxArms1:list){
            BoxArmsDto boxArmsDto= BeanUtils.copyProperties(boxArms1, BoxArmsDto.class);
            Arms arms=armsService.getById(boxArmsDto.getArmsId());
            boxArmsDto.setArms(arms);
            list1.add(boxArmsDto);
        }
        return Response.success(list1);
    }
}

