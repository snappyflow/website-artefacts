#<SFTRACE-CONFIG> add below lines to attach java agent to tomcat to enable tracing
export CATALINA_OPTS="$CATALINA_OPTS -javaagent:/opt/sfagent/sftrace/java/sftrace-java-agent.jar"
#Replace <my-service> with appropriate service name. Transactions can be filtered by service name in SnappyFlow-APM UI
export CATALINA_OPTS="$CATALINA_OPTS -Dsftrace.apm.service_name=< my-service >"
# <SFTRACE-CONFIG>Below system properties are only applicable for spring boot applications and optional. More information available in the documentation. #Refer to HTTPConfiguration Options before setting these properties
export CATALINA_OPTS="$CATALINA_OPTS -Delastic.apm.disable_instrumentation=spring-mvc"
export CATALINA_OPTS="$CATALINA_OPTS -Delastic.apm.use_path_as_transaction_name=true"
#Replace <normalized urls> with appropriate urls. Transactions can be filtered by normalized url in SnappyFlow-APM UI
export CATALINA_OPTS="$CATALINA_OPTS -Delastic.apm.url_groups=<normalized urls>"
