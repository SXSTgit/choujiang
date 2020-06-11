package com.itsq.controller.resources;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.RechargeRecordDto;
import com.itsq.pojo.entity.Site;
import com.itsq.service.resources.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/site")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "站点模块")
public class SiteController extends BaseController {


    @Autowired
    private SiteService siteService;


    @PostMapping("getOneSite")
    @ApiOperation(value = "站点-查询", notes = "", httpMethod = "POST")
    public Response<Site> getOneSite(){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        QueryWrapper queryWrapper=new QueryWrapper();
        return Response.success( siteService.getOne(queryWrapper));
    }

    @PostMapping("updateByIdSite")
    @ApiOperation(value = "站点-查询", notes = "", httpMethod = "POST")
    public Response updateByIdSite(@RequestBody Site site){
        /*CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }*/
        boolean b = siteService.updateById(site);
        if(b){
            return Response.success("修改成功");
        }
        return Response.fail("修改站点失败" );
    }



}

