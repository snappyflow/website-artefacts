##### Metric

##### Description

Tomcat Metric plugin monitor Tomcat server by collecting multiple types of metrics like server stats, context stats, jvm stats using Jolokia

##### Prerequisites

- Tomcat Plugin is based on Jolokia agent which requires JMX monitoring to be enable locally. Following property needs to be included during the start of tomcat process

```
 -Dcom.sun.management.jmxremote
```

- JCMD command must be installed in the machine

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: tomcat</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>documentsTypes:</div>
      <div>- tomcatStats</div>
      <div>- requestProcessorStats</div>
      <div>- jvmStats</div>
      <div>- contextStats</div>
    </div>
  </div>
</div>

##### Operating Instructions

Mention the documentTypes required under *documentsTypes.* 
Available options are: tomcatStats, requestProcessorStats, jvmStats, contextStats

##### Documents

It consists of four document types:

- Tomcat stats: contain metrics like tomcat sever version, uptime, thread details.
- Request processor stats: shows request information like processing time, request count, data received and sent.
- Context stats: contain tomcat context related metrics like hit count, lookup count etc.
- JVM stats: contain all JVM related metrics used by tomcat server like garbage collection details, memory pools, loaded/unloaded classes etc.

Use Tomcat dashboard for data visualization.

------

##### Logger

##### Description

Tomcat logger to capture Apache tomcat server access logs

##### Prerequisites

Tomcat server access log format needs to be modified to capture all metrics from the access logs, which includes following steps

- Edit the file $TOMCAT_HOME/conf/server.xml

- Set log format in *“org.apache.catalina.valves.AccessLogValve*” class, pattern value to pre-defined “combined” log format or

```
%h %l %u %t &quot;%r&quot; %s %b %D &quot;%{Referer}i&quot; &quot;%{User-Agent}i&quot;
```

After changing log pattern to combined or the above mentioned pattern, sample log would look like:

```
49.206.1.85 - - [30/Jun/2020:13:12:32 +0000] "GET / HTTP/1.1" 200 11286 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Safari/537.36"
```

##### Configuration Settings

Mention the access log file path in plugin configuration. Wildcard character supported.

<div class="sfpollerExample">
<div>- name: tomcat-access</div>
<div class="innerLeft">
  <div>enabled: true</div>
  <div>config:</div>
  <div class="innerLeft">
   <div>log_path: <..tomcat-access log path></div>
   </div>
   </div>
</div>

##### Documents

Tomcat access log metrics come under metrics section on APM dashboard.



For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).