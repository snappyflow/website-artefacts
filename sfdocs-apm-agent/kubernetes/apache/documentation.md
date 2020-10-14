##### Prometheus

##### Description

Plugin provides statistical information about Apache server performance in real-time

##### Prerequisites

1. Enabling Apache status module

   Apache server should be configured to allow  traffic from  localhost.

   - LoadModule directive should have been set to load the named module “status_module”

     ```
     LoadModule status_module libexec/apache2/mod_status.so
     ```

   - Basic server configuration to invoke status_module and expose server stats is as follows

     ```
     <Location /server-status>
         SetHandler server-status
         Order deny,allow
         Deny from all
         Allow from localhost
     </Location>
     ```

2. Exporter Integration

   *Docker Image*: bitnami/apache-exporter

   *Image Tag*: latest

   *Configuration:* Container  configuration should be as follows
   
   ```
   command: ["/bin/sh", "-c"]
   args: ['/opt/bitnami/apache-exporter/bin/apache_exporter -scrape_uri="http://serverhost:serverport/server_status?auto"']
   ```

   *Configuration Parameters*

   - scrape_uri: Configured URI exposing apache server statistics.
   
   Kubernetes service needs to be created for exposing the default exporter port *9117*
   
   *Reference*: [Apache Prometheus Exporter](https://github.com/Lusitaniae/apache_exporter/blob/master/README.md)


##### Documents

Plugin Type kube-prom-apache contains the metrics organized into following document type

- apacheStats

Use Apache_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).