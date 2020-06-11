package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.RechargeRecordDto;
import com.itsq.pojo.entity.RechargeRecord;
import com.itsq.service.resources.RechargeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/rechargeRecord")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "充值记录模块")
public class RechargeRecordController extends BaseController {
    @Autowired
    private RechargeRecordService rechargeRecordService;



    @PostMapping("selectRechargeRecordDtoPage")
    @ApiOperation(value = "充值记录-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> selectRechargeRecordDtoPage(@RequestBody RechargeRecordDto rechargeRecordDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( rechargeRecordService.selectRechargeRecordDtoPage(rechargeRecordDto));
    }
    @PostMapping("addRechargeRecord")
    @ApiOperation(value = "添加充值记录", notes = "", httpMethod = "POST")
    public Response addRechargeRecord(@RequestBody RechargeRecord rechargeRecord){

        this.rechargeRecordService.addRechargeRecord(rechargeRecord);
        return Response.success();
    }
}

