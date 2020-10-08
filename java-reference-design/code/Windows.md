<!-- Copyright 2020 MapleLabs -->
<!-- Author: Anchal Gupta (Anchal.Gupta@maplelabs.com) -->
<!-- Description: README file for Developers (Windows Setup). -->

![Provisioner](https://res.cloudinary.com/anchal-gupta/image/upload/v1596830627/Git%20Images/provisioner-logo_kh4cdd.png)

Provisioner is an application where you can easily provision multiple VMs & also analyze the real-time status of it so that you don't have to manually configure each VM.

<hr />

### Prerequisites :

<br/>

**Java Installation :** 

* **Download JDK :** https://www.oracle.com/java/technologies/javase-downloads.html
* **Set Path :** Edit the system environment variables. 
  * **JAVA_HOME :** C:\Program Files\Java\jdk1.8.0_152
  * **JRE_HOME :** C:\Program Files (x86)\Java\jre1.8.0_231
  * **Path :** C:\Program Files\Java\jdk1.8.0_152\bin
* **Command Prompt :** 
  * `java -version`
  * `javac -version`

<br/>

**Eclipse Installation :** 

* **Download Eclipse IDE for Enterprise Java Developers :** https://www.eclipse.org/downloads/packages/
* **Download Code Formatter :** Download Eclipse [provisioner].xml Code Formatter from [here](https://github.com/pramurthy/javasamples/tree/master/docs).
* **Import Code Formatter :** Window >> Preferences >> Java >> Code Style >> Formatter >> Import >> Select Eclipse [provisioner]

<br/>

**MySQL Installation :** 

* **Download MySQL Installer 8.0.20 :** https://dev.mysql.com/downloads/windows/installer/
* **Download MySQL Workbench 8.0.20 :** https://dev.mysql.com/downloads/workbench/
  * **username :** root
  * **password :** root
  * **Reference :** https://phoenixnap.com/kb/install-mysql-on-windows

<br/>

**Apache Kafka Installation :** 

* **Download :** https://kafka.apache.org/downloads ... (2.12 is recommended)
* **Unzip it to a particular location :** C:\Kafka\
* **Go to the unzipped Kafka folder :** cd C:\Kafka\kafka_2.12-2.5.0
* **Create 3 folders :** data, kafka-logs, logs ... (Delete its contents if you face any Kafka issue)
* **Go inside the config folder :** cd C:\Kafka\kafka_2.12-2.5.0\config
  * **zookeeper properties :** `dataDir=C:\\Kafka\\kafka_2.12-2.5.0\\data`
  * **server properties :** `log.dirs=C:\\Kafka\\kafka_2.12-2.5.0\\kafka-logs`

<hr />

### Running the Application :

<br/>

**Command Prompt - 1 :** Start the Zoo Keeper.
```
cd C:\Kafka\kafka_2.12-2.5.0
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

**Command Prompt - 2 :** Start the Apache Kafka.
```
cd C:\Kafka\kafka_2.12-2.5.0
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

**Command Prompt - 3 :** Start the consumer which listens to the topic task-topic.
```
cd C:\Kafka\kafka_2.12-2.5.0
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic task-topic --from-beginning
```

**Command Prompt - 4 :** Start the consumer which listens to the topic notification-topic.
```
cd C:\Kafka\kafka_2.12-2.5.0
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic notification-topic --from-beginning
```

**Command Prompt - 5 :** Start the provision-manager application.
```python
./mvnw clean install
java -jar .\target\provision-manager-0.0.1-SNAPSHOT.jar
```

Command Line Option | Default Value | Example Value | Description
---------|----------|---------|---------
 --server.port | 8080 | 8085 | To run the application on different port
 --worker.server.ip | localhost:8083 | localhost:8083,localhost:8084 | List of Worker Servers/IPs configurations <br />(If you are running multiple workers, then <br />you need to provide the list of Server/IPs <br />of those workers.)
 --logging.file.name | provision-manager.log | ./logs/manager.log | To change the log file name and it's location

**Command Prompt - 6 :** Start the provision-worker application.
```python
./mvnw clean install
java -jar .\target\provision-worker-0.0.1-SNAPSHOT.jar
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
