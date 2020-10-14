##### Metric

##### Description

LSOF (list of open files) Metric plugin capture the open files in the running Linux distribution. This plugin returns the list of files opened based on the file descriptor.

##### Prerequisites

*lsof* command needs to be installed before running this plugin.

- To install *lsof* in Centos / RHEL

  ```
  sudo yum install lsof
  ```

- To install *lsof* in Ubuntu

  ```
  sudo apt-get install lsof
  ```

After installing lsof, run the following command to verify the successful installation of lsof.

```
lsof -v
```

 Expected output:

```
lsof version information:
    revision: 4.87
    latest revision: ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/
    latest FAQ: ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/FAQ
    latest man page: ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/lsof_man
    constructed: Tue Oct 30 16:28:19 UTC 2018
    constructed by and on: mockbuild@x86-01.bsys.centos.org
    compiler: cc
    compiler version: 4.8.5 20150623 (Red Hat 4.8.5-36) (GCC)
    compiler flags: -DLINUXV=310000 -DGLIBCV=217 -DHASIPv6 -DHASSELINUX -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -DHAS_STRFTIME -DLSOF_VSTR="3.10.0" -O2 -g -pipe -Wall -Wp,-D_FORTIFY_SOURCE=2 -fexceptions -fstack-protector-strong --param=ssp-buffer-size=4 -grecord-gcc-switches -m64 -mtune=generic
    loader flags: -L./lib -llsof  -lselinux
    system info: Linux x86-01.bsys.centos.org 3.10.0-693.17.1.el7.x86_64 #1 SMP Thu Jan 25 20:13:58 UTC 2018 x86_64 x86_64 x86_64 GNU/Linux
```

Note: version may vary depending upon the Linux distribution.

#####  Configuration Settings
Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

<div class="sfpollerExample">
  <div> - name: lsof </div>
  <div class="innerLeft">
    <div>enabled: true </div>
    <div>interval: 600 </div>
    <div>config:</div>
    <div class="innerLeft">
      <div>completeStats: false </div>
      <div>numProcess: 5 </div>
      <div>sortFilter: DIR </div>
    </div>
  </div>
</div>


##### Operating Instructions

This plugin can run in two different modes: *summary* and *complete stats*.

- In *summary* mode, it returns only the count of files open of each file descriptor type (like DIR, CHR, REG etc.) and the files open, in total.
- In *complete stats* mode, it returns the entire list of open files in the machine, process wise along with the process id.

###### Configuring parameters

*completeStats*: Since the list of all opened files can be huge in number, plugin is by default configured in summary mode (i.e. *completeStats: false*) . To enable complete stats, set “*completeStats: true*” in plugin configuration. 

*numProcess*: Number of top N processes with maximum number of files opened. For example, if numProcess is set to 5, it returns top 5 process stats with maximum number of files opened. Set numProcess to 0 to get all process details. Default value is 10.

*sortFilter*:  Selection of top N processes only for a particular file descriptor type can be manipulated using sortFilter.  If set, it returns the stats of top N processes with maximum number of files opened of file descriptor type M (sort filter). For example, to get top 10 processes with maximum directories opened, *numProcess* should be set to 10 and *sortFilter* as ‘*DIR’.* Set sort filter to none if no sorting is required. All the options of sortFilter value are available in Dropdown on APM. Following are the options available: *none, CHR, DIR, REG, FIFO, IP, netlink, socket, a_inode*. Default value is *none*.

Note: All the traffic related (IPv4, IPv6) file types are combined as *IPv4/6* and unix, sockets into *socket* for better analysis.

##### Documents

Use LSOF dashboard for analysis.

- Summary stats documents are present with document Type “lsofSummary”. Summary stats are displayed under “Summary” pane on LSOF dashboard.
- Complete stats documents are with document Type “lsofStats”. *Open Files* pane shows the number of files opened by each process. This can be filtered based on the file type using the *sort by* dropdown. Likewise, *Process Details*  pane show stats per process.

##### Further reading

[Linux](../linux/documentation.md), [psutil](../psutil/documentation.md) and [netstat](../netstat/documentation.md) for other linux related monitoring.



For help with plugins, please reach out to support@snappyflow.io.
