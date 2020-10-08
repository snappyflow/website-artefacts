/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Aanchal Sathyanarayanan (aanchal.sathyanarayanan@maplelabs.com)
 * 
 * Description: Represents a JSON message to be sent to the Kafka Topic.
 **/

package com.manager.provisionmanager.entity;

import java.sql.Timestamp;
import java.util.Hashtable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "tasktable")
public class Tasks {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long worker_id;

  @Column(name = "task_name", nullable = false)
  private String name;

  @Column(name = "task_status")
  private String status;

  @Column(name = "target_Cloud", nullable = false)
  private String targetCloud;

  @Column(name = "provision_Details")
  Hashtable<String, String> provisionDetails = new Hashtable<String, String>();

  @CreationTimestamp
  @Column(name = "created")
  private java.sql.Timestamp created;

  @UpdateTimestamp
  @Column(name = "updated")
  private java.sql.Timestamp updated;

  public Tasks() {
    this(null, null, null, null, null, null, 0);
  }

  public Tasks(String name, String status, String targetCloud, Hashtable<String, String> provisionDetails, Timestamp created,
    Timestamp updated, long worker_id) {
    super();
    this.name = name;
    this.status = status;
    this.targetCloud = targetCloud;
    this.provisionDetails = provisionDetails;
    this.created = created;
    this.updated = updated;
    this.worker_id = worker_id;
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

  public Hashtable<String, String> getProvisionDetails() {
    return provisionDetails;
  }

  public void setProvisionDetails(Hashtable<String, String> provisionDetails) {
    this.provisionDetails = provisionDetails;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public Timestamp getUpdated() {
    return updated;
  }

  public void setUpdated(Timestamp updated) {
    this.updated = updated;
  }

  public String apiResponse() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Tasks responseTask = new Tasks();
    responseTask.setId(id);
    responseTask.setName(name);
    responseTask.setStatus(status);
    responseTask.setTargetCloud(targetCloud);

    String jsonString = gson.toJson(responseTask);
    return jsonString;
  }

  @Override
  public String toString() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Tasks task = new Tasks();
    task.setId(id);
    task.setName(name);
    task.setStatus(status);
    task.setTargetCloud(targetCloud);
    task.setCreated(created);

    String jsonString = gson.toJson(task);
    return jsonString;
  }

}
