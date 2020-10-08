/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Staring point of the application.
 **/

package com.manager.provisionmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.manager.provisionmanager.common.AppConstants;

@SpringBootApplication
public class ManagerApplication {

  private static final Logger LOG = LoggerFactory.getLogger(ManagerApplication.class);

  public static void main(String[] args) {

    SpringApplication.run(ManagerApplication.class, args);

    LOG.info("Worker Servers/IPs : " + AppConstants.WORKER_SERVER_IP);
    LOG.info("Worker Get URI List : " + AppConstants.WORKER_GET_URI_LIST);
    LOG.info("Worker Reset URI List : " + AppConstants.WORKER_RESET_URI_LIST);

  }

}
