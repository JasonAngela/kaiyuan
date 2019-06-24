package com.thinkgem.jeesite.modules.schedule;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import groovy.json.JsonBuilder;
import groovy.json.JsonOutput;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * TaskDna
 */
//@Service
//@Lazy(false)
public class TaskDna {

    @Autowired
    private SystemService systemService;

    //@Scheduled(cron = "0 */1 * * * ?")
    public void job1() {
        //"0 15 10 * * ? *" 每天上午10:15触发
        // 0 00 01 * * ? 每天凌晨1点开始执行
        //0/5 * * * * ? 每5秒执行一次
        //每隔1分钟执行一次  "0 */1 * * * ?"
        User user = systemService.getUserByLoginName("thinkgem");
        if(user != null){
            System.out.println(user.getMobile());
        }
        System.out.println("任务进行中");
    }
}
