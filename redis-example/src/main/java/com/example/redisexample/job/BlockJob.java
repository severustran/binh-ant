package com.example.redisexample.job;

import com.example.redisexample.entity.Coin;
import com.example.redisexample.service.impl.CoinServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Slf4j
@Service
public class BlockJob implements Job {

    @Autowired
    private CoinServiceImpl coinService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        coinService.print();
    }


//    @Override
//    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//        log.info("INNNNN");
//        coinService.print();
////        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
////            System.out.println("*****RUN JOB*****");
////            Coin coin = new Coin();
////            coin.setId(1);
////            coin.setName("XRP");
////            coin.setLatestblock(1);
////            coinService.saveCoin(coin);
//        }
}

