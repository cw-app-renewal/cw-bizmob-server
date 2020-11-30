DOMAIN_HOME=/woongjin/domain/user_projects/domains/bizmob
ADMIN_URL=t3://localhost:7001
SERVER_NAME=bizmob1
USER_NAME=weblogic
PASSWORD=welcome1

 # Stop WebLogic

 $DOMAIN_HOME/bin/stopManagedWebLogic.sh $SERVER_NAME $ADMIN_URL $USER_NAME $PASSWORD
