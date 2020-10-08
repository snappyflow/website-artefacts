<!-- Copyright 2020 MapleLabs -->
<!-- Author: Anchal Gupta (Anchal.Gupta@maplelabs.com) -->
<!-- Description: Main README file for Provisioner. -->

![Provisioner](https://res.cloudinary.com/anchal-gupta/image/upload/v1596830627/Git%20Images/provisioner-logo_kh4cdd.png)

Provisioner is an application where you can easily provision multiple VMs & also analyze the real-time status of it so that you don't have to manually configure each VM.

# Kubernetes Helm charts ###

## Clone the Application :

```
git clone https://github.com/pramurthy/javasamples.git ... (at /home/ubuntu ... it asks for credentials)
```

## Installing the Chart

To install the chart with the release name `my-release`:

```python
cd ~/javasamples/provisioner/kubernetes 
```
For helm version 2 use this command:
```python
helm install ./provisioner/ --set global.key=<profile-key> --name my-release --namespace <my-namespace>
helm list -a
kubectl get pods -n my-namespace
```
For helm version 3 use this command:
```python
helm install my-release ./provisioner/ --set global.key=<profile-key>  --namespace <my-namespace>
helm list -a
kubectl get pods -n my-namespace
```

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

For helm version 2 use this command:
```console
helm delete --purge my-release
```

For helm version 3 use this command:
```console
helm delete  my-release
```

The command removes nearly all the Kubernetes components associated with the
chart and deletes the release.

## Configuration

The following table lists the configurable parameters of the drone charts and their default values.

Specify each parameter using the `--set key=value[,key=value]` argument to `helm install`.

Alternatively, a YAML file that specifies the values for the parameters can be provided while installing the chart. For example,

```bash
helm install --name my-release -f values.yaml stable/<chart>
```

> **Tip**: You can use the default [values.yaml](values.yaml)


| Parameter | Description | Default |
|-----------|-------------|---------|
| `global.key` | snappyflow profile key | |
| `global.sfappname` | snappyflow appname |  |
| `global.sfprojectname`  | snappyflow projectname |  |
| `global.sfappname_key`  | snappyflow appname key | `snappyflow/appname` |
| `global.sfprojectname_key` | snappyflow projectname key | `snappyflow/projectname` |
| `manager.replicaCount`  | The number of replicas of manager | `1` |
| `manager.container.image.repository`  | location of image to run | `snappyflowml/provisionmanager-with-sftrace` |
| `manager.container.image.tag`  | **manager** image tag | `1.0` |
| `manager.container.image.pullPolicy`  | **manager** image pull policy | `Always` |
| `manager.nodePort` | **manager** service nodeport | `30100` |
| `worker.replicaCount`  | The number of replicas of manager | `1` |
| `worker.container.image.repository`  | location of image to run | `snappyflowml/provisionworker-with-sftrace` |
| `worker.container.image.tag`  | **worker** image tag | `1.0` |
| `worker.container.image.pullPolicy`  | **worker** image pull policy | `Always` |
| `jmeter.enabled`  | Controller JMeter deployment, change value **false** if you don't want jmeter in deployment | `true` |
| `jmeter.replicaCount`  | The number of replicas of jmeter | `1` |
| `jmeter.container.image.repository`  | location of image to run | `justb4/jmeter` |
| `jmeter.container.image.tag`  | **jmeter** image tag | `5.1.1` |
| `jmeter.container.image.pullPolicy`  | **jmeter** image pull policy | `IfNotPresent` |
| `mysql.replicaCount`  | The number of replicas of mysql | `1` |
| `mysql.container.image.repository`  | location of image to run | `mysql` |
| `mysql.container.image.tag`  | **mysql** image tag | `5.7` |
| `mysql.container.image.pullPolicy`  | **mysql** image pull policy | `IfNotPresent` |
