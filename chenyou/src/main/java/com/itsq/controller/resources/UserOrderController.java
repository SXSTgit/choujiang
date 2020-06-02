package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.MemberPageDto;
import com.itsq.pojo.dto.UserOrderPageDto;
import com.itsq.service.resources.MemberService;
import com.itsq.service.resources.UserOrderService;
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
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/userOrder")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "流水模块")
public class UserOrderController extends BaseController {
    @Autowired
    private UserOrderService userOrderService;


    @PostMapping("selectUserOrderPage")
    @ApiOperation(value = "会员流水-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> selectUserOrderPage(@RequestBody UserOrderPageDto userOrderPageDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( userOrderService.selectUserOrderPage(userOrderPageDto));
    }


}

