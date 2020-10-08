/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Kafka Consumer Service to consume the data from the Kafka topic.
 **/

package com.worker.provisionworker.kafka;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.worker.provisionworker.common.AppConstants;
import com.worker.provisionworker.entity.Notification;
import com.worker.provisionworker.entity.TaskDetails;

@PropertySource("classpath:application.properties")
@Service
public class KafkaConsumerService {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

  @Autowired
  KafkaProducerController kafkaProducerController;

  @Value("${kafka.topic-name-consumer}")
  public String topicName;

  @KafkaListener(topics = "${kafka.topic-name-consumer}", groupId = "${kafka.group-id}", containerFactory = "kafkaListener")
  public void consume(TaskDetails task) {

    LOG.info("Consumed Message (" + topicName + ") by worker : " + task);

    LOG.info("Provisioning " + task.getName() + " in " + task.getTargetCloud() + " ...");

    Notification notification = new Notification(task.getId(), AppConstants.WORKER_ID, task.getStatus());

    notification.setStatus("In Progress");

    kafkaProducerController.send(notification);

    try {

      Thread.sleep(AppConstants.SLEEP_TIME_IN_MILLISECONDS);

      LOG.info("Random Sleep Time for execution : " + AppConstants.SLEEP_TIME_IN_MILLISECONDS + " ms");

    } catch (InterruptedException e) {

      e.printStackTrace();

    }

    int success = AppConstants.SUCCESS_PERCENTAGE / 10;

    ArrayList<String> resultArray = new ArrayList<String>();

    for (int i = 0; i < success; i++) {
      resultArray.add("Completed");
    }

    int fail = 10 - success;

    for (int i = 0; i < fail; i++) {
      resultArray.add("Failed");
    }

    int random = new Random().nextInt(resultArray.size());

    LOG.info("Result Array for " + AppConstants.SUCCESS_PERCENTAGE + "% Success probability : " + resultArray);

    String result = resultArray.get(random);

    LOG.info("Final Status : " + result);

    notification.setStatus(result);

    kafkaProducerController.send(notification);

  }

}
