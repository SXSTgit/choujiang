package com.itsq.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sq
 * @date 2020/2/21  17:31
 */
@Component
public class ScheduleJobTask {
    /**
     * 定时任务
     * */
    /*
    * fixedDelay每隔多久执行一次 单位毫秒
    * */
    @Scheduled(fixedDelay = 3000)
    public void sendEmail(){
        System.out.println("发送邮件");
    }
}
