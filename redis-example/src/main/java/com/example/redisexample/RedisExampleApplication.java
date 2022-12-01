package com.example.redisexample;

import com.example.redisexample.model.AutowiringSpringBeanJobFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@EnableScheduling
@SpringBootApplication
public class RedisExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisExampleApplication.class, args);
	}

}
