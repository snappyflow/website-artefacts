##### Prometheus

##### Description

Elasticsearch plugin collects node, cluster and index level stats.	 

##### Prerequisites

1. Exporter Integration

   *Docker Image*: justwatch/elasticsearch_exporter

   *Image Tag*: 1.1.0

   *Configuration:* Exporter image can be deployed as container in separate pod. And the container command should be configured as follows		

   ```
    command: ["elasticsearch_exporter","--es.uri=protocol://elastichost:elasticsport", 
              "--es.all", "--es.shards", "--es.cluster_settings", "--es.indices",
              “--es.timeout=ESTimeOut” ,"--web.listen-address=prometheusListenerPort,
              “--web.telemetry-path=”/metrics"]
   
   ```

   *Configuration Parameters*

   - ESTimeOut: Exporter timeout for scraping stats from Elasticsearch.

   - prometheusListenerPort: Port to serve Prometheus scrape request.

     (es.uri can be specified in the format *protocol://user:password@host:port* if elasticsearch server is password protected)
   
   Kubernetes service needs to be created for exposing *prometheusListenerPort*
   
   *Reference*:  [Elasticsearch Exporter](https://github.com/justwatchcom/elasticsearch_exporter/blob/master/README.md)

##### Documents

Plugin Type kube-prom-elasticsearch contains the metrics organized into following document types

- indexStats
- clusterStats 
- nodeStats

Use Elasticsearch_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).