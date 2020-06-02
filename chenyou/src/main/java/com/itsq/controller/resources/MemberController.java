package com.itsq.controller.resources;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.pojo.dto.FindPageManagerParmeters;
import com.itsq.pojo.dto.MemberPageDto;
import com.itsq.pojo.entity.Member;
import com.itsq.service.resources.ManagerService;
import com.itsq.service.resources.MemberService;
import com.itsq.token.CurrentUser;
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
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/member")
@AllArgsConstructor
@CrossOrigin
@Api(tags = "会员模块")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;


    @PostMapping("selectMemberPage")
    @ApiOperation(value = "会员-分页查询", notes = "", httpMethod = "POST")
    public Response<Page> selectMemberPage(@RequestBody  MemberPageDto memberPageDto){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }
        return Response.success( memberService.selectMemberPage(memberPageDto.getUserName(),memberPageDto.getPhone(),memberPageDto.getManagerId(),memberPageDto.getUserId(),memberPageDto.getPageIndex(), memberPageDto.getPageSize()));
    }

    @PostMapping("addMember")
    @ApiOperation(value = "会员-添加", notes = "", httpMethod = "POST")
    public Response addMember(@RequestBody Member member){
       CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }

        int i = memberService.addMember(member);
        if( i<=0){
            return Response.fail("添加失败");
        }
        return Response.success();
    }

    @PostMapping("updateMemberById")
    @ApiOperation(value = "会员-修改", notes = "", httpMethod = "POST")
    public Response updateMemberById(@RequestBody Member member){
       CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }

        int i = memberService.updateMemberById(member);

        if( i<=0){
            return Response.fail("修改失败");
        }
        return Response.success();
    }

    @PostMapping("selectById")
    @ApiOperation(value = "会员-查询", notes = "", httpMethod = "POST")
    public Response selectById(Integer id){
        CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }




        return Response.success(memberService.selectById(id));
    }


    @PostMapping("removeById")
    @ApiOperation(value = "会员-查询", notes = "", httpMethod = "POST")
    public Response removeById(Integer id){
       CurrentUser currentUser = currentUser();
        if(currentUser==null){
            return Response.fail(ErrorEnum.SIGN_VERIFI_EXPIRE);
        }


        boolean b = memberService.removeById(id);

        return Response.success(b);
    }

}

