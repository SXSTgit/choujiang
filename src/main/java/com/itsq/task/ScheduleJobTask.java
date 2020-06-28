package com.itsq.task;

import com.itsq.pojo.entity.Box;
import com.itsq.service.resources.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author sq
 * @date 2020/2/21  17:31
 */
@Component
public class ScheduleJobTask {
    @Autowired
   private BoxService boxService;

    /**
     * 定时任务
     * */
    /*
    * fixedDelay每隔多久执行一次 单位毫秒
    * */
   // @Scheduled(fixedDelay = 3000)
    public void sendEmail(){
        System.out.println("发送邮件");
    }

    @Scheduled(cron = "0 0 0 * * ? ")
 //  @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateBoxDato(){
        List<Box> boxes = boxService.selectListBox();
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");

        Date date = new Date();
        for (Box box : boxes) {

            if(box.getOutTime()!=null){
                if(date.after(box.getOutTime())){
                    box.setIsStatus(1);
                    boxService.updateBoxUpdateById(box);
                }
            }
        }
    }

}
