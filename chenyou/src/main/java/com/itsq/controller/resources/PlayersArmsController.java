package com.itsq.controller.resources;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.LoginRespDto;
import com.itsq.pojo.dto.PlayersArmsDto;
import com.itsq.pojo.dto.PlayersDto;
import com.itsq.pojo.entity.*;
import com.itsq.service.resources.*;
import com.itsq.token.AuthToken;
import com.itsq.token.CurrentUser;
import com.itsq.utils.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/playersArms")
public class PlayersArmsController extends BaseController {

    @Autowired
    private PlayersArmsService playersArmsService;
    @Autowired
    private BoxArmsService boxArmsService;
    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;
    @Autowired
    private PlayersService playersService;
    @Autowired
    private BoxService boxService;
    @Autowired
    private ArmsService armsService;

    @Autowired
    private OperationRecordService operationRecordService;
    @PostMapping("buybox")
    @ApiOperation(value = "用户购买箱子", notes = "", httpMethod = "POST")
    public Response login(@RequestBody PlayersArmsDto playersArmsDto){


        operationRecordService.addOperationRecord(new OperationRecord(playersArmsDto.getId(),"购买箱子","购买"+playersArmsDto.getBoxId()+"箱子","/playersArms/buybox",1));


        //获得用户信息
        Players players=playersService.getById(playersArmsDto.getId());
        //获得箱子信息
        Box box=boxService.getById(playersArmsDto.getBoxId());
        if(players.getBalance().compareTo(box.getPrice())==-1){
            return Response.fail(ErrorEnum.YUE_BU_ZU);
        }
        PlayerBoxArms playerBoxArms=new PlayerBoxArms();
        playerBoxArms.setBoxId(playersArmsDto.getBoxId());
        playerBoxArms.setPlayerId(playersArmsDto.getId());
        PlayerBoxArms boxArms=playersArmsService.ratioExtract(playerBoxArms);
        Arms arms=null;
        if(boxArms.getArmsId()!=null){
            arms=armsService.getById(armsService.getById(boxArms.getArmsId()));
            arms.setPbaId(boxArms.getId());
        }
        return  Response.success(arms);
    }

}

