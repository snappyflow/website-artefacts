package com.manager.provisionmanager.entity;

public class TaskDetails {

  private long id;
  private String name;
  private String targetCloud;
  private String status;

  public TaskDetails(long id, String name, String targetCloud, String status) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.targetCloud = targetCloud;
  }

  public TaskDetails() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTargetCloud() {
    return targetCloud;
  }

  public void setTargetCloud(String targetCloud) {
    this.targetCloud = targetCloud;
  }

  @Override
  public String toString() {
    return "Task {" + "id=" + id + ", name='" + name + '\'' + ", targetCloud='" + targetCloud + '\'' + ", status='" + status
      + '\'' + '}';
  }

}
