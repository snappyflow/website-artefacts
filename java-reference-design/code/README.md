<!-- Copyright 2020 MapleLabs -->
<!-- Author: Anchal Gupta (Anchal.Gupta@maplelabs.com) -->
<!-- Description: Main README file for Provisioner. -->

![Provisioner](https://res.cloudinary.com/anchal-gupta/image/upload/v1596830627/Git%20Images/provisioner-logo_kh4cdd.png)

Provisioner is an application where you can easily provision multiple VMs & also analyze the real-time status of it so that you don't have to manually configure each VM.

<hr />

> **Contents :**
> * [Dependency Matrix](#dependency-matrix-)
> * [Application Setup](#application-setup-)
> * [Running the Application](#running-the-application-)
> * [Provisioner UI Setup](#provisioner-ui-setup-)
> * [JMeter Setup](#jmeter-setup-)
> * [Contributing](#contributing-)

<br />

## Dependency Matrix :

<table>
  <thead>
    <tr>
      <th>Type</th>
      <th>Technology</th>
      <th>Version</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td rowspan=2>Backend (Server)</td>
      <td>Java</td>
      <td>1.8</td>
    </tr>
    <tr>
      <td>Spring Boot</td>
      <td>2.3.1.RELEASE</td>
    </tr>
    <tr>
      <td>Database</td>
      <td>MySQL</td>
      <td>8.0.20.0</td>
    </tr>
    <tr>
      <td>Messaging Queue</td>
      <td>Apache Kafka</td>
      <td>2.12</td>
    </tr>
    <tr>
      <td rowspan=7>Frontend (UI)</td>
      <td>Angular CLI</td>
      <td>10.0.0</td>
    </tr>
    <tr>
      <td>Node</td>
      <td>12.16.3</td>
    </tr>
    <tr>
      <td>rxjs</td>
      <td>6.5.5</td>
    </tr>
    <tr>
      <td>Bootstrap</td>
      <td>4.5</td>
    </tr>
    <tr>
      <td>jQuery</td>
      <td>3.5.1</td>
    </tr>
    <tr>
      <td>Popper.js</td>
      <td>1.16.0</td>
    </tr>
    <tr>
      <td>Font Awesome</td>
      <td>5</td>
    </tr>
    <tr>
      <td>Load Generator</td>
      <td>JMeter</td>
      <td>5.3</td>
    </tr>
  </tbody>
</table>

<br />

## Application Setup : 

> Before starting the application, you need to setup MySQL and Kafka in your system. You can easily perform the necessary setup by following the below the steps based on your Linux flavour.
>
> * [Ubuntu (Debian) Based Setup](https://github.com/snappyflow/website-artefacts/blob/first-draft-code-check-in/java-reference-design/code/Ubuntu%20(debian).md#ubuntu-debian-based-setup-)
>
> * [CentOS Based Setup](https://github.com/snappyflow/website-artefacts/blob/first-draft-code-check-in/java-reference-design/code/CentOS.md#centos-based-setup-)

<br />

## Running the Application :

```
git clone https://github.com/snappyflow/website-artefacts.git ... (at /home/ubuntu ... it asks for credentials)
```

> Note : Make sure that MySQL and Kafka are running.

Open new terminal build and run manager code : 

```python
cd ~/website-artefacts/java-reference-design/code/provision-manager

mvn clean install 

# If you already have sftrace jar, then the below command will run the manager with sfagent in background :
nohup java -javaagent:/opt/sfagent/sftrace/sfjava/sftrace-java-agent-1.0.jar -jar target/provision-manager-0.0.1-SNAPSHOT.jar &


# OR 

# If you don't have sftrace jar, then the below command will run the manager without sfagent in background :
nohup java -jar target/provision-manager-0.0.1-SNAPSHOT.jar &


# For checking logs : 
tail -f nohup.out
```

Command Line Option | Default Value | Example Value | Description
---------|----------|---------|---------
 --server.port | 8080 | 8085 | To run the application on different port
 --worker.server.ip | localhost:8083 | localhost:8083,localhost:8084 | List of Worker Servers/IPs configurations <br />(If you are running multiple workers, then <br />you need to provide the list of Server/IPs <br />of those workers.)
 --logging.file.name | provision-manager.log | ./logs/manager.log | To change the log file name and it's location

Open new terminal build and run worker code : 

```python
cd ~/website-artefacts/java-reference-design/code/provision-worker

mvn clean install

# If you already have sftrace jar, then the below command will run the manager with sfagent in background :
nohup java -javaagent:/opt/sfagent/sftrace/sfjava/sftrace-java-agent-1.0.jar -jar target/provision-worker-0.0.1-SNAPSHOT.jar &


# OR 

# If you don't have sftrace jar, then the below command will run the worker without sfagent in background :
nohup java -jar target/provision-worker-0.0.1-SNAPSHOT.jar &


# For checking logs : 
tail -f nohup.out
```

Command Line Option | Default Value | Example Value | Description
---------|----------|---------|---------
 --server.port | 8083 | 8084 | To run the application on different port
 --worker.id | 1 | 2 | To set the worker ID
 --logging.file.name | provision-worker.log | ./logs/worker.log | To change the log file name and it's location

<br />

**Swagger UI :** http://localhost:8080/swagger-ui.html ... (where http://localhost:8080 is URL (Service/IP) for Manager)

**Postman :** Download the Provisioner.postman_collection.json from [here](https://github.com/pramurthy/javasamples/tree/master/docs) and import it in your Postman.

<br />

## Provisioner UI Setup :

If you want to setup the Provisioner UI, then follow the steps defined
[here](https://github.com/snappyflow/website-artefacts/blob/first-draft-code-check-in/java-reference-design/code/provision-ui#provisioner-ui).

<br />

## JMeter Setup :

If you want to setup the JMeter to perform load testing, then follow the steps defined
[here](https://github.com/snappyflow/website-artefacts/blob/first-draft-code-check-in/java-reference-design/code/jmeter#jmeter-installation-).

<br />

## Contributing :

> Before contributing to this project, first you need to perform the necessary setup (for Windows) by following the steps described 
> [here](https://github.com/snappyflow/website-artefacts/blob/first-draft-code-check-in/java-reference-design/code//Windows.md#prerequisites-).

To contribute to this project, follow these steps 🤓 :

* Fork this repository.
* Create your feature branch : `git checkout -b feature/fooBar`
* Commit your changes : `git commit -am 'Add some fooBar'`
* Push to the branch : `git push origin feature/fooBar`
* Create a new Pull Request.

<br />
