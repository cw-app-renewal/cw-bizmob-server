#!/bin/sh
USER_NAME=weblogic
DOMAIN_NAME=bizmob
SERVER_NAME=bizmob1
ADMIN_URL=t3://localhost:7001
DOMAIN_HOME=/woongjin/domain/user_projects/domains/bizmob
LOG_DIR=/logs/was/${SERVER_NAME}
LOG_NAME=$SERVER_NAME
SMART_HOME_PATH=/app/webapp/SMART_HOME
JAVA_LIBRARY_PATH=/app/webapp/SMART_HOME/lib-sap

# Check User Name
IAM=`id | awk '{print substr($1, 1, index($1,")")-1 )}' | awk '{print substr($1, index($1,"(")+1 )}'`

# Check startup user validation
if [ $USER_NAME != $IAM ]
then
echo "Startup Error :[SALT-WLS001] User validation is failed. This instance has been started as \"$IAM\", actual script owner is
 \"$USER_NAME\""
 exit
 fi

 # Check process status
 PID=`ps -ef|grep java|grep :${DOMAIN_NAME}_${SERVER_NAME} |awk '{print $2}'`
 if [ "$PID" != "" ]
 then
 echo "Startup Error :[SALT-WLS002] \"${DOMAIN_NAME}_${SERVER_NAME}\" server is already running !!!"

 exit
 fi

########### Creat Log Directory ##########
mkdir -p $LOG_DIR
mkdir -p $LOG_DIR/backup
mkdir -p $LOG_DIR/wls
mkdir -p $LOG_DIR/heapdump
mkdir -p $LOG_DIR/gc

mv $LOG_DIR/$LOG_NAME.out $LOG_DIR/backup/$LOG_NAME.out.`date '+20%y%m%d_%H%M%S'`

########## Custom Args Start ##########
#Common start Args
        USER_MEM_ARGS="-D:${DOMAIN_NAME}_${SERVER_NAME} -DSMART_HOME=${SMART_HOME_PATH} -Djava.library.path=${JAVA_LIBRARY_PATH} -Djava.awt.headless=true -Xms1024m -Xmx1024m -XX:MaxPermSize=256m"
#64bit JDK
        ## only HP
        #USER_MEM_ARGS="${USER_MEM_ARGS} -d64"

#GC (not setted)

#GC Log
        ## General
        USER_MEM_ARGS="${USER_MEM_ARGS} -verbose:gc -Xloggc:${LOG_DIR}/gc/${SERVER_NAME}_GC_`date '+%y%m%d_%H%M%S'`.gc"

        ##HP only
        #USER_MEM_ARGS="${USER_MEM_ARGS} -Xverbosegc:file=${LOG_DIR}/gc/${SERVER_NAME}_GC_`date '+%y%m%d_%H%M%S'`.gc"

        ##only IBM
        #USER_MEM_ARGS="${USER_MEM_ARGS} -Xverbosegclog:${LOG_DIR}/gc/${SERVER_NAME}_GC_`date '+%y%m%d_%H%M%S'`.gc"

#HeapDump
        ## hp : over 1.4.2.10 or over 1.5.0.03
        ## sun :  over 1.4.2_12 or over 1.5.0_07
        USER_MEM_ARGS="${USER_MEM_ARGS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_DIR}/heapdump"

        ##IBM only ( kill -3 <pid> makes heap dump setting)
        #export IBM_HEAPDUMP=true
        #export IBM_HEAP_DUMP=true
        #export IBM_HEAPDUMP_OUTOFMEMORY=true
        #export IBM_JAVADUMP_OUTOFMEMORY=true
        #export IBM_HEAPDUMPDIR=${LOG_DIR}/heapdump
        #export IBM_JAVACOREDIR=${LOG_DIR}/heapdump

#Jconsole using
        #USER_MEM_ARGS="${USER_MEM_ARGS} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

#Common end Args
        export USER_MEM_ARGS

########## Custom Args End ##########

nohup  $DOMAIN_HOME/bin/startManagedWebLogic.sh $SERVER_NAME $ADMIN_URL >> $LOG_DIR/$LOG_NAME.out 2>&1 &
