package com.example.redisexample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoSchedulerService {
    public void run() {
        log.info("Job is running");
    }
}
