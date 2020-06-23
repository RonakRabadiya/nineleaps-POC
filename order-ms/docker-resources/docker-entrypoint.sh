#!/bin/sh

set -e
set -x

/tmp/scripts/wait-for-it.sh  cassandra-node1:9042 -- echo "CASSANDRA Node1 started"
/tmp/scripts/wait-for-it.sh  discovery:8761 -- echo "discovery started"
/tmp/scripts/wait-for-it.sh  config-server:8888 -- echo "configserver started"
/tmp/scripts/wait-for-it.sh  gateway-ms:8765 -- echo "gateway-ms started"
/tmp/scripts/wait-for-it.sh  172.16.2.10:9092 -- echo "kafka started"


echo "java -jar ${JAVA_OPTS} /tmp/order-ms.jar"

java -jar ${JAVA_OPTS} /tmp/order-ms.jar




