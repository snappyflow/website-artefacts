/**
 * Copyright 2020 MapleLabs
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Summary Counter.
 **/

package com.manager.provisionmanager.entity;

import java.util.List;

public class SummaryCounter {

  private int total;
  private int in_progress;
  private int completed;
  private int failed;

  private List<Integer> worker_ids;

  public SummaryCounter(int total, int in_progress, int completed, int failed, List<Integer> worker_ids) {
    this.total = total;
    this.in_progress = in_progress;
    this.completed = completed;
    this.failed = failed;
    this.worker_ids = worker_ids;
  }

  public SummaryCounter() {

  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getIn_progress() {
    return in_progress;
  }

  public void setIn_progress(int in_progress) {
    this.in_progress = in_progress;
  }

  public int getCompleted() {
    return completed;
  }

  public void setCompleted(int completed) {
    this.completed = completed;
  }

  public int getFailed() {
    return failed;
  }

  public void setFailed(int failed) {
    this.failed = failed;
  }

  public List<Integer> getWorker_ids() {
    return worker_ids;
  }

  public void setWorker_ids(List<Integer> worker_ids) {
    this.worker_ids = worker_ids;
  }

  @Override
  public String toString() {
    return "SummaryCounter {" + "total=" + total + ", in_progress=" + in_progress + ", completed=" + completed + ", failed="
      + failed + ", worker_ids=" + worker_ids + "}";
  }

}
