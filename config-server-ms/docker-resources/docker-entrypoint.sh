#!/bin/sh

set -e
set -x

/tmp/scripts/wait-for-it.sh  discovery:8761 -- echo "discovery started"

echo "Command :: java -jar ${JAVA_OPTS} /tmp/config-server-ms.jar"

java -jar ${JAVA_OPTS} /tmp/config-server-ms.jar




