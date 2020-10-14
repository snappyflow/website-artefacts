##### Prometheus

##### Description

Plugin provides statistical information about postgres DB performance. 

##### Prerequisites

1. Exporter Integration

   *Docker Image*: bitnami/postgres-exporter

   *Configuration:* Command and arguments for the container should be configured as follows

   ```
   command: ["/bin/sh", "-c"]
   args:['DATA_SOURCE_NAME="postgresql://dbuser:dbpassword@hostIP:hostPort/dbname?sslmode=disable" /opt/bitnami/postgres-exporter/bin/postgres_exporter']
   ```
   
   Kubernetes service needs to be created for exposing the default exporter port *9187*
   
   *Reference:* [Postgres Exporter](https://github.com/wrouesnel/postgres_exporter/blob/master/README.md)


##### Documents

Plugin Type kube-prom-postgres contains the metrics organized into following document type

- psql

Use PostgreSQL_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).