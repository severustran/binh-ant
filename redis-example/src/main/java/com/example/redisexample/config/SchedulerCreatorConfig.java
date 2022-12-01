package com.example.redisexample.config;

import com.example.redisexample.job.DemoJob;
import com.example.redisexample.ultil.QuartzUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class SchedulerCreatorConfig {
    private final ApplicationContext context;
    private final QuartzUtils quartzUtils;

    @Bean(name = "demoJob")
    public JobDetail demoJob() {
        JobKey jobKey = JobKey.jobKey("demo_job_jn", "demo_job_jg");
        return quartzUtils.createJob(DemoJob.class, false, context, jobKey);
    }

    @Bean(name = "demoJobTrigger")
    public CronTrigger demoJobTrigger(@Qualifier("demoJob") JobDetail jobDetail) {
        TriggerKey triggerKey = TriggerKey.triggerKey("demo_job_jn", "demo_job_jg");
        //every second
        String cronExp = "0/3 0 0 ? * * *";
        return quartzUtils.createCronTrigger(jobDetail, triggerKey, cronExp, new Date());
    }
}
