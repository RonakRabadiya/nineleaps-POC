#!/bin/sh

set -e
set -x

/tmp/scripts/wait-for-it.sh  cassandra-node1:9042 -- echo "CASSANDRA Node1 started"




echo "java -jar ${JAVA_OPTS} /tmp/product-ms.jar"

java -jar ${JAVA_OPTS} /tmp/product-ms.jar




