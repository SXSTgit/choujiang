package com.itsq.controller.resources;


import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayerBoxArmsDtoUpd;
import com.itsq.pojo.dto.PlayersSellDto;
import com.itsq.pojo.entity.OperationRecord;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.pojo.vo.ArmsVo;
import com.itsq.service.resources.OperationRecordService;
import com.itsq.service.resources.PlayerBoxArmsService;
import com.itsq.utils.PagesUtil;
import com.itsq.utils.http.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/playerBoxArms")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "开箱记录模块")
public class PlayerBoxArmsController extends BaseController {
    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private Client client;
    @PostMapping("selectRechargeRecordDtoPage")
    @ApiOperation(value = "开箱记录-分页查询", notes = "", httpMethod = "POST")
    public Response<PagesUtil<PlayerBoxArms>> selectPlayerBoxArmsPage(@RequestBody PageParametersDto pageParametersDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( playerBoxArmsService.selectPlayerBoxArmsPage(pageParametersDto));
    }

    @PostMapping("selectUpRecord")
    @ApiOperation(value = "升级记录-分页查询", notes = "", httpMethod = "POST")
    public Response<PagesUtil<ArmsVo>> selectUpRecord(@RequestBody PageParametersDto pageParametersDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( playerBoxArmsService.selectUpRecord(pageParametersDto));
    }

    @PostMapping("updatePlayerBoxArms")
    @ApiOperation(value = "用户所属武器-修改", notes = "", httpMethod = "POST")
    public Response updatePlayerBoxArms(@RequestBody PlayerBoxArmsDtoUpd playerBoxArmsDtoUpd,  HttpServletRequest request){
       /* CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
       String concat="";


        int i = playerBoxArmsService.updatePlayerBoxArms(playerBoxArmsDtoUpd);

        if( i==0){
            return Response.fail("暂未获取到武器请稍后再试");
        }
        return Response.success();
    }

    @PostMapping("sellArms")
    @ApiOperation(value = "用户-售出所有武器", notes = "", httpMethod = "POST")
    public Response sellArms(@RequestBody PlayersSellDto playersSellDto,  HttpServletRequest request){
       /* CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        operationRecordService.addOperationRecord(new OperationRecord(playersSellDto.getId(),"出售武器","出售所有武器","/playerBoxArms/sellArms",1,client.getAddress(request.getRemoteAddr())));

        int i = playerBoxArmsService.sellArms(playersSellDto);

        if( i==0){
            return Response.fail("售出所有武器失敗");
        }
        return Response.success();
    }

}

