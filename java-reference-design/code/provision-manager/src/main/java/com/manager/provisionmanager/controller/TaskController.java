package com.manager.provisionmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manager.provisionmanager.entity.TaskDetails;
import com.manager.provisionmanager.entity.Tasks;
import com.manager.provisionmanager.exception.CustomException;
import com.manager.provisionmanager.exception.ResourceNotFoundException;
import com.manager.provisionmanager.repository.TaskRepository;

import io.micrometer.core.instrument.util.StringUtils;

@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/provision")
public class TaskController {

  public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  KafkaTemplate<String, TaskDetails> KafkaJsontemplate;

  @Value("${kafka.topic-name-producer}")
  public String topicName;

  @GetMapping(value = "/all")
  @ResponseStatus(HttpStatus.OK)
  Page<Tasks> getTasks(Pageable pageable) {

    logger.info(this.getClass().getSimpleName() + " - Get tasks service is invoked.");

    return this.taskRepository.findAll(pageable);

  }

  // get all tasks
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Tasks>> getAllTasks() {
    List<Tasks> tasks = taskRepository.findAll();
    if (tasks.isEmpty()) {
      logger.error("Status: " + HttpStatus.OK + "\nNo Content Found");
      return new ResponseEntity(new CustomException("No Content Found"), HttpStatus.OK);
      // You many return HttpStatus.NOT_FOUND
    }
    return new ResponseEntity<List<Tasks>>(tasks, HttpStatus.OK);
  }

  // get task by id
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Tasks getTaskById(@PathVariable(value = "id") long taskId) {
    return this.taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found with id :" + taskId));
  }

  // post new task
  @RequestMapping(value = " ", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> createTask(@RequestBody Tasks task) {
    logger.info("Creating Task : \n{}", task);
    long id = task.getId();

    if (taskRepository.findById(id) != null && id != 0) { // id!=0 means user has entered some id cz id is set
      // automatically by taskRepository.save
      logger.error("Status: " + HttpStatus.BAD_REQUEST + "\nID parameter not allowed as input by user");
      return new ResponseEntity(new CustomException("ID parameter not allowed as input by user. Unable to create task."),
        HttpStatus.BAD_REQUEST);
    } else if (StringUtils.isBlank(task.getName()) || StringUtils.isBlank(task.getTargetCloud())) {
      logger.error("Status: " + HttpStatus.BAD_REQUEST + "\nUnable to create task. Values found to be null");
      return new ResponseEntity(new CustomException("Unable to create task. Values found to be null"),
        HttpStatus.BAD_REQUEST);
    } else {
      task.setStatus("Scheduled");
      this.taskRepository.save(task);
      TaskDetails taskDetails = new TaskDetails(task.getId(), task.getName(), task.getTargetCloud(), task.getStatus());

      if ((KafkaJsontemplate.send(topicName, taskDetails)) != null) {
        logger.info("Status: " + HttpStatus.ACCEPTED + "\nProduced Message (" + topicName + ") by manager: \n" + task);
        return new ResponseEntity<>(task.apiResponse(), HttpStatus.ACCEPTED);
      }
      // check if Kafka exception exists
      else {
        return new ResponseEntity<>("Status: " + HttpStatus.REQUEST_TIMEOUT + "\nKafka Request Timed Out",
          HttpStatus.REQUEST_TIMEOUT);
      }
    }
  }

  // update task by id
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> updateTask(@RequestBody Tasks task, @PathVariable("id") long taskId) {
    Tasks existingTask = this.taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found with id :" + taskId));

    if (StringUtils.isNotBlank(task.getStatus()) || task.getId() != 0) {
      logger.info("Invalid inputs. You cant modify these fields.");
      return new ResponseEntity<>("Invalid inputs. You cant modify these fields.", HttpStatus.BAD_REQUEST);
    }
    // check if the user has entered the task name or not
    if (StringUtils.isBlank(task.getName())) {
      existingTask.setName(existingTask.getName());
    } else {
      existingTask.setName(task.getName());
    }
    // check if the user has entered the target cloud or not
    if (StringUtils.isBlank(task.getTargetCloud())) {
      existingTask.setTargetCloud(existingTask.getTargetCloud());
    } else {
      existingTask.setTargetCloud(task.getTargetCloud());
    }
    logger.info("Status: " + HttpStatus.OK + "\nTask Updated:\n" + task);
    this.taskRepository.save(existingTask);
    return new ResponseEntity<>(existingTask.toString(), HttpStatus.OK);
  }

  // delete task by id
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTask(@PathVariable("id") long taskId) {
    Tasks existingTask = this.taskRepository.findById(taskId)
      .orElseThrow(() -> new ResourceNotFoundException("Task not found with id :" + taskId));
    this.taskRepository.delete(existingTask);
    logger.info("Status: " + HttpStatus.OK + "\nTask with id " + taskId + " has been Deleted");
    return new ResponseEntity<>("Task with id " + taskId + " has been Deleted", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-all", produces = "application/json")
  public String deleteAll() {

    logger.info(this.getClass().getSimpleName() + " - Delete all tasks is invoked.");

    this.taskRepository.deleteAll();

    return "All the tasks have been deleted successfully!";

  }

}
