##### Prometheus

##### Description

Plugin provides JVM related metrics for the Java based applications hosted in kubernetes.

##### Prerequisites

1. Enabling JMX Monitoring

   JMX monitoring needs to be enabled for the java process. Following properties need to be set for the process.
   
   ```
   -Dcom.sun.management.jmxremote
   -Djava.rmi.server.hostname=0.0.0.0 
   -Dcom.sun.management.jmxremote.local.only=false 
   -Dcom.sun.management.jmxremote.port= userDefinedJMXPort 
   -Dcom.sun.management.jmxremote.rmi.port= userDefinedJMXPort 
   -Dcom.sun.management.jmxremote.authenticate=false 
   -Dcom.sun.management.jmxremote.ssl=false
   ```
   
   
   
2. Exporter Integration

   *Docker Image*: bitnami/jmx-exporter

   *Image Tag*: latest

   *Configuration:* Exporter needs to be deployed as one of the containers in application pod and following command needs to be executed as the actual container process. 		

   ```
   java -jar jmx_prometheus_httpserver.jar userDefinedPrometheusPort exporterConfigFile 
   ```

   *Instrumentation*: Prometheus instrumentation is based on the exporter rules specified in  exporterConfigFile. Config File needs to be mounted with following contents

   ```
   jmxUrl: service:jmx:rmi:///jndi/rmi://127.0.0.1:userDefinedJMXPort/jmxrmi
   ssl: false
   rules:
       - pattern: ".*"
   ```

   Kubernetes service needs to be created for exposing *userDefinedPrometheusPort*
   
   Reference: [Prometheus JMX exporter](https://github.com/prometheus/jmx_exporter/blob/master/README.md)

##### Documents:

Plugin Type kube-prom-jmx contains the metrics organized into following document type

- jmxStats

Use JVM_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).