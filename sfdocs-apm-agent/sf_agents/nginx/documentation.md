##### Metric

##### Description

Nginx Metric plugin connects to nginx server. It collects the number of requests handled by the nginx daemon since startup and the number of current connections by connection state (reading, writing,handled etc).

##### Prerequisites

1. ##### Installing Nginx web server

   Nginx web server can be installed following the guidelines available at [nginx.com](https://www.nginx.com/resources/wiki/start/topics/tutorials/install/)  (official nginx web server site) .

2. ##### Checking if nginx status module is enabled

   Open source NGINX exposes several basic metrics about server activity on a simple status page, provided that you have the HTTP stub status module enabled. To check if the module is already enabled, run:

   ```
   nginx -V 2>&1 | grep -o with-http_stub_status_module
   ```

   The status module is enabled if you see **with-http_stub_status_module** as output in the terminal.
   If not enabled , follow step 3.

3. ##### Enabling nginx status module

   In order to enable mod_status , you will need to enable the status module. You can use the --with-http_stub_status_module configuration parameter when building NGINX from source:

   ```
   ./configure \
   … \
   --with-http_stub_status_module
   make
   sudo make install
   ```

   After verifying the module is enabled or enabling it yourself, you will also need to modify your NGINX configuration to set up a locally accessible URL (e.g., /stats) for the status page:

   ```
   server {
     location /stats {
       stub_status;
       access_log off;
       allow 127.0.0.1;
       deny all;
     }
   }
   ```

    

   **Note:** The server blocks of the NGINX config are usually found not in the master configuration file (e.g., /etc/nginx/nginx.conf) but in supplemental configuration files that are referenced by the master config. To find the relevant configuration files, first locate the master config by running:

   **nginx -t**

   Open the master configuration file listed, and look for lines beginning with include near the end of the http block, such as:

   **include /etc/nginx/conf.d/\*.conf;**

   In one of the referenced config files you should find the main server block, which you can modify as above to configure NGINX metrics reporting. After changing any configurations, reload the configs by executing:

   **nginx -s reload**

   Now you can view the status page to see your metrics: 

   **http://127.0.0.1/stats**

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: nginx </div>
  <div class="innerLeft">
    <div>enabled: true </div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>port: 80 </div>
      <div>secure: false </div>
      <div>location: 'stats' </div>
    </div>
  </div>
</div>


##### Documents

All Nginx metrics are collected and displayed in Nginx dashboard. 

------
#####  Logger

##### Description

Nginx logger to capture Nginx server access logs and error logs

##### Prerequisites

Nginx server access log format needs to be modified to capture all metrics from the access log. It includes following steps:

1. Edit Log format in nginx conf file “/etc/nginx/nginx.conf” or in the respective nginx configuration file.

2. Set Log format to:

   ```
   '$remote_addr $remote_user [$time_local] ' '"$request" $status $body_bytes_sent '                    '"$http_referer" "$http_user_agent" "$http_referer" ' 'rt=$request_time uct=$upstream_connect_time  uht=$upstream_header_time urt=$upstream_response_time';
   ```

Sample: 

```
log_format upstream_time  '$remote_addr $remote_user [$time_local] '
                       '"$request" $status $body_bytes_sent '
                       '"$http_referer" "$http_user_agent" "$http_referer" '
                       'rt=$request_time uct=$upstream_connect_time uht=$upstream_header_time urt=$upstream_response_time';

access_log  /var/log/nginx/access.log upstream_time buffer=16k flush=2m;
```

After configuring log format, expected log entry would be:

```
172.31.72.81 - [01/Jul/2020:03:36:04 +0000] "POST /owners/6/edit HTTP/1.1" 504 167 "-" "Apache-HttpClient/4.5.7 (Java/1.8.0_252)" "-" rt=60.004 uct=- uht=- urt=60.004
```

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin. Mention the access and error log file path in plugin configuration. Wildcard characters are supported.

<div class="sfpollerExample">
  <div>- name: nginx-access</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_path: /var/log/nginx/access.log, /var/log/nginx/access_log</div>
    </div>
  </div>
  <p></p>
  <div>- name: nginx-error</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_level:</div>
      <div>- error</div>
      <div>- emerg</div>
      <div>- alert</div>
      <div>log_path: /var/log/nginx/error.log, /var/log/nginx/error_log</div>
    </div>
  </div>
</div>

Mention the required log levels to capture for nginx-error logs in “log_level”. Available options for log level are: error, warn, alert, emerg, crit. Default values are : emerg, alert, error

##### Documents

- Nginx access log metrics are visualized on *Nginx* Dashboard.
- Nginx error logs are under log sections on sfAPM dashboard.


For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).
