package com.example.redisexample.config;

import com.example.redisexample.job.BlockJob;
import com.example.redisexample.model.AutowiringSpringBeanJobFactory;
import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Objects;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private QuartzProperties quartzProperties;

    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {

        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        AutowiringSpringBeanJobFactory jobFactory1 = new AutowiringSpringBeanJobFactory();
        jobFactory1.setApplicationContext(applicationContext);
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setAutoStartup(true);
//        factory.setDataSource(dataSource);
        quartzScheduler.setQuartzProperties(properties);
        quartzScheduler.setJobFactory(jobFactory1);
        CronTrigger triggers = procesoMQTrigger().getObject();
        quartzScheduler.setTriggers(triggers);
        return quartzScheduler;
    }

    @Bean
    public JobDetailFactoryBean procesoMQJob() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(BlockJob.class);
        jobDetailFactory.setGroup("spring3-quartz");
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean procesoMQTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(procesoMQJob().getObject()));
        cronTriggerFactoryBean.setCronExpression("* * * ? * *");
        cronTriggerFactoryBean.setGroup("spring3-quartz");
        return cronTriggerFactoryBean;
    }
}
