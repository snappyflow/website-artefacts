/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Kafka Consumer Service to consume the data from the Kafka topic.
 **/

package com.manager.provisionmanager.kafka;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.manager.provisionmanager.entity.Tasks;
import com.manager.provisionmanager.repository.TaskRepository;

@PropertySource("classpath:application.properties")
@Service
public class KafkaConsumerService {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

  @Autowired
  private TaskRepository taskRepository;

  @Value("${kafka.topic-name-consumer}")
  public String topicName;

  @KafkaListener(topics = "${kafka.topic-name-consumer}", groupId = "${kafka.group-id}", containerFactory = "kafkaListener")
  public void consume(Tasks queueTask) {

    LOG.info("Consumed Message (" + topicName + ") by manager : \n" + queueTask);

    Optional<Tasks> task = taskRepository.findById(queueTask.getId());

    LOG.info("Updating Task : ");

    if (queueTask.getWorker_id() == 0)
      queueTask.setWorker_id(task.get().getWorker_id());

    if (queueTask.getName() == null || queueTask.getName().isEmpty())
      queueTask.setName(task.get().getName());

    if (queueTask.getTargetCloud() == null || queueTask.getTargetCloud().isEmpty())
      queueTask.setTargetCloud(task.get().getTargetCloud());

    if (queueTask.getStatus() == null || queueTask.getStatus().isEmpty())
      queueTask.setStatus(task.get().getStatus());

    if (queueTask.getCreated() == null)
      queueTask.setCreated(task.get().getCreated());

    queueTask.setId(queueTask.getId());

    taskRepository.save(queueTask);

    LOG.info("Updated Task : \n" + queueTask);

  }

}
