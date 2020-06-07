package com.itsq.controller.resources;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.AddArmsDto;
import com.itsq.pojo.entity.Arms;
import com.itsq.service.resources.ArmsService;
import com.itsq.token.CurrentUser;
import com.itsq.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/arms")
@CrossOrigin
@Api(tags = "获取武器相关接口")
@AllArgsConstructor
public class ArmsController  extends BaseController {

    private ArmsService armsService;

    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "获取全部武器", notes = "", httpMethod = "POST")
    public Response getAllArms(@RequestBody Arms arms){
        QueryWrapper queryWrapper=new QueryWrapper();
        if(arms.getName()!=null&&arms.getName().length()>0){
            queryWrapper.like("name",arms.getName());
        }
        queryWrapper.orderByDesc("id");
        List<Arms> list=armsService.list(queryWrapper);
        return Response.success(list);
    }

    @RequestMapping(value = "addInfo",method = RequestMethod.POST)
    @ApiOperation(value = "添加武器", notes = "", httpMethod = "POST")
    public Response addInfo(@RequestBody AddArmsDto dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        dto.setCreDate(new Date());
        Arms arms=BeanUtils.copyProperties(dto, Arms.class);
        if(!armsService.save(arms)){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success(arms);
    }

    @RequestMapping(value = "romveArms",method = RequestMethod.POST)
    @ApiOperation(value = "删除武器", notes = "", httpMethod = "POST")
    public Response romveArms(@RequestBody String id){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        JSONObject jsonObject=JSONObject.parseObject(id);
        if(!armsService.removeById(Long.parseLong(jsonObject.getString("id")))){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success();
    }

    @RequestMapping(value = "updateById",method = RequestMethod.POST)
    @ApiOperation(value = "修改武器信息", notes = "", httpMethod = "POST")
    public Response updateById(@RequestBody AddArmsDto dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        Arms arms=BeanUtils.copyProperties(dto, Arms.class);
        if(!armsService.updateById(arms)){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success(arms);
    }

    @RequestMapping(value = "updateAramStatus",method = RequestMethod.POST)
    @ApiOperation(value = "修改武器状态根据id", notes = "", httpMethod = "POST")
    public Response updateAramStatus(String id,String isStatus){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        Arms arms=new Arms();
        arms.setId(Integer.parseInt(id));
        arms.setIsStatus(Integer.parseInt(isStatus));
        if(!armsService.updateById(arms)){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success();
    }


}

