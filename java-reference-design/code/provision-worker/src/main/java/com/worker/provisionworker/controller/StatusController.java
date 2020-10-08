/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Status Controller.
 **/

package com.worker.provisionworker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worker.provisionworker.common.AppConstants;
import com.worker.provisionworker.entity.Counter;
import com.worker.provisionworker.service.StatusHelperFunctions;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class StatusController {

  private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

  StatusHelperFunctions helper = new StatusHelperFunctions();

  @GetMapping(value = "/get-counter", produces = "application/json")
  public Counter getCounter() {

    LOG.info(this.getClass().getSimpleName() + " - Get counter is invoked.");

    Counter jsonObject = helper.readJsonFile();

    return jsonObject;

  }

  @GetMapping(value = "/reset-counter/{id}", produces = "application/json")
  public Counter resetCounter(@PathVariable int id) {

    LOG.info(this.getClass().getSimpleName() + " - Reset counter is invoked.");

    Counter jsonObject = helper.readJsonFile();

    if (AppConstants.WORKER_ID == id) {

      helper.writeJsonFile(0, 0, 0, 0);

      jsonObject = helper.readJsonFile();

    }

    return jsonObject;

  }

  @GetMapping(value = "/reset-counter", produces = "application/json")
  public Counter resetCounter() {

    LOG.info(this.getClass().getSimpleName() + " - Reset counter is invoked.");

    Counter jsonObject = helper.readJsonFile();

    helper.writeJsonFile(0, 0, 0, 0);

    return jsonObject;

  }

}
