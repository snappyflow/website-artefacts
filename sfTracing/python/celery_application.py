from celery import Celery

# <SFTRACE-CONFIG> add the below agent specific configuration
from elasticapm import Client, instrument
from elasticapm.contrib.celery import register_exception_tracking, register_instrumentation
import os
instrument()

# <SFTRACE-CONFIG> Replace <service_name> with appropriate value. The service_name is used to identify and filter the traces related to an application and should be named appropriately to distinctly identify it.  Service name must only contain characters from the ASCII alphabet, numbers, dashes, underscores and spaces.
import os,json
try:
    SFTRACE_CONFIG = json.loads(os.popen('/opt/sfagent/sftrace/sftrace').readlines()[0]) if len(os.popen('/opt/sfagent/sftrace/sftrace').readlines()) > 0 else dict()	
    apm_client = Client(service_name= '<service_name>',
                        server_url= SFTRACE_CONFIG.get('SFTRACE_SERVER_URL'), 
                        global_labels= SFTRACE_CONFIG.get('SFTRACE_GLOBAL_LABELS'),
					    verify_server_cert= SFTRACE_CONFIG.get('SFTRACE_VERIFY_SERVER_CERT')) 
	register_exception_tracking(apm_client)
    register_instrumentation(apm_client)
except Exception as error:
    print("Error while fetching snappyflow tracing configurations", error)

# sfagent config finish


app = Celery('tasks', broker='amqp://guest@localhost:5672')

@app.task
def add(x, y):
    return x + y
