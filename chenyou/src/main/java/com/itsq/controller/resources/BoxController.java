package com.itsq.controller.resources;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.AddArmsDto;
import com.itsq.pojo.dto.AddBoxDto;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Box;
import com.itsq.pojo.entity.BoxArms;
import com.itsq.service.resources.BoxArmsService;
import com.itsq.service.resources.BoxService;
import com.itsq.token.CurrentUser;
import com.itsq.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
@RequestMapping("/box")
@CrossOrigin
@Api(tags = "获取箱子相关接口")
@AllArgsConstructor
public class BoxController extends BaseController {

    private BoxService boxService;
    private BoxArmsService boxArmsService;


    @RequestMapping(value = "getAllBox",method = RequestMethod.POST)
    @ApiOperation(value = "获取全部箱子", notes = "", httpMethod = "POST")
    public Response getAllBox(){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.orderByDesc("id");
        List list=boxService.list(queryWrapper);
        return Response.success(list);
    }

    @RequestMapping(value = "romveBox",method = RequestMethod.POST)
    @ApiOperation(value = "删除武器", notes = "", httpMethod = "POST")
    public Response romveBox(@RequestBody String id){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        JSONObject jsonObject=JSONObject.parseObject(id);
        if(!boxService.removeById(Long.parseLong(jsonObject.getString("id")))){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success();
    }

    @RequestMapping(value = "addInfo",method = RequestMethod.POST)
    @ApiOperation(value = "添加箱子", notes = "", httpMethod = "POST")
    public Response addInfo(@RequestBody AddBoxDto dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        dto.setCrDate(new Date());
        Box box= BeanUtils.copyProperties(dto, Box.class);
        if(!boxService.save(box)){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        JSONArray array=JSONArray.parseArray(dto.getGlId());
        for(Object obj:array){
            JSONObject object=(JSONObject)obj;
            System.out.println(object);
            BoxArms boxArms=new BoxArms();
            boxArms.setCrDate(new Date());
            boxArms.setChance(object.getInteger("chance"));
            boxArms.setCount(object.getInteger("count"));
            boxArms.setArmsId(object.getInteger("id"));
            boxArms.setIsStatus(0);
            boxArms.setBoxId(box.getId());
            boxArmsService.save(boxArms);
        }
        return Response.success(box);
    }

    @RequestMapping(value = "updateById",method = RequestMethod.POST)
    @ApiOperation(value = "修改箱子信息", notes = "", httpMethod = "POST")
    public Response updateById(@RequestBody AddBoxDto dto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        Box box=BeanUtils.copyProperties(dto, Box.class);
        if(!boxService.updateById(box)){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("box_id",box.getId());
        boxArmsService.remove(queryWrapper);

        JSONArray array=JSONArray.parseArray(dto.getGlId());
        for(Object obj:array){
            JSONObject object=(JSONObject)obj;
            System.out.println(object);
            BoxArms boxArms=new BoxArms();
            boxArms.setCrDate(new Date());
            boxArms.setChance(object.getInteger("chance"));
            boxArms.setCount(object.getInteger("count"));
            boxArms.setArmsId(object.getInteger("id"));
            boxArms.setIsStatus(0);
            boxArms.setBoxId(box.getId());
            boxArmsService.save(boxArms);
        }
        return Response.success(dto);
    }
}

