/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Kafka Producer Controller to produce/send data to the Kafka topic.
 **/

package com.worker.provisionworker.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.worker.provisionworker.entity.Notification;
import com.worker.provisionworker.service.StatusHelperFunctions;

@PropertySource("classpath:application.properties")
@Service
public class KafkaProducerController {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerController.class);

  @Autowired
  KafkaTemplate<String, Notification> KafkaJsontemplate;

  StatusHelperFunctions helper = new StatusHelperFunctions();

  @Value("${kafka.topic-name-producer}")
  public String topicName;

  public void send(Notification notification) {

    KafkaJsontemplate.send(topicName, notification);

    LOG.info("Produced Message (" + topicName + ") by worker : " + notification);

    helper.updateJsonFile(notification.getStatus());

  }

}
