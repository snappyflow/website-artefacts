##### Metric

##### Description

Jboss Metric plugin monitors Jboss server by collecting multiple types of metrics like server stats, jvm stats using Jolokia

##### Prerequisites

- Jboss Metric Plugin is based on Jolokia agent which requires JMX monitoring to be enabled locally. Following property needs to be included during the start of jboss process

```
 -Dcom.sun.management.jmxremote
```

- JCMD command must be installed in the machine

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div>- name: jboss</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>username: admin</div>
      <div>password: admin</div>
      <div>protocol: http</div>
      <div>port: 8080</div>
      <div>context: jolokia</div>
    </div>
  </div>
</div>


##### Documents

It consists of two document types:

- Jboss stats: contain metrics like jboss sever version, uptime, server name,transactions and session related details,request information like processing time, request count, data received and sent,processing time, request count, data received and sent.
- JVM stats: contain all JVM related metrics used by tomcat server like garbage collection details, memory pools, loaded/unloaded classes etc.

Use Jboss dashboard for data visualization.

------

##### Logger

##### Description

Jboss logger to capture Wildfly server access logs

##### Prerequisites

Jboss server access log format needs to be modified to capture all metrics from the access logs, which includes following steps

- Edit the file $JBOSS_HOME/standalone/configuration/standalone.xml

- Set log format in the following section `<host name="default-host" alias="localhost">` by specifying the pattern value to pre-defined “combined” log format or

```
<host name="default-host" alias="localhost">
<location name="/" handler="welcome-content"/>
<access-log pattern="%h %t &quot;%r&quot; %s &quot;%{i,User-Agent}&quot; %D " use-server-log="false"/>
<http-invoker security-realm="ApplicationRealm"/>
</host>

```
- Set the attribute record-request-start-time="true" in the section `<subsystem xmlns="urn:jboss:domain:undertow:*">` for all the listeners as %D and %T access log elements will work only after record-request-start-time is turned on.

- Set the attribute statistics-enabled="true" in all the occurences of standalone.xml as the statistics are disabled by default

After changing log pattern to combined or the above mentioned pattern, sample log would look like:

```
183.83.155.203 [07/Aug/2020:14:24:17 +0000] "GET /petclinic/org.richfaces.resources/javax.faces.resource/org.richfaces/skinning.ecss?db=eAG7dPvZfwAIqAOT HTTP/1.1" 500 "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36" 3

```

##### Configuration Settings

Mention the access log file path in plugin configuration. Wildcard character supported.

<div class="sfpollerExample">
<div>- name: jboss-access</div>
<div class="innerLeft">
  <div>enabled: true</div>
  <div>config:</div>
  <div class="innerLeft">
   <div>log_path: <..jboss-access log path></div>
   </div>
   </div>
</div>

##### Documents

Jboss access log metrics come under metrics section on APM dashboard.



For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).
