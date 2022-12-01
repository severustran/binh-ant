package com.example.redisexample.ultil;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class QuartzUtils {

    public CronTrigger createCronTrigger(JobDetail jobDetail, TriggerKey triggerKey, String cronExp, Date startTime) {
        return TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobDetail).startAt(startTime)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp).withMisfireHandlingInstructionFireAndProceed())
                .build();
    }

    public SimpleTrigger createSimpleTrigger(JobDetail jobDetail, Long repeat, Date startTime) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setRepeatInterval(repeat);
        simpleTriggerFactoryBean.setStartTime(startTime);
        return simpleTriggerFactoryBean.getObject();
    }

    public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
                               ApplicationContext context, JobKey jobKey) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setApplicationContext(context);
        factoryBean.setJobClass(jobClass);
        factoryBean.setName(jobKey.getName());
        factoryBean.setGroup(jobKey.getGroup());
        factoryBean.setDurability(isDurable);
        //Set job data map

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobKey.getName() + jobKey.getGroup(), jobClass.getName());
        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
    public boolean jobOfTrigger(Trigger trigger, JobKey jobKey) {
        return trigger.getKey().compareTo(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup())) == 0;
    }

    public boolean isJobAlreadySchedule(JobKey jobKey, Scheduler scheduler) {
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            log.error("An error occurs while check exits of trigger key", e);
            return false;
        }
    }

}
