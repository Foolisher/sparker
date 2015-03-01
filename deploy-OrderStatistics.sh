#!/bin/bash


PROJ_ROOT='/usr/dev/workspace/sparker'
SPARK_HOME='/Users/wanggen/workspace/spark-1.2.0'

mvn clean package  -Dmaven.test.skip=true

cd ${SPARK_HOME}

./bin/spark-submit \
	--class simpler.Demo2 \
  ${PROJ_ROOT}/target/sparker-1.0-SNAPSHOT.jar
