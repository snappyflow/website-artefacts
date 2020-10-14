##### Prometheus

##### Description

Plugin provides statistical information related to message traffic distribution and other internal transactions among the brokers.

##### Prerequisites

1. Enabling JMX Monitoring

   JMX monitoring needs to be enabled for the kafka process. Following properties need to be set for the process.
   
   ```
   -Dcom.sun.management.jmxremote
   -Djava.rmi.server.hostname=0.0.0.0 
   -Dcom.sun.management.jmxremote.local.only=false 
   -Dcom.sun.management.jmxremote.port= userDefinedJMXPort 
   -Dcom.sun.management.jmxremote.rmi.port= userDefinedJMXPort 
   -Dcom.sun.management.jmxremote.authenticate=false 
   -Dcom.sun.management.jmxremote.ssl=false
   ```
   
2. Prometheus JMX exporter Integration

   *Docker Image*: solsson/kafka-prometheus-jmx-exporter@sha256

   *Image Tag*: a23062396cd5af1acdf76512632c20ea6be76885dfc20cd9ff40fb23846557e8

   *Configuration:* Exporter needs to be deployed as one of the containers in  broker pods and following command needs to be executed as the actual container process 		

   ```
   java -jar jmx_prometheus_httpserver.jar userDefinedPrometheusPort configurationFile
   ```

   *Instrumentation*: Prometheus instrumentation is based on the exporter rules specified in  exporterConfigurationFile. Config File needs to be mounted with following contents

   ```
   jmxUrl: service:jmx:rmi:///jndi/rmi://127.0.0.1:userDefinedJMXPort/jmxrmi
   lowercaseOutputName: true
   lowercaseOutputLabelNames: true
   ssl: false
   
   whitelistObjectNames: ["kafka.controller:*","kafka.server:*","java.lang:*","kafka.network:*","kafka.log:*"]
   
   rules:
   - pattern: kafka.controller<type=(ControllerChannelManager), name=(QueueSize), broker-id=(\d+)><>(Value)
     name: kafka_controller_$1_$2_$4
     labels:
     broker_id: "$3"
   - pattern: kafka.controller<type=(ControllerChannelManager), name=(TotalQueueSize)><>(Value)
     name: kafka_controller_$1_$2_$3
   - pattern: kafka.controller<type=(KafkaController), name=(.+)><>(Value)
     name: kafka_controller_$1_$2_$3
   - pattern: kafka.controller<type=(ControllerStats), name=(.+)><>(Count|OneMinuteRate)
     name: kafka_controller_$1_$2_$3
   - pattern: kafka.server<type=(ReplicaFetcherManager), name=(.+), clientId=(.+)><>(Value)
     name: kafka_server_$1_$2_$4
     labels:
       client_id: "$3"
   - pattern : kafka.network<type=(Processor), name=(IdlePercent), networkProcessor=(.+)><>(Value)
     name: kafka_network_$1_$2_$4
     labels:
       network_processor: $3
   - pattern : kafka.network<type=(RequestMetrics), name=(.+), request=([^,]+).*><>(Count|OneMinuteRate|Mean)
     name: kafka_network_$1_$2_$4
     labels:
       request: $3
   - pattern: kafka.server<type=(.+), name=(.+), topic=(.+)><>(Count|OneMinuteRate)
     name: kafka_server_$1_$2_$4
     labels:
       topic: $3
   - pattern: kafka.server<type=(DelayedOperationPurgatory), name=(.+), delayedOperation=(.+)><>(Value)
     name: kafka_server_$1_$2_$3_$4
   - pattern: kafka.server<type=(.+), name=(.+)><>(Count|Value|OneMinuteRate)
     name: kafka_server_$1_total_$2_$3
   - pattern: kafka.server<type=(.+)><>(queue-size)
     name: kafka_server_$1_$2
   - pattern: java.lang<type=(.+), name=(.+)><(.+)>(\w+)
     name: java_lang_$1_$4_$3_$2
   - pattern: java.lang<type=(.+), name=(.+)><>(\w+)
     name: java_lang_$1_$3_$2
   - pattern : java.lang<type=(.*)>
   - pattern: kafka.log<type=(.+), name=(.+), topic=(.+), partition=(.+)><>Value
     name: kafka_log_$1_$2
     labels:
       topic: $3
       partition: $4
   
   ```

   (Note: By default, confluent and apache kafka helm charts ships with the JMX exporter. In these charts , JMX port will be set to 5555 by default .Exporter rules and whitelisted objects only needs to be updated with the above specified Instrumentation in the configuration file.)

   Kubernetes service needs to be created for exposing *userDefinedPrometheusPort*

   *Reference:*  [Prometheus JMX exporter](https://github.com/prometheus/jmx_exporter/blob/master/README.md)

3. Danielqs Exporter

   *Docker Image*: danielqsj/kafka-exporter

   *Image Tag*: v1.0.1

   *Configuration:* Exporter is installed as a container in separate pod. Kafka broker details and prometheus listener port needs to be configured in following way

   ```
   - args:
     - --kafka.server= brokerhost:brokerPort
     - --web.listen-address=:userDefinedPrometheusListenerPort
   ```
   
   Kubernetes service needs to be created for exposing *userDefinedPrometheusListenerPort*
   
   *Reference:*  [Danielqs Exporter]( https://github.com/danielqsj/kafka_exporter/blob/master/README.md)

##### Documents:

Plugin Type kube-prom-kafka-jmx contains the metrics organized into following document types

- Kafka stats: contain transactional data and metrics related to broker state
- Topic stats: provide metrics for analyzing internal transactions associated with each topic
- Partition stats: provide log size information for each partition
- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

Plugin Type kube-prom-kafka contains the metrics organized into following document types

- Kafka stats: Contains consumers related information  and the total partiton count.

Use Kafka_Application_Kubernetes_Prometheus dashboard template in APM for visualization.

------

##### SFKubeAgent

##### Description

Plugin provides statistical information related to message traffic distribution and other internal transactions among the brokers.

##### Prerequisites

1. Adding Jolokia Init Container

   Plugin is based on jolokia. Jolokia agent (jar file) can be downloaded as a part of broker’s init container.
   (Download URL: https://repo1.maven.org/maven2/org/jolokia/jolokia-jvm/1.6.2/jolokia-jvm-1.6.2-agent.jar) and can be mounted in any directory of user choice.

   Sample Init Container Configuration for including Jolokia

   ```
   initContainers:
   - name: get-jolokia
     image: alpine
     command:
     - sh
     - -c
     - -x
     - apk add --no-cache curl && curl ${JOLOKIA_JAR_URL} -o /agent/jolokia.jar
     env:
     - name: JOLOKIA_JAR_URL
       value: https://repo1.maven.org/maven2/org/jolokia/jolokia-jvm/1.6.2/jolokia-jvm-1.6.2-agent.jar
     volumeMounts:
     - name: javaagentdir
       mountPath: /agent
   ```

   Jolokia can started along with broker process. Both Confluent and Apache distributions, allow java agents to be started along with kafka process. With Configured agent mount path , environment variable KAFKA_JMX_OPTS needs to be set in broker’s container configuration with the following value

   ```
   - name: KAFKA_JMX_OPTS
     value: "-javaagent:/agent/jolokia.jar=port=8778,host=127.0.0.1 
             -Djava.rmi.server.hostname=127.0.0.1 
             -Dcom.sun.management.jmxremote=true 
             -Dcom.sun.management.jmxremote.authenticate=false  
             -Dcom.sun.management.jmxremote.ssl=false"
   ```

   Port 8778 is used by Jolokia for exposing mbeans. It can be configured to any port as well.

2. Configuring SFKubeAgent Container

   Sample kafka Metric Plugin Configuration as follows

   ```
   key: "{{ .Values.global.key }}"
   metrics:
     plugins:
     - name: kafkatopic
       enabled: {{ .Values.sfagent.metric_collection }}
       interval: 300
       config:
         documentsTypes:
           - kafkaStats
           - partitionStats
           - topicStats
           - consumerStats
         jolokiaPort: 8778
         RMIPort: 8778
         jolokiaContext: jolokia
         jolokiaProtocol: http
         jolokiaProxy: false
         port: 9092
     - name: kafkajmx
       enabled: {{ .Values.sfagent.metric_collection }}
       interval: 300
       config:
         jolokiaPort: 8778
         RMIPort: 8778
         jolokiaContext: jolokia
         jolokiaProtocol: http
         jolokiaProxy: false
   ```

   Configuration Parameters Breakdown:-

   *jolokiaPort*         : port on which jolokia based requests are served
   
   *jolokiaContext*   : context makes jolokia endpoint. Default value is string
   
   *jolokiaProtocol*  : request/response protocol

   *jolokiaProxy*       : jolokia proxy mode , if enabled for Jolokia agent

   *RMIPort*              : RMI port .if enabled 
   
   
   Logging can also be enabled as mentioned in [Kafka SFAgent Plugin]()

##### Documents

Plugin Type kafkatopic consists of  documents organized into following types 

- Kafka stats: contain transactional data and metrics related to broker state
- Topic stats: provide metrics for analyzing internal transactions associated with each topic
- Partition stats:  provide log size and segment information for each partition 
- Consumer stats: provide consumer lag and offset information

Plugin Type kafkajmx contains single type of document 

- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

Use Kafka_Application_Kubernetes_SFKubeAgent dashboard template in APM for visualization

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).