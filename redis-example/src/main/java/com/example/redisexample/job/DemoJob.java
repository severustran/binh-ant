package com.example.redisexample.job;

import com.example.redisexample.service.DemoSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class DemoJob extends QuartzJobBean {

    @Autowired
    private DemoSchedulerService schedulerService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        schedulerService.run();
    }
    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        schedulerService.run();
    }
}
