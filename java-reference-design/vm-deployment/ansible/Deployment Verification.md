<!-- Copyright 2020 MapleLabs -->
<!-- Author: Aanchal Sathyanarayanan (aanchal.sathyanarayanan@maplelabs.com) -->
<!-- Description: README file for verifying if the deployment was successful. -->

# Deployment verification 
### Status verification of all services
After running the below commands, if the services are running properly, then they should show the status as **active (running)**

Checking the status of MySQL:
```python
For Ubuntu(Debian)
sudo systemctl status mysql

For centos
sudo systemctl status mariadb
```
Checking the status of zookeeper:
```python
sudo systemctl status zookeeper
```

Checking the status of kafka:
```python
sudo systemctl status kafka
```

Checking the status of provision-manager:
```python
sudo systemctl status provision-manager
```

Checking the status of provision-worker:
```python
sudo systemctl status provision-worker
```

Checking if JMeter has been installed:
If after performing below steps, the **/jmeter** directory exists and inside it shows **apache-jmeter-5.3** directory, then our JMeter has been installed properly and can be used to run tests now.
```python
cd /usr/local/jmeter/
ls
```

### Restarting a service
These steps are to be done only when the status shown in the verification steps is **inactive (dead)**

Restarting the service of MySQL:
```python
For Ubuntu(Debian)
sudo systemctl restart mysql

For Centos
sudo systemctl restart mariadb
```

Restarting the service of zookeeper:
```python
sudo systemctl restart zookeeper
```

Restarting the service of kafka:
```python
sudo systemctl restart kafka
```

Restarting the service of provision-manager:
```python
sudo systemctl restart provision-manager
```

Restarting the service of provision-worker:
```python
sudo systemctl restart provision-worker
```

### Checking service files
For the services that we have created, there are service files which have been copied and kept inside the /etc/systemd/system directory. To access these we can use:
```python
cd /etc/systemd/system/ 
```

It will give a list of files inside this directory. If **provision-manager.service**, **provision-worker.service**, **zookeeper.service** and **kafka.service** are present inside the list of files then our service files have been copied successfully.
To view the contents of the file the command to be used is:
```python
vi provision-manager.service
```

```python
vi provision-worker.service
```
```python
vi zookeeper.service
```

```python
vi kafka.service
```