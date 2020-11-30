DOMAIN_HOME=/woongjin/domain/user_projects/domains/bizmob
ADMIN_URL=t3://10.101.1.50:7001
USER_NAME=weblogic
PASSWORD=welcome1
 
# Stop WebLogic

${DOMAIN_HOME}/bin/stopWebLogic.sh ${USER_NAME} ${PASSWORD}  ${ADMIN_URL} 
