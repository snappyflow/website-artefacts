##### Logger

##### Description

PostgreSQL logger plugin collects general logs and slow query logs, PostgreSQL general log are accessible from the log section, slow queries are displayed in the dashboard.

##### Prerequisites

1. ##### Enable PostgreSQL general logs

   This needs to enable this in conf file ,If you are unsure about the conf file path login to postgres and execute the command as below,

   ```
   postgres=# show config_file;
   
              config_file            
   ----------------------------------
   
    /data/pgsql/data/postgresql.conf
   (1 row)
   ```

   We can also take locate the data directory path for later use by execute the command below,

   

   ```
   postgres=# show data_directory;
     data_directory  
   ------------------
    /data/pgsql/data
   (1 row)
   
   ```

   Open the config file and uncomment/configure the “log_min_messages” to required level, now logs will be collected in specified file. Set log_line_prefix to specific format like below,

   

   ```
   log_line_prefix = '< %m > '
   ```

2. ##### Enable Slow Query Logs.

   In the config file search for “log_min_duration_statement” and enable/configuring this parameter with value so the query which takes more than specified milliseconds will be captured and logged.

   **Example**: Configuring *log_min_duration_statement = 200* will log the query which takes more than 200ms to executed which is considered as slow query.



##### Configuration Settings

Add the plugin configuration in config.yaml file under /opt/sfagent/ directory as follows to enable this plugin

Config for general log ,  

<div class="sfpollerExample">
  <div> - name: postgres-general</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300</div>
    <div>config:</div>
    <div class="innerLeft">
    <div>log_path: /data/pgsql/data/pg_log/postgresql-*.log,/var/log/postgresql/postgresql-10-main.log</div>
    <div>log_level:</div>
    <div>- error</div>
    <div>- warning</div>
    <div>- info</div>
    <div>- log</div>
</div>
  </div>
</div>

Config for Slow Query Logs,

<div class="sfpollerExample">
  <div> - name: postgres-slowquery</div>
  <div class="innerLeft">
    <div>enabled: true</div>
    <div>interval: 300</div>
    <div>config:</div>
    <div class="innerLeft">
      <div>log_path: /data/pgsql/data/pg_log/postgresql-*.log,/var/log/postgresql/postgresql-10-main.log</div>
    </div>
  </div>
</div>



##### Documents

General logs are categorized under log type postgres-general and have configured log level. Slow queries are displayed in dashboard

[MySQL](../mysql/documentation.md) for other database related monitoring.

For help with plugins, please reach out to [support@snappyflow.io](mailto:support@snappyflow.io).


