package com.example.training.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GoogleTablesReportJob {

    @Scheduled(cron = "0 0 0 * * *")
    public void sendReport() {//todo create table

    }
}
