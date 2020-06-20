package com.itsq.service.mail;

/**
 * @author sq
 * @date 2020/2/22  16:31
 */
public interface MailTaskService {
    void sendSimpleMail();//简单的邮件
    void sendComplexMail(Long id);//复杂的邮件
}
