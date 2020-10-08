/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Common application constants.
 **/

package com.worker.provisionworker.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service
public class AppConstants {

  public static int SLEEP_TIME_IN_MILLISECONDS = 0;
  public static int SUCCESS_PERCENTAGE = 0;

  public static long WORKER_ID = 0;

  public static String WORKER_STATUS_FILE_LOCATION = "";

  AppConstants(@Value("${task.sleep-time-in-milliseconds}") int SLEEP_TIME_IN_MILLISECONDS,

    @Value("${task.success-percentage}") int SUCCESS_PERCENTAGE,

    @Value("${worker.id}") long WORKER_ID,

    @Value("${worker.status-file-location}") String WORKER_STATUS_FILE_LOCATION) {

    AppConstants.SLEEP_TIME_IN_MILLISECONDS = SLEEP_TIME_IN_MILLISECONDS;

    AppConstants.SUCCESS_PERCENTAGE = SUCCESS_PERCENTAGE;

    AppConstants.WORKER_ID = WORKER_ID;

    AppConstants.WORKER_STATUS_FILE_LOCATION = WORKER_STATUS_FILE_LOCATION + "-" + WORKER_ID + ".json";

  }

}
