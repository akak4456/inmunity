package com.akak4456.cron;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.akak4456.service.FileDeleteService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Log
public class DeleteScheduler {
	@Autowired
	private FileDeleteService service;
	@Scheduled(cron = "0 0 2 * * *")
	public void run() throws IOException {
		log.info("unnecessary file deleting...");
		service.deleteUnnecessaryFilePeriodically();
		log.info("unnecessary file deleted...");
	}
}
