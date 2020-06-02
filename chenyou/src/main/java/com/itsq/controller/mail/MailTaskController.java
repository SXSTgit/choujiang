package com.itsq.controller.mail;


import com.itsq.common.bean.Response;
import com.itsq.service.mail.MailTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sq
 * @date 2020/2/27  17:11
 */
@RequestMapping("/api/mail")
@Api(tags = "邮件管理")
@RestController
@CrossOrigin
public class MailTaskController {
    private static final Logger log = LoggerFactory.getLogger(MailTaskController.class);

    @Autowired(required = false)
    private MailTaskService mailTaskService;

    @GetMapping("sendComplexMail")
    @ApiOperation(value = "邮件管理-HTML模式的邮件发送", notes = "", httpMethod = "GET")
    @ApiImplicitParams({
    })
    public Response sendComplexMail(Long id) {
        mailTaskService.sendComplexMail(id);
        return Response.success();
    }

//    @GetMapping("sendSimpleMail")
//    @ApiOperation(value = "邮件管理-纯文字式邮箱发送", notes = "", httpMethod = "GET")
//    @ApiImplicitParams({
//    })
//    public String sendSimpleMail() {
//        try {
//            mailTaskService.sendSimpleMail();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("纯文字式邮箱发送异常 调用方法:sendSimpleMail", e);
//        }
//        return "ok";
//    }
}
