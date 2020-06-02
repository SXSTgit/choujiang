package com.itsq.service.mail.impl;

import com.itsq.mapper.EmailMapper;
import com.itsq.pojo.entity.EmailEntity;
import com.itsq.service.mail.MailTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author sq
 * @date 2020/2/22  15:52
 */
@Slf4j
@Component
public class MailTaskServiceImpl implements MailTaskService {
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;

    @Autowired(required = false)
    private EmailMapper emailMapper;

    @Autowired(required = false)
    JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail() {
        //简单的邮件
        //邮件发送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("主题");
        message.setText("文本内容");
        message.setTo("1366033452@qq.com");//发送给谁
        message.setFrom("1366033452@qq.com");//谁发送
        javaMailSender.send(message);
    }

    @Override
    public void sendComplexMail(Long id) {
        //根据ID查询信息
        EmailEntity emailEntity = emailMapper.selectById(id);
        //发送一个复杂的邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //组装
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setSubject(emailEntity.getTitle());
            //正文
            mimeMessageHelper.setText(emailEntity.getContent(),true);
            //附件
            if(emailEntity.getFujian()!=null && !"".equals(emailEntity)){
                mimeMessageHelper.addAttachment("附件.zip", new File(emailEntity.getFujian()));
            }
           // mimeMessageHelper.addAttachment("2.jpg", new File("C:\\Users\\13660\\Desktop\\1.jpg"));
            mimeMessageHelper.setTo(emailEntity.getToEmails());//发送给谁
            mimeMessageHelper.setFrom(username);//谁发送
        } catch (MessagingException e) {
            log.error("sendComplexMail send error{}", e);
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
        //修改邮件状态
        EmailEntity entity = new EmailEntity();
        entity.setSendStatus(1);
        entity.setId(id);
        emailMapper.updateById(entity);
        log.info("发送成功接受者:"+emailEntity.getToEmails());
    }
}
