##### Metric

##### Description

JVMJolokia Metric plugin is an agent-based plugin which is used for Java Runtime monitoring. It returns the Java Virtual Machine (JVM) runtime metrics exposed as MBean's attributes through jolokia REST endpoint to detect application performance issues.

##### Prerequisites

JVM Jolokia plugin requires jolokia agent to be deployed on the target machine and it should expose on configured port.

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

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div>- name: jvmjolokia</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>username: admin</div>
      <div>password: admin</div>
      <div>protocol: http</div>
      <div>port: 8778</div>
      <div>context: jolokia</div>
    </div>
  </div>
</div>

Plugin collects JMX metrics from an HTTP endpoint using Jolokia's JSON over HTTP and supports jolokia configured with HTTP Basic Authentication. Apply JVM_Jolokia Dashboard for metrics visualization.

##### Configuring parameters

*username*: username configured in jolokia used for authentication.

*password*: Password Used for authentication

*protocol*: HTTP protocol to use. Should be either http or https. Default value is http

*port*: Port in which HTTP server is listening. Default port is 8778

*context*: this is used to compose jolokia URL. Default context value is “jolokia”

##### Documents

Use JVM_Jolokia dashboard to analysis the performance with collected metrics.



For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).


