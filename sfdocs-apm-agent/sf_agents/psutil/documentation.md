##### Metric

##### Description

PSUtil Metric plugin is an agent-based plugin which captures the process running in Linux system. It returns the Running process details. 

##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: psutil</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 60</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>numprocess: 10</div>
      <div>sortby: pcpu</div>
    </div>
  </div>
</div>

The plugin collects Process ID, Username, CPU (%), CPU time, Memory (%), Resident Memory (%), Virtual Memory (%), Elapsed time , Processor, State Code of each process running in the machine and also has an option to sort the process with these fields.

**Example**: You can configure plugin to collect top 10 process which used High CPU (%) as in sample configuration.

##### Configuring parameters

*numprocess*:  Number of process to collect. Set numprocess to 0 or leave it empty to get all process details.Default value is 15.
*sortby*: sort the collected process based on this sortby option. Default value is pcpu. Possible values 
are,

```
                            uname	- Username
                            pid		- ProcessId
                            psr     - Processor
                            pcpu    - CPUPercent
                            cputime - CPUTime
                            pmem    - Memory Percent
                            rsz     - Resident Memory
                            vsz     - Virtual Memory
                            etime   - Elapsed Time
                            s       - State code
```



##### Documents

1.	List of Current process running in the machine (dropdown filter is added if PSUtil is configured in multiple instances) is displayed in Process Pane of PSUtil Dashboard.
2.	Process History Pane will have visualization of process utilization in the specified time interval.

##### Further Reading

[Linux](../linux/documentation.md), [LSOF](../lsof/documentation.md) and [netstat](../netstat/documentation.md) for other linux related monitoring.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).

