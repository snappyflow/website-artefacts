/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Status Helper Functions : read, write, update JSON file.
 **/

package com.worker.provisionworker.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.worker.provisionworker.common.AppConstants;
import com.worker.provisionworker.entity.Counter;

public class StatusHelperFunctions {

  private static final Logger LOG = LoggerFactory.getLogger(StatusHelperFunctions.class);

  public Counter readJsonFile() {

    File file1 = new File(AppConstants.WORKER_STATUS_FILE_LOCATION);

    if (file1.length() == 0) {

      LOG.info("File is empty !!!");

      writeJsonFile(0, 0, 0, 0);

    }

    Counter jsonObject = new Counter();

    try {

      ObjectMapper objectMapper = new ObjectMapper();

      jsonObject = objectMapper.readValue(new File(AppConstants.WORKER_STATUS_FILE_LOCATION), Counter.class);

      LOG.info("Reading JSON file : " + jsonObject);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return jsonObject;

  }

  public void writeJsonFile(long total, long in_progress, long completed, long failed) {

    Counter obj = new Counter();

    obj.setId(AppConstants.WORKER_ID);
    obj.setTotal(total);
    obj.setIn_progress(in_progress);
    obj.setCompleted(completed);
    obj.setFailed(failed);

    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    try {

      objectMapper.writeValue(new File(AppConstants.WORKER_STATUS_FILE_LOCATION), obj);

    } catch (IOException e) {
      e.printStackTrace();
    }

    LOG.info("Writing into JSON File : " + obj);

  }

  public void updateJsonFile(String currentStatus) {

    Counter jsonObject = readJsonFile();

    long total_count = jsonObject.getTotal();
    long progress_count = jsonObject.getIn_progress();
    long completed_count = jsonObject.getCompleted();
    long failed_count = jsonObject.getFailed();

    String status = currentStatus;

    if (status == "In Progress") {

      progress_count = (long) progress_count + 1;

    } else if (status == "Completed") {

      completed_count = (long) completed_count + 1;
      progress_count = (long) progress_count - 1;

    } else if (status == "Failed") {

      failed_count = (long) failed_count + 1;
      progress_count = (long) progress_count - 1;

    }

    total_count = (long) progress_count + (long) completed_count + (long) failed_count;

    writeJsonFile(total_count, progress_count, completed_count, failed_count);

  }

}
