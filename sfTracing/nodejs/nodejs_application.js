//<SFTRACE-CONFIG> add the below agent specific variable 
//Replace <service_name> with approariate value. The service_name is used to identify and filter the traces related to an application and should be named appropriately to distinctly identify it.  Service name must only contain characters from the ASCII alphabet, numbers, dashes, underscores and spaces.
try {
  let sfTraceConfig = JSON.parse(String.fromCharCode.apply(String, require('child_process').execSync("/opt/sfagent/sftrace/sftrace")))
  const apm = require('elastic-apm-node').start({
    serviceName: '<service_name>',
    serverUrl: sfTraceConfig['SFTRACE_SERVER_URL'],
    globalLabels: sfTraceConfig['SFTRACE_GLOBAL_LABELS'],
    verifyServerCert: sfTraceConfig['SFTRACE_VERIFY_SERVER_CERT'] === undefined ? false : sfTraceConfig['SFTRACE_VERIFY_SERVER_CERT'],
    active: sfTraceConfig['SFTRACE_SERVER_URL'] === undefined ? false : true,
    stackTraceLimit: sfTraceConfig['SFTRACE_STACK_TRACE_LIMIT'],
    captureSpanStackTraces: sfTraceConfig['SFTRACE_CAPTURE_SPAN_STACK_TRACES']
  })
} catch (e) {
  console.log(e)
}



const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
  res.send('Hello Maplelabs!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
