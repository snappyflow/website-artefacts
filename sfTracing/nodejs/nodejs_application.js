//<SFTRACE-CONFIG> add the below agent specific variable 
//Replace <service_name> with approariate value. The service_name is used to identify and filter the traces related to an application and should be named appropriately to distinctly identify it.  Service name must only contain characters from the ASCII alphabet, numbers, dashes, underscores and spaces.
const apm=require('elastic-apm-node').start({
	  serviceName: '<service_name>',
	  serverUrl: process.env.SFTRACE_SERVER_URL,
	  globalLabels: process.env.SFTRACE_GLOBAL_LABELS,
	  verifyServerCert: process.env.SFTRACE_VERFIY_SERVER_CERT === undefined ? false : process.env.SFTRACE_VERFIY_SERVER_CERT,
	  active: process.env.SFTRACE_SERVER_URL === undefined ? false : true
	  })



const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
  res.send('Hello Maplelabs!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
