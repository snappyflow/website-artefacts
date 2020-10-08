/**
 * Copyright 2020 MapleLabs
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Counter Model.
 **/

package com.manager.provisionmanager.entity;

public class Counter {

  private long id;
  private String total;
  private String in_progress;
  private String completed;
  private String failed;

  public Counter(long id, String total, String in_progress, String completed, String failed) {
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

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getIn_progress() {
    return in_progress;
  }

  public void setIn_progress(String in_progress) {
    this.in_progress = in_progress;
  }

  public String getCompleted() {
    return completed;
  }

  public void setCompleted(String completed) {
    this.completed = completed;
  }

  public String getFailed() {
    return failed;
  }

  public void setFailed(String failed) {
    this.failed = failed;
  }

  @Override
  public String toString() {
    return "Counter {" + "id=" + id + ", total=" + total + ", in_progress=" + in_progress + ", completed=" + completed
      + ", failed=" + failed + '}';
  }

}
