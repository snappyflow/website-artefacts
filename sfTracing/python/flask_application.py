from flask import Flask
#<SFTRACE-CONFIG> import trace agent package as below
from elasticapm.contrib.flask import ElasticAPM
import os
app = Flask(__name__)

@app.route('/hello')
def helloIndex():
    return 'Hello World from Python Flask!'

#<SFTRACE-CONFIG> add the below agent specific configuration 
# Replace <service_name> with approariate value. The service_name is used to identify and filter the traces related to an application and should be named appropriately to distinctly identify it.  Service name must only contain characters from the ASCII alphabet, numbers, dashes, underscores and spaces.
import os,json
try:
    SFTRACE_CONFIG = json.loads(os.popen('/opt/sfagent/sftrace/sftrace').readlines()[0]) if len(os.popen('/opt/sfagent/sftrace/sftrace').readlines()) > 0 else dict()
    app.config['ELASTIC_APM'] =  {
         'SERVICE_NAME': "<service_name>",
         'SERVER_URL': SFTRACE_CONFIG.get('SFTRACE_SERVER_URL'),
         'GLOBAL_LABELS': SFTRACE_CONFIG.get('SFTRACE_GLOBAL_LABELS'),
         'VERIFY_SERVER_CERT': SFTRACE_CONFIG.get('SFTRACE_VERIFY_SERVER_CERT'),
         'SPAN_FRAMES_MIN_DURATION': SFTRACE_CONFIG.get('SFTRACE_SPAN_FRAMES_MIN_DURATION'),
         'STACK_TRACE_LIMIT': SFTRACE_CONFIG.get('SFTRACE_STACK_TRACE_LIMIT'),
         'CENTRAL_CONFIG': False,
         'DEBUG': True,
    }
except Exception as error:
    print("Error while fetching snappyflow tracing configurations", error)


#<SFTRACE-CONFIG> Initialize the agent for this flask application by adding the below line
apm = ElasticAPM(app)

app.run(host='0.0.0.0', port=5000)
