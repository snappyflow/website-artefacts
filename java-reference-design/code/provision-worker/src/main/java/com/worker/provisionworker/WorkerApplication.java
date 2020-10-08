/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Staring point of the application.
 **/

package com.worker.provisionworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.worker.provisionworker.common.AppConstants;
import com.worker.provisionworker.controller.StatusController;

@SpringBootApplication
public class WorkerApplication {

  private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

  public static void main(String[] args) {

    SpringApplication.run(WorkerApplication.class, args);

    LOG.info("Worker ID : " + AppConstants.WORKER_ID);
    LOG.info("JSON File : " + AppConstants.WORKER_STATUS_FILE_LOCATION);

  }

}
