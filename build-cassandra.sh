#!/bin/bash


PROJ_ROOT='/usr/dev/workspace/sparker'
SPARK_HOME='/usr/dev/workspace/spark'

mvn clean package  -Dmaven.test.skip=true

cd ${SPARK_HOME}

./bin/spark-submit --class simpler.CassandraConnectorTest \
  ${PROJ_ROOT}/target/sparker-1.0-SNAPSHOT.jar \
  127.0.0.1 10.211.55.9

