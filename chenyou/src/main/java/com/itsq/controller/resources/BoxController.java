package com.itsq.controller.resources;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.PageInfo;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.common.redis.RedisUtils;
import com.itsq.pojo.dto.AddBoxDto;
import com.itsq.pojo.entity.Box;
import com.itsq.pojo.entity.BoxArms;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.service.resources.BoxArmsService;
import com.itsq.service.resources.BoxService;
import com.itsq.service.resources.OperationRecordService;
import com.itsq.token.CurrentUser;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.http.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private OperationRecordService operationRecordService;

    @Resource
    private RedisUtils redisUtil;
    @Autowired
    private Client client;
    @RequestMapping(value = "getAllBox",method = RequestMethod.POST)
    @ApiOperation(value = "获取全部箱子", notes = "", httpMethod = "POST")
    public Response getAllBox(@RequestBody Box box, HttpServletRequest request) throws ParseException {



      if(!redisUtil.exist( request.getRemoteAddr())){
          redisUtil.set( request.getRemoteAddr(),60*10,"1");

                  if(!redisUtil.exist("count")){
                      redisUtil.set("count","0");
                  }
          Integer count = Integer.valueOf(redisUtil.get("count") + "");
          redisUtil.set("count",count+1+"");
      }




        QueryWrapper queryWrapper=new QueryWrapper();

        if(box.getName()!=null&&box.getName().length()>0){
            queryWrapper.like("name",box.getName());
        }
        if(box.getType()!=null&&box.getType()!=-1){
            queryWrapper.eq("type",box.getType());
        }
        queryWrapper.orderByDesc("id");
        queryWrapper.gt("out_time",new Date());
        List<Box> list=  boxService.list(queryWrapper);
        List list1=new ArrayList();
        for(Box box1:list){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(new Date())).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(box1.getOutTime())).getTime();
            Integer day= (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
            AddBoxDto dto=BeanUtils.copyProperties(box1,AddBoxDto.class);
            dto.setDays(day);
            list1.add(dto);
        }
        return Response.success(list1);
    }

    @RequestMapping(value = "romveBox",method = RequestMethod.POST)
    @ApiOperation(value = "删除箱子", notes = "", httpMethod = "POST")
    public Response romveBox(@RequestBody String id,  HttpServletRequest request){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        operationRecordService.addOperationRecord(new OperationRecord(1,"删除箱子","删除id为"+id+"的箱子","/box/romveBox",0,client.getAddress(request.getRemoteAddr())));

        JSONObject jsonObject=JSONObject.parseObject(id);
        if(!boxService.removeById(Long.parseLong(jsonObject.getString("id")))){
            return Response.fail(ErrorEnum.ERROR_SERVER);
        }
        return Response.success();
    }

    @RequestMapping(value = "addInfo",method = RequestMethod.POST)
    @ApiOperation(value = "添加箱子", notes = "", httpMethod = "POST")
    public Response addInfo(@RequestBody AddBoxDto dto,  HttpServletRequest request){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        operationRecordService.addOperationRecord(new OperationRecord(dto.getManagerId(),"添加箱子",dto.getName()+"箱子","/arms/addInfo",0,client.getAddress(request.getRemoteAddr())));

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
    public Response updateById(@RequestBody AddBoxDto dto,  HttpServletRequest request){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        operationRecordService.addOperationRecord(new OperationRecord(dto.getManagerId(),"修改箱子",dto.getName()+"箱子","/arms/updateById",0,client.getAddress(request.getRemoteAddr())));

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


    @PostMapping("selectBoxArms")
    @ApiOperation(value = "箱子-详情", notes = "", httpMethod = "POST")
    public Response<Box> selectBoxArms(@RequestBody BoxArms boxArms){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( boxService.selectBoxArms(boxArms));
    }
}

