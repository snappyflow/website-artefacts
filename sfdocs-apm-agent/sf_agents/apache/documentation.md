##### Metric

##### Description

Apache Metric plugin queries the status module of the Apache web server and collects metrics such as bytes transferred, number of transactions etc,..

##### Prerequisites

1. ##### Installing Apache web server

   Apache web server can be installed following the guidelines available at [apache.org](https://httpd.apache.org/docs/2.4/install.html)  (official apache web server site) .

2. ##### Checking if apache status module is enabled

   Apache web server exposes metrics through its status module, **mod_status**. If apache server is running and mod_status is enabled, apache server’s status page should be available at http://127.0.0.1/server-status. 

   Alternatively , 

   on ubuntu(or debian based systems) run this command 

   ```
   sudo apache2ctl -M | grep status_module
   ```

   on centos/rhel/fedora run this command

   ```
   sudo httpd -M | grep status_module
   ```

   if output of above command is **status_module** , then apache status module is enabled.
   If not enabled , follow step 3.

3. ##### Enabling apache status module

   In order to enable mod_status , you either have to edit the status module’s configuration file (on Debian platforms), or your main Apache configuration file (all other Unix-like platforms).

   Debian users can find the status module’s configuration file at 

   **/etc/apache2/mods-enabled/status.conf**

    

   Users of other platforms (such as Red Hat–based systems) will find their main configuration file at **/etc/apache2/apache2.conf**, **/etc/httpd/conf/httpd.conf**, or **/etc/apache2/httpd.conf**. In the main configuration file, locate the following line and make sure it is uncommented:

   **LoadModule status_module libexec/apache2/mod_status.so**

    

   You’ll need to update the block (either in your status module’s config file or main Apache config file) that starts with <Location /server-status> to specify which IP addresses should have access to the status page. In the example below, we are allowing access from localhost, as well as the IP address x.x.x.x. 

   ```
   <Location /server-status>
       SetHandler server-status
       Require local
       Require ip x.x.x.x
   </Location>
   ```

   After you’re done making changes, save and exit. You can check your configuration file for errors with the following command:

   ```
   apachectl configtest 
   ```

   Perform a graceful restart to apply the changes without interrupting live connections (**apachectl -k graceful** or **service apache2 graceful)**

   After enabling mod_status and restarting Apache, you will be able to see your status page at **http://<YOUR_DOMAIN>/server-status**.
   
   

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: apache </div>
  <div class="innerLeft">
    <div>enabled: true </div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>port: 80 </div>
      <div>secure: false </div>
      <div>location: 'server-status' </div>
    </div>
  </div>
</div>

##### Documents

All Apache metrics are collected and displayed in Apache dashboard in sfAPM. 

------
##### Logger

##### Description

Apache logger to capture Apache server access logs and error logs

##### Prerequisites

Apache server access log format needs to be modified to capture all metrics from the access log. It includes following steps:

1. Open Log format in apache conf file,

   - In case of httpd: *“/etc/httpd/conf/httpd.conf”* 
   - In case of apache2: *“/etc/apache2/apache2.conf”* 

   or in the respective apache version configuration file

2. Set Log format to:

   ```
   %h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %D
   ```

Sample: 

```
LogFormat "%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %D" combined
CustomLog "logs/access_log" combined
```

After configuring log format, expected log entry would be:

```
45.112.52.50 - - [28/Jun/2020:23:34:10 -0700] "GET / HTTP/1.1" 302 242 "-" "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Safari/537.36" 271
```

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin. Mention the access and error log file path in plugin configuration. Wildcard characters are supported.

<div class="sfpollerExample">
  <div>- name: apache-access</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_path: /var/log/apache2/access.log, var/log/apache2/access_log</div>
    </div>
  </div>
  <p></p>
  <div>- name: apache-error</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_level:</div>
      <div>- error</div>
      <div>- warning</div>
      <div>- notice</div>
      <div>log_path: /var/log/apache2/error.log, /var/log/httpd/error_log</div>
    </div>
  </div>
</div>

Mention the required log levels to capture for apache-error logs in “log_level”. Available options for log level are: error, warn, alert, emerg, crit. Default values are : emerg, alert, error

##### Documents

- Apache access log metrics are visualized on *Apache* Dashboard.
- Apache error logs are under log sections on sfAPM dashboard.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).
