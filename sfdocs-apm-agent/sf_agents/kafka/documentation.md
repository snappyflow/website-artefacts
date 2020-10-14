##### Metric

##### Description

Kafka Metric plugin is an agent-based plugin .Plugin provides statistical information related to message traffic distribution and other internal transactions among the brokers .

##### Prerequisites

- Kafka Metric Plugin is based on Jolokia agent which requires JMX monitoring to be enable locally. Following property needs to be included during the start of kafka process

  ```
  -Dcom.sun.management.jmxremote
  ```

- JCMD command must be installed in the machine

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: kafkatopic</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>process: kafka.Kafka</div>
      <div>port: 9092</div>
      <div>documentsTypes:</div>
      <div>- kafkaStats</div>
      <div>- partitionStats</div>
      <div>- topicStats</div>
      <div>- consumerStats</div>
    </div>
  </div>
  <div> - name: kafkajmx</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>process: kafka.Kafka</div>
    </div>
  </div>
</div>

Kafkatopic plugin type collects metrics specific to kafka Internals and real time transactions . On the other hand, Kafkajmx plugin type collects jvm related informations

##### Configuring parameters

*process:* Kafka process name (It should be part of java main class)

*port:* Broker Port

##### Operating Instructions

Mention the documentTypes required under *documentsTypes.* 
Available options for plugin type kafkatopic are kafkaStats ,partitonStats ,topicStats ,consumerStats 

##### Documents

Plugin Type kafkatopic consists of  documents organized into following types 

- Kafka stats: contain transactional data and metrics related to broker state 
- Topic stats: provide metrics for analyzing internal transactions associated with each topic
- Partition stats:  provide log size and segment information for each partition 
- Consumer stats: provide consumer lag and offset information

Plugin Type kafkajmx contains single type of document 

- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

Use Kafka dashboard template in APM for visualization

------

##### Logger

##### Description

Kafka logger plugin collects general logs comprising state change and broker specific information

##### Prerequisites

Kafka ships with log4j support .Log4j property file (log4j.properties) will be present in root folder of kafka.
Log4j properties have to be set as follows 

- Enabling root logger and file appender where file appender can be of any type based on rolling strategy

  ```
  log4j.rootLogger=INFO, logfile
  log4j.appender.logFile=org.apache.log4j.DailyRollingFileAppender
  ```

- Specifying custom log file name along with its path , layout properties and data pattern

  ```
  log4j.appender.logFile.DatePattern='.'yyyy-MM-dd-HH
  log4j.appender.logFile.File=<..logpath..>
  log4j.appender.logFile.layout=org.apache.log4j.PatternLayout
  log4j.appender.logFile.layout.ConversionPattern=[%d] %p %m (%c)%n
  ```

After configuring log4j properties, emitted log would look like:

```
[2020-07-09 11:15:23,376] INFO [GroupCoordinator 1]: Group consgrp-tst with generation 1588 is now empty (__consumer_offsets-5) (kafka.coordinator.group.GroupCoordinator)
```

##### Configuration Settings

Mention the configured log file path in plugin configuration. Wildcard character supported.

<div class="sfpollerExample">
  <div> - name: kafka-general</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_path: <..logpath..>
      </div>
      <div>log_level:</div>
      <div>- error</div>
      <div>- warning</div>
      <div>- info</div>
      <div>- log</div>
    </div>
  </div>
</div>

##### Documents

Kafka General logs come under log section of APM dashboard.



For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io)