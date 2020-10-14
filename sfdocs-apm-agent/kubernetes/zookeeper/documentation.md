##### Prometheus

##### Description

Plugin helps in analyzing the efficiency of zookeeper infrastructure by providing key metrics like node count, packet count, latency, watch count.

##### Prerequisites

1. Enabling JMX Monitoring

   JMX monitoring needs to be enabled for zookeeper process. Following properties need to be set for the process.
   
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

   *Configuration:* Exporter needs to be deployed as one of the containers in  zookeeper pods and following command needs to be executed as the actual container process 		

   ```
   java -jar jmx_prometheus_httpserver.jar userDefinedPrometheusPort exporterConfigFile
   ```

   *Instrumentation*: Prometheus instrumentation is based on the exporter rules specified in  exporterConfigFile .Config File needs to be mounted with following contents

   ```
   jmxUrl: service:jmx:rmi:///jndi/rmi://127.0.0.1:userDefinedJMXPort/jmxrmi
   lowercaseOutputName: true
   lowercaseOutputLabelNames: true
   ssl: false
   
   whitelistObjectNames: ["java.lang:*","org.apache.ZooKeeperService:*"]
   
   rules:
   - pattern: "org.apache.ZooKeeperService<name0=(.+)><>(\\w+)"
     name: "zookeeper_$2"
   - pattern: "org.apache.ZooKeeperService<name0=(.+), name1=(.+)><>(\\w+)"
     name: "zookeeper_$3"
     labels:
       replicaId: "$2"
   - pattern: "org.apache.ZooKeeperService<name0=(.+), name1=(.+), name2=(.+)><>(\\w+)"
     name: "zookeeper_$4"
     labels:
       replicaId: "$2"
       memberType: "$3"
   - pattern: "org.apache.ZooKeeperService<name0=(.+), name1=(.+), name2=(.+), name3=(.+)><>(\\w+)"
     name: "zookeeper_$4_$5"
     labels:
       replicaId: "$2"
       memberType: "$3"
   - pattern: java.lang<type=(.+), name=(.+)><(.+)>(\w+)
     name: java_lang_$1_$4_$3_$2
   - pattern: java.lang<type=(.+), name=(.+)><>(\w+)
     name: java_lang_$1_$3_$2
   - pattern : java.lang<type=(.*)>
   ```

   (Note: By default, confluent and apache helm charts ships with this exporter. Here  exporter rules needs to be updated with the above specified Instrumentation)
   
   Kubernetes service needs to be created for exposing *userDefinedPrometheusPort*
   
   *Reference:*  [Prometheus JMX exporter](https://github.com/prometheus/jmx_exporter/blob/master/README.md)

##### Documents:

Plugin Type kube-prom-zookeeper-jmx contains the metrics organized into following document types

- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

- Zookeeper stats: Contains performance related metrics.

Kafka_Application_Kubernetes_Prometheus dashboard template in APM comprises zookeeper metrics visualization.

------

##### SFKubeAgent

##### Description

Plugin helps in analyzing the efficiency of zookeeper infrastructure by providing key metrics like node count, packet count, latency, watch count.

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

   Jolokia can started along with broker process. Both Confluent and Apache distributions, allow java agents to be started along with zookeeper process. With Configured agent mount path , environment variable KAFKA_JMX_OPTS needs to be set in broker’s container configuration with the following value

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

   Sample Zookeeper Metric Plugin Configuration as follows

   ```
   key: "{{ .Values.global.key }}"
   metrics:
     plugins:
     - name: zookeeperjmx
       enabled: {{ .Values.sfagent.metric_collection }}
       interval: 300
       config:
         documentsTypes:
           - jmxStats
           - zookeeperStats
         jolokiaPort: 8778
         RMIPort: 8778
         jolokiaContext: jolokia
         jolokiaProtocol: http
         jolokiaProxy: false
   ```

   Configuration Parameters Breakdown

   *jolokiaPort*         : port on which jolokia based requests are served
   
   *jolokiaContext*   : context makes jolokia endpoint. Default value is string
   
   *jolokiaProtocol*  : request/response protocol

   *jolokiaProxy*       : jolokia proxy mode , if enabled for Jolokia agent

   *RMIPort*              : RMI port .if enabled 
   
   
   Logging can also be enabled as mentioned in [Zookeeper SFAgent Plugin]()

##### Documents

Plugin consists of  documents organized into following types 

- Zookeeper stats: contain zookeeper server related metrics 

- JVM stats: contain all JVM related metrics like garbage collection details, memory pools, loaded/unloaded classes etc.

Kafka_Application_Kubernetes_SFKubeAgent dashboard template in APM is integrated with zookeeper metric visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).