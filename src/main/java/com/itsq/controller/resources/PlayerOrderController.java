package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.PageParametersDto;
import com.itsq.pojo.dto.PlayerOrderDto;
import com.itsq.pojo.entity.PlayerBoxArms;
import com.itsq.pojo.entity.PlayerOrder;
import com.itsq.service.resources.PlayerOrderService;
import com.itsq.utils.PagesUtil;
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
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/playerOrder")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "取回模块")
public class PlayerOrderController extends BaseController {



    @Autowired
    private PlayerOrderService playerOrderService;


    @PostMapping("selectPagePlayerOrder")
    @ApiOperation(value = "取回记录-分页查询", notes = "", httpMethod = "POST")
    public Response<Page<PlayerOrder>> selectPagePlayerOrder(@RequestBody PlayerOrderDto playerOrderDto){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        return Response.success( playerOrderService.selectPagePlayerOrder(playerOrderDto));
    }


}

