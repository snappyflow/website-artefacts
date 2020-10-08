/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Represents a JSON to maintain task status in a JSON file.
 **/

package com.worker.provisionworker.entity;

public class Counter {

  private long id;
  private long total;
  private long in_progress;
  private long completed;
  private long failed;

  public Counter(long id, long total, long in_progress, long completed, long failed) {
    this.id = id;
    this.total = total;
    this.in_progress = in_progress;
    this.completed = completed;
    this.failed = failed;
  }

  public Counter() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public long getIn_progress() {
    return in_progress;
  }

  public void setIn_progress(long in_progress) {
    this.in_progress = in_progress;
  }

  public long getCompleted() {
    return completed;
  }

  public void setCompleted(long completed) {
    this.completed = completed;
  }

  public long getFailed() {
    return failed;
  }

  public void setFailed(long failed) {
    this.failed = failed;
  }

  @Override
  public String toString() {
    return "Counter : {" + "id=" + this.id + ", total=" + this.total + ", in_progress=" + this.in_progress + ", completed="
      + this.completed + ", failed=" + this.failed + "}";
  }

}
