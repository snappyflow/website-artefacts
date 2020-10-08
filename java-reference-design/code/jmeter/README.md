<!-- Copyright 2020 MapleLabs -->
<!-- Author: Anchal Gupta (Anchal.Gupta@maplelabs.com) -->
<!-- Description: README file for JMeter Installation. -->

## JMeter Installation :

To start, create a directory in `/home/{ user }` called `Downloads` to store your downloads :

```
mkdir ~/Downloads
```

Use curl to download the JMeter binaries :

```
curl "https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.3.tgz" -o ~/Downloads/jmeter.tgz
```

Create a directory called jmeter. This will be the base directory of the jmeter installation :

```
mkdir ~/jmeter
```

Extract the archive you downloaded using the tar command :

```
tar -xvzf ~/Downloads/jmeter.tgz --strip 1 -C ~/jmeter
```

Run the following test plan to generate continuous load (replace localhost with IP address of server if its not localhost):

```
~/jmeter/bin/jmeter -n -t javasamples/provisioner/jmeter/Continuous_Load_Generator.jmx -Jserver=localhost
```
