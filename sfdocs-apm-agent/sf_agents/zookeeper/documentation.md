##### Metric

##### Description

Zookeeper Metric plugin is an agent-based plugin . It helps in analyzing the efficiency of zookeeper infrastructure by providing key metrics like node count, packet count, latency, watch count .

##### Prerequisites

- Zookeeper Plugin is based on Jolokia agent which requires JMX monitoring to be enable locally. Following property needs to be included during the start of Zookeeper process

  ```
  -Dcom.sun.management.jmxremote
  ```

- JCMD command must be installed in the machine

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: zookeeperjmx</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>documentsTypes:</div>
      <div>- jmxStats</div>
      <div>- zookeeperStats</div>
    </div>
  </div>
</div>

##### Operating Instructions

Mention the documentTypes required under *documentsTypes.* 
Available options are jmxStats ,zookeeperStats

##### Documents

Plugin consists of  documents organized into following types 

- Zookeeper stats: contain zookeeper server related metrics 

- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

Kafka dashboard template in APM is integrated with zookeeper metric visualization

------

##### Logger

##### Description

Zookeeper logger plugin collects general server logs 

##### Prerequisites

Zookeeper ships with log4j support .Log4j property file (log4j.properties) will be present in root folder of Zookeeper.
Log4j properties have to be set as follows 

- Enabling root logger and file appender where file appender can be of any type based on rolling strategy

  ```
  log4j.rootLogger=INFO, logfile
  log4j.appender.logFile=org.apache.log4j.DailyRollingFileAppender
  ```

- Specifying custom log file name along with its path , layout properties and data pattern

  ```
  log4j.appender.logFile.DatePattern='.'yyyy-MM-dd-HH
  log4j.appender.logFile.File= <..logpath..>
  log4j.appender.logFile.layout=org.apache.log4j.PatternLayout
  log4j.appender.logFile.layout.ConversionPattern=[%d] %p %m (%c)%n
  ```

After configuring log4j properties, emitted log would look like:

```
[2020-07-09 11:15:23,376] INFO Accepted socket connection from /10.233.115.193:34962 (org.apache.zookeeper.server.NIOServerCnxnFactory)
```

##### Configuration Settings

Mention the configured log file path in plugin configuration. Wildcard character supported.

<div class="sfpollerExample">
  <div> - name: zookeeper-general</div>
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

Zookeeper General logs come under log section of APM dashboard.



For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io)