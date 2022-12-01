package com.example.redisexample.config;

import com.example.redisexample.model.SchedulerJobFactory;
import com.example.redisexample.ultil.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class QuartzSchedulerConfig {

    private final DataSource dataSource;
    private final ApplicationContext context;
    private final QuartzProperties quartzProperties;
    private final QuartzUtils quartzUtils;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(context);

        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setDataSource(dataSource);
        factoryBean.setQuartzProperties(properties);
        factoryBean.setJobFactory(jobFactory);
        return factoryBean;
    }

    @Bean
    public Scheduler scheduler(Map<String, JobDetail> jobMap, Set<? extends Trigger> triggers) {
        try {
            SchedulerFactoryBean schedulerFactory = schedulerFactoryBean();
            Scheduler scheduler = schedulerFactory.getScheduler();
            Map<JobDetail, Set<? extends Trigger>> triggerAndJobs = new HashMap<>();
            Set<Trigger> jobTrigger;
            for (JobDetail job : jobMap.values()) {
                jobTrigger = triggers.stream()
                        .filter(tr -> quartzUtils.jobOfTrigger(tr, job.getKey())
                                && !quartzUtils.isJobAlreadySchedule(job.getKey(), scheduler))
                        .collect(Collectors.toSet());
                if (!jobTrigger.isEmpty()) {
                    triggerAndJobs.put(job, jobTrigger);
                }
            }

            scheduler.scheduleJobs(triggerAndJobs, true);
            scheduler.start();
            scheduler.getListenerManager().addJobListener(new GlobalJobListener());
            scheduler.getListenerManager().addTriggerListener(new GlobalTriggerListener());
            return scheduler;
        } catch (SchedulerException e) {
            log.error("Error when scheduler job", e);
            return null;
        }
    }

}
