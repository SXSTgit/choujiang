package com.itsq.controller.resources;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.PlayerBoxArmsDto;
import com.itsq.pojo.dto.PlayersArmsDto;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.service.resources.ArmsService;
import com.itsq.service.resources.OperationRecordService;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.itsq.utils.LotteryUtil;
import com.itsq.utils.http.Client;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/upgrading")
public class UpgradingController  extends BaseController {

    @Autowired
    private ArmsService armsService;
    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;

    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private Client client;
    @PostMapping("buybox")
    @ApiOperation(value = "根据选择的个人武器获取可升级武器", notes = "", httpMethod = "POST")
    public Response login(@RequestBody String info){
        JSONObject object = JSONObject.parseObject(info);
        QueryWrapper wrapper=new QueryWrapper();
        JSONObject infoObj = object.getJSONObject("info");
        wrapper.eq("is_status",0);
        BigDecimal total=infoObj.getBigDecimal("price").multiply(new BigDecimal(1.2));

        wrapper.gt("price",total);
        if(infoObj.getString("fangshi").equals("desc")){
            wrapper.orderByDesc(object.getString("ziduan"));
        }else{
            wrapper.orderByAsc(object.getString("ziduan"));

        }
        List<Arms> list=armsService.list(wrapper);
        return Response.success(list);
    }

    @PostMapping("huoqujl")
    @ApiOperation(value = "获取升级几率", notes = "", httpMethod = "POST")
    public Response huoqujl(@RequestBody String info){
        JSONObject object = JSONObject.parseObject(info);
        BigDecimal oldBig=new BigDecimal(object.getString("oldPrice"));
        BigDecimal newBig=new BigDecimal(object.getString("newPrice"));
        BigDecimal xin=oldBig.divide(newBig,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        DecimalFormat df=new DecimalFormat("0.00");
        if(xin.compareTo(new BigDecimal(80))==1){
            xin=new BigDecimal(80.00);
        }
        String str = df.format(xin);
        return Response.success(str);
    }

    @PostMapping("shengji")
    @ApiOperation(value = "升级武器", notes = "", httpMethod = "POST")
    public Response shenji(@RequestBody PlayerBoxArmsDto boxArms, HttpServletRequest request){
        List<Double> list = new ArrayList<Double>();
        list.add(Double.parseDouble(boxArms.getJilv()));
        list.add(100-Double.parseDouble(boxArms.getJilv()));
        LotteryUtil ll=new LotteryUtil(list);

        //String armsArray=boxArms.getOldArmss();
        //String[] armsArray2=armsArray.split(",");
        operationRecordService.addOperationRecord(new OperationRecord(boxArms.getPlayerId(),"升级武器","用户升级武器","/upgrading/updatePlayersById",1,client.getAddress(request.getRemoteAddr())));

        int index = ll.randomColunmIndex();

        for(String str:boxArms.getOldArms()) {
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("player_id",boxArms.getPlayerId());
            queryWrapper.in("arms_id",str);
            queryWrapper.eq("is_status",0);
            List<PlayerBoxArms> list1=playerBoxArmsService.list(queryWrapper);
            if(list1.size()==0){
                return Response.fail(ErrorEnum.NO_WUQI);
            }
            PlayerBoxArms arms=list1.get(0);
            if (index == 0) {
                //true;
                arms.setIsStatus(3);
                playerBoxArmsService.updateById(arms);

            } else {
                arms.setIsStatus(4);
                playerBoxArmsService.updateById(arms);

            }
        }
        if(index == 0){
            PlayerBoxArms boxArms1 = new PlayerBoxArms();
            boxArms1.setIsStatus(0);
            boxArms1.setType(1);
            boxArms1.setPlayerId(boxArms.getPlayerId());
            boxArms1.setArmsId(boxArms.getNewArms());
            boxArms1.setCreDate(new Date());
            playerBoxArmsService.save(boxArms1);
            Arms arms=armsService.selectArmsById(boxArms1.getArmsId());
            arms.setPbaId(boxArms1.getId());
            return Response.success(arms);
        }else{
            return Response.fail(ErrorEnum.WEI_HUODE);
        }

    }
}
