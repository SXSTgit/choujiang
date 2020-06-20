package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.OperationRecordDto;
import com.itsq.pojo.dto.PlayerOrderDto;
import com.itsq.pojo.entity.PlayerOrder;
import com.itsq.service.resources.OperationRecordService;
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
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/operationRecord")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "操作记录模块")
public class OperationRecordController extends BaseController {

    @Autowired
    private OperationRecordService operationRecordService;

    @PostMapping("selectPagePlayerOrder")
    @ApiOperation(value = "操作记录-分页查询", notes = "", httpMethod = "POST")
    public Response<Page<PlayerOrder>> selectPagePlayerOrder(@RequestBody OperationRecordDto operationRecordDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( operationRecordService.selectOperationRecord(operationRecordDto));
    }
}

