from celery import Celery

# <SFTRACE-CONFIG> add the below agent specific configuration
from elasticapm import Client, instrument
from elasticapm.contrib.celery import register_exception_tracking, register_instrumentation
import os
instrument()

# <SFTRACE-CONFIG> Replace <service_name> with appropriate value. The service_name is used to identify and filter the traces related to an application and should be named appropriately to distinctly identify it.  Service name must only contain characters from the ASCII alphabet, numbers, dashes, underscores and spaces.
apm_client = Client(server_url=os.getenv( 'SFTRACE_SERVER_URL', None), global_labels= os.getenv('SFTRACE_GLOBAL_LABELS', None), service_name= '<service_name>', verify_server_cert= os.getenv('SFTRACE_VERFIY_SERVER_CERT', None)) 
register_exception_tracking(apm_client)
register_instrumentation(apm_client)

# sfagent config finish


app = Celery('tasks', broker='amqp://guest@localhost:5672')

@app.task
def add(x, y):
    return x + y
