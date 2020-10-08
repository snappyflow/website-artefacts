/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Represents a JSON message to be sent to the Kafka Topic.
 **/

package com.worker.provisionworker.entity;

public class Notification {

  private long id;
  private long worker_id;
  private String status;

  public Notification(long id, long worker_id, String status) {
    this.id = id;
    this.worker_id = worker_id;
    this.status = status;
  }

  public Notification() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getWorker_id() {
    return worker_id;
  }

  public void setWorker_id(long worker_id) {
    this.worker_id = worker_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Task {" + "id=" + id + ", worker_id=" + worker_id + ", status=" + status + "}";
  }

}
