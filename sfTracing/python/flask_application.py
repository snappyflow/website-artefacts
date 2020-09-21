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
app.config['ELASTIC_APM'] =  {
	'SERVICE_NAME': '<service-name>',
	'DEBUG': True,
	'SERVER_URL': os.getenv( 'SFTRACE_SERVER_URL', None),
	'GLOBAL_LABELS': os.getenv('SFTRACE_GLOBAL_LABELS', None),
	'VERIFY_SERVER_CERT': os.getenv('SFTRACE_VERFIY_SERVER_CERT', None),
}

#<SFTRACE-CONFIG> Initialize the agent for this flask application by adding the below line
apm = ElasticAPM(app)

app.run(host='0.0.0.0', port=5000)
