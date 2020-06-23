#!/bin/sh

set -e
set -x

/tmp/scripts/wait-for-it.sh  discovery:8761 -- echo "discovery started"
/tmp/scripts/wait-for-it.sh  config-server:8888 -- echo "configserver started"

echo "Command :: java -jar ${JAVA_OPTS} /tmp/gateway-ms.jar"

java -jar ${JAVA_OPTS} /tmp/gateway-ms.jar




