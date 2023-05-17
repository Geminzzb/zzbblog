package com.zzb.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author: zhangzongbin
 * @date: 2023/3/18 - 21:26
 * @mail: 2218722664@qq.com
 * @info:
 */
//@Component

public class TestJob {

    @Scheduled(cron = "* 0/1 * * * ?")
    public void testjob(){
        //执行的代码
        System.out.println("自动执行");
    }
}
