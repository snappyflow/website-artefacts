<!-- Copyright 2020 MapleLabs -->
<!-- Author: Anchal Gupta (Anchal.Gupta@maplelabs.com) -->
<!-- Description: README file for Ubuntu (debian) Based Setup. -->

![Provisioner](https://res.cloudinary.com/anchal-gupta/image/upload/v1596830627/Git%20Images/provisioner-logo_kh4cdd.png)

Provisioner is an application where you can easily provision multiple VMs & also analyze the real-time status of it so that you don't have to manually configure each VM.

<hr />

## Ubuntu (Debian) Based Setup :

> **Contents :**
> * [Mandatory Pre-Installation Steps](#mandatory-pre-installation-steps-)
> * [Kafka Installation](#kafka-installation-)
> * [MySQL Installation](#mysql-installation-)

<br />

### Mandatory Pre-Installation Steps : 

```
sudo apt-get update

sudo apt-get upgrade

sudo apt install openjdk-8-jdk

sudo apt install maven

sudo apt install wget
```

<br />

### Kafka Installation :

<!-- **References :** https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-18-04  -->

<!-- (Download latest Kafka from this URL : https://kafka.apache.org/downloads) -->

**Creating a User for Kafka :** 

Logged in as your non-root sudo user, create a user called kafka with the useradd command :

```
sudo useradd kafka -m
```

Set the password using passwd :

```
sudo passwd kafka
```

Add the kafka user to the sudo group with the adduser command, so that it has the privileges required to install Kafka’s dependencies :

```
sudo adduser kafka sudo
```

**Download, install and run Kafka :** 

To start, create a directory in `/home/kafka` called `Downloads` to store your downloads :

```
sudo mkdir /home/kafka/Downloads
```

Use curl to download the Kafka binaries :

```
sudo curl "https://downloads.apache.org/kafka/2.5.1/kafka_2.12-2.5.1.tgz" -o /home/kafka/Downloads/kafka.tgz
```

Create a directory called kafka and change to this directory. This will be the base directory of the Kafka installation :

```
sudo mkdir /home/kafka/kafka && cd /home/kafka/kafka
```

Extract the archive you downloaded using the tar command :

```
sudo tar -xvzf /home/kafka/Downloads/kafka.tgz --strip 1
```

Change the folder permission :

```
sudo chmod -R 777 /home/kafka/kafka
```

**Creating Systemd Unit Files and Starting the Kafka Server :**

Create the unit file for zookeeper :

```
sudo nano /etc/systemd/system/zookeeper.service
```

Enter the following unit definition into the file (/etc/systemd/system/zookeeper.service) :

```
[Unit]
Requires=network.target remote-fs.target
After=network.target remote-fs.target

[Service]
Type=simple
User=kafka
ExecStart=/home/kafka/kafka/bin/zookeeper-server-start.sh /home/kafka/kafka/config/zookeeper.properties
ExecStop=/home/kafka/kafka/bin/zookeeper-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
```

Create the systemd service file for kafka :

```
sudo nano /etc/systemd/system/kafka.service
```

Enter the following unit definition into the file (/etc/systemd/system/kafka.service) :

```
[Unit]
Requires=zookeeper.service
After=zookeeper.service

[Service]
Type=simple
User=kafka
ExecStart=/bin/sh -c '/home/kafka/kafka/bin/kafka-server-start.sh /home/kafka/kafka/config/server.properties > /home/kafka/kafka/kafka.log 2>&1'
ExecStop=/home/kafka/kafka/bin/kafka-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
```

Now that the units have been defined, start Kafka with the following command :

```
sudo systemctl start kafka
```

To ensure that the server has started successfully, check the journal logs for the kafka unit (Optional) :

```
sudo journalctl -u kafka
```

Status Check (Optional) : 

```python
# Zookeeper :
sudo systemctl status zookeeper

# Kafka :
sudo systemctl status kafka
```

<!-- Troubleshoot : If Kafka is not running ... delete kafka logs -->

While we have started the kafka service, if we were to reboot our server, it would not be started automatically. To enable kafka on server boot, run :

```
sudo systemctl enable kafka
```

<br />

### MySQL Installation :

<!-- References :  https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04-->

**Installing MySQL :**

To install it, update the package index on your server with apt :

```
sudo apt update
```

Then install the default package :

```
sudo apt install mysql-server
```

**Configuring MySQL :** 

Run the security script :

```
sudo mysql_secure_installation
```

> Securing the MySQL server deployment.
> 
> Connecting to MySQL using a blank password.
> 
> VALIDATE PASSWORD PLUGIN can be used to test passwords
> and improve security. It checks the strength of password
> and allows the users to set only those passwords which are
> secure enough. Would you like to setup VALIDATE PASSWORD plugin?
> 
> Press y|Y for Yes, any other key for No: **No**
>
> Please set the password for root here.
> 
> New password: **root**
> 
> Re-enter new password: **root**
>
> By default, a MySQL installation has an anonymous user,
> allowing anyone to log into MySQL without having to have
> a user account created for them. This is intended only for
> testing, and to make the installation go a bit smoother.
> You should remove them before moving into a production
> environment.
> 
> Remove anonymous users? (Press y|Y for Yes, any other key for No) : **y**
> 
> Success.
> 
> Normally, root should only be allowed to connect from
> 'localhost'. This ensures that someone cannot guess at
> the root password from the network.
> 
> Disallow root login remotely? (Press y|Y for Yes, any other key for No) : **No**
> 
>  ... skipping.
>
> By default, MySQL comes with a database named 'test' that
> anyone can access. This is also intended only for testing,
> and should be removed before moving into a production
> environment.
> 
> Remove test database and access to it? (Press y|Y for Yes, any other key for No) : **y**
>  - Dropping test database...
> Success.
> 
>  - Removing privileges on test database...
> Success.
> 
> Reloading the privilege tables will ensure that all changes
> made so far will take effect immediately.
> 
> Reload privilege tables now? (Press y|Y for Yes, any other key for No) : **y**
>
> Success.
> 
> All done!

**Adjusting User Authentication and Privileges :** 

In order to use a password to connect to MySQL as root, you will need to switch its authentication method from `auth_socket` to `mysql_native_password`. To do this, open up the MySQL prompt from your terminal :

```
sudo mysql
```

Next, check which authentication method each of your MySQL user accounts use with the following command :

```
SELECT user,authentication_string,plugin,host FROM mysql.user;
```

To configure the root account to authenticate with a password, run the following ALTER USER command. For demo purpose, we are using password as `root`.

```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
```

Then, run FLUSH PRIVILEGES which tells the server to reload the grant tables and put your new changes into effect :

```
FLUSH PRIVILEGES;
```

Check the authentication methods employed by each of your users again to confirm that root no longer authenticates using the auth_socket plugin :

```
SELECT user,authentication_string,plugin,host FROM mysql.user;
```

Once you confirm this on your own server, you can exit the MySQL shell :

```
exit
```
