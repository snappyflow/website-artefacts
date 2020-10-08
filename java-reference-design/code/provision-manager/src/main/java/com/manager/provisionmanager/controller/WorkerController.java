/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Worker Controller.
 **/

package com.manager.provisionmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manager.provisionmanager.common.AppConstants;
import com.manager.provisionmanager.entity.Counter;
import com.manager.provisionmanager.entity.SummaryCounter;
import com.manager.provisionmanager.exception.ResourceNotFoundException;
import com.manager.provisionmanager.service.ServerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/worker")
public class WorkerController {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerController.class);

  @GetMapping(value = "/get-all-counters", produces = "application/json")
  public SummaryCounter getAllCounters() throws Exception {

    LOG.info(this.getClass().getSimpleName() + " - Get all counters is invoked.");

    SummaryCounter counters = new SummaryCounter();

    int total, in_progress, completed, failed;
    total = in_progress = completed = failed = 0;

    List<Integer> worker_ids = new ArrayList<Integer>();

    for (String url : AppConstants.WORKER_GET_URI_LIST) {

      Object[] obj = ServerService.getStatus(url);
      String status = (String) obj[0];
      Counter result = (Counter) obj[1];

      if (status != "Green")
        continue;

      total += Integer.parseInt(result.getTotal());
      in_progress += Integer.parseInt(result.getIn_progress());
      completed += Integer.parseInt(result.getCompleted());
      failed += Integer.parseInt(result.getFailed());
      worker_ids.add((int) (long) result.getId());

    }

    counters.setTotal(total);
    counters.setIn_progress(in_progress);
    counters.setCompleted(completed);
    counters.setFailed(failed);
    counters.setWorker_ids(worker_ids);

    LOG.info("\nSummary Counter : " + counters + "\n");

    return counters;

  }

  @GetMapping(value = "/get-counters", produces = "application/json")
  public List<Counter> getCounters() throws Exception {

    LOG.info(this.getClass().getSimpleName() + " - Get counters is invoked.");

    List<Counter> results = new ArrayList<Counter>();

    for (String url : AppConstants.WORKER_GET_URI_LIST) {

      Object[] obj = ServerService.getStatus(url);
      String status = (String) obj[0];
      Counter result = (Counter) obj[1];

      if (status != "Green")
        continue;

      results.add(result);

    }

    return results;

  }

  @GetMapping(value = "/reset-counters", produces = "application/json")
  public String resetCounters() throws Exception {

    LOG.info(this.getClass().getSimpleName() + " - Reset counters is invoked.");

    for (String url : AppConstants.WORKER_RESET_URI_LIST) {

      Object[] obj = ServerService.getStatus(url);
      String status = (String) obj[0];

      if (status != "Green")
        continue;

    }

    return "All the counters have been resetted successfully!";

  }

  @GetMapping(value = "/get-counter/{id}", produces = "application/json")
  public Counter getCounter(@PathVariable int id) throws Exception {

    LOG.info(this.getClass().getSimpleName() + " - Get counter is invoked.");

    List<Counter> results = new ArrayList<Counter>();

    for (String url : AppConstants.WORKER_GET_URI_LIST) {

      Object[] obj = ServerService.getStatus(url);
      String status = (String) obj[0];
      Counter result = (Counter) obj[1];

      if (status != "Green")
        continue;

      results.add(result);

    }

    Counter result = new Counter();

    boolean found = false;

    for (Counter item : results) {
      if (item.getId() == id) {
        found = true;
        result = item;
      }
    }

    if (!found)
      throw new ResourceNotFoundException("Could not find worker with id - " + id);

    return result;

  }

  @GetMapping(value = "/reset-counter/{id}", produces = "application/json")
  public String resetCounter(@PathVariable int id) throws Exception {

    LOG.info(this.getClass().getSimpleName() + " - Reset counter is invoked.");

    boolean result = true;

    for (String url : AppConstants.WORKER_RESET_URI_LIST) {

      Object[] obj = ServerService.getStatus(url + "/" + id);
      String status = (String) obj[0];
      Counter res = (Counter) obj[1];

      if (status != "Green")
        continue;

      if (res.getId() == id && res.getTotal().equals("0")) {

        result = true;
        break;

      } else {
        result = false;
      }

    }

    if (!result)
      return "Could not find worker with id - " + id;

    return "The counter for worker with ID : " + id + " is resetted successfully!";

  }

}
