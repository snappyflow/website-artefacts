##### Prometheus

##### Description

Plugin provides MySQL database performance related metrics.

##### Prerequisites

1. Exporter Integration

   *Docker Image*: prom/mysqld-exporter

   *Image Tag:* v0.10.0

   *Configuration:* Container should be configured in following way		
   
   ```
   env:
     - name : MYSQL_ROOT_PASSWORD
   	value: rootPassword
   command: [ 'sh','-c', 'DATA_SOURCE_NAME="root:$MYSQL_ROOT_PASSWORD@
   (localhost:mysqlPort)/" /bin/mysqld_exporter' ]
   ```
   
   Kubernetes service needs to be created for exposing the default exporter port *9104*
   
   *Reference:* [MySQL Exporter](https://github.com/prometheus/mysqld_exporter/blob/master/README.md)


##### Documents

Plugin Type kube-prom-mysql contains the metrics organized into following document type

- serverDetails (contains metrics related to server performance)
- tabledetails (contains metrics related to stored tables)

Use MySQL_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).
