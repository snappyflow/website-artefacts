##### Prometheus

##### Description

Plugin provides statistical information about the nginx server performance in real-time.

##### Prerequisites

1. Enabling Nginx Stub Status

   Nginx application image should ship with http_stub_status_module enabled. 

   Following is a basic server configuration to enable stub_status module and expose server stats

   ```
   location /nginx_stats {
   	stub_status;
   	allow 127.0.0.1;
   	deny all;
   }
   ```

2. Exporter Integration

   *Docker Image*: nginx/nginx-prometheus-exporter

   *Image Tag*: 0.5.0

   *Configuration:* Container environment should be configured as follows		

   ```
    - env:
        - name: HOST_IP
          Value: serverIP
        - name: SCRAPE_URI
          value: http://serverIP:serverPort/nginx_stats
   ```
   
   Kubernetes service needs to be created for exposing the default exporter port *9113*
   
   Reference:  [Nginx Exporter](https://github.com/nginxinc/nginx-prometheus-exporter/blob/master/README.md)

##### Documents

Plugin Type kube-prom-nginx contains the metrics organized into following document type

- nginxStats 

Use Nginx_Kubernetes_Prometheus dashboard template in APM for visualization.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).