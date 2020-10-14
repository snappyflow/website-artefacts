##### Metric

##### Description

An agent-based plugin to monitor Tomcat server by collecting multiple types of metrics like server stats, context stats, jvm stats using Jolokia

##### Prerequisites

Tomcat plugin requires jolokia agent to be deployed on the target machine and it should expose on configured port.

Sample configurations refer to [jolokia](https://jolokia.org/documentation.html) 

Let’s assume after successful configuration, agent is reachable under http://localhost:8778/jolokia/version/  , while accessing this URL (provide username and password if configured) you should get response similar to the below one. 

```json
{
  "request": {
    "type": "version"
  },
  "value": {
    "agent": "1.6.2",
    "protocol": "7.2",
    "config": {
      "listenForHttpService": "true",
      "maxCollectionSize": "0",
      "authIgnoreCerts": "false",
      "agentId": "192.168.1.167-34190-55cfe2ae-servlet",
      "agentType": "servlet",
      "policyLocation": "classpath:/jolokia-access.xml",
      "agentContext": "/jolokia",
      "mimeType": "text/plain",
      "discoveryEnabled": "false",
      "streaming": "true",
      "historyMaxEntries": "10",
      "allowDnsReverseLookup": "true",
      "maxObjects": "0",
      "debug": "false",
      "serializeException": "false",
      "detectorOptions": "{}",
      "dispatcherClasses": "org.jolokia.http.Jsr160ProxyNotEnabledByDefaultAnymoreDispatcher",
      "maxDepth": "15",
      "authMode": "basic",
      "authMatch": "any",
      "canonicalNaming": "true",
      "allowErrorDetails": "true",
      "realm": "jolokia",
      "includeStackTrace": "true",
      "useRestrictorService": "false",
      "debugMaxEntries": "100"
    },
    "info": {
      "product": "tomcat",
      "vendor": "Apache",
      "version": "9.0.24"
    }
  },
  "timestamp": 1582224539,
  "status": 200
}
```

##### Configuration Settings

Add the plugin configuration in configmap as follows and mount it to enable this plugin

<div class="sfpollerExample">
  <div> - name: tomcat</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>context: jolokia</div>
      <div>protocol: http</div>
      <div>port: <..jolokiaPort..></div>
      <div>username: <..username..></div>
      <div>password: <..password..></div>
      <div>proxy: false</div>
      <div>rmiport: <..RMIPort..></div>
      <div>documentsTypes:</div>
      <div>- tomcatStats</div>
      <div>- requestProcessorStats</div>
      <div>- jvmStats</div>
      <div>- contextStats</div>
    </div>
  </div>
</div>

##### Operating Instructions

*context*: jolokia context. Default value is “jolokia”

*protocol*: request protocol. [http/https]. Default value is http.

*port*: Jolokia port

*username & password*: login credentials required to access jolokia application

*proxy*: Set this to true if tomcat is running on proxy mode. Default value is false.

*rmiport*: RMI port set in the tomcat configuration. This is an optional field and only required in proxy mode

*documentsTypes*: Mention the required documentTypes (tomcatStats, requestProcessorStats, jvmStats, contextStats)

##### Documents

Use Tomcat dashboard for data visualization.

It consists of four document types:

- Tomcat stats: contain metrics like tomcat sever version, uptime, thread details.
- Request processor stats: shows request information like processing time, request count, data received and sent.
- Context stats: contain tomcat context related metrics like hit count, lookup count etc.
- JVM stats: contain all JVM related metrics used by tomcat server like garbage collection details, memory pools, loaded/unloaded classes etc.

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

