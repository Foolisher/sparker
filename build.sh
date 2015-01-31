#!/bin/bash


PROJ_ROOT=/usr/dev/workspace/sparker
SPARK_HOME=/usr/dev/workspace/spark

rm -rf lib && mvn dependency:copy-dependencies -DoutputDirectory=lib -o
rm ${PROJ_ROOT}/jars

ls lib | awk 'BEGIN{n=1}{if(n++!=1)printf(","); printf("%s/lib/%s", root, $1);}' root=${PROJ_ROOT}  \
 > ${PROJ_ROOT}/jars

cd ${SPARK_HOME}

./bin/spark-submit --jars `cat ${PROJ_ROOT}/jars` \
 --class simpler.CassandraSparker \
  ${PROJ_ROOT}/target/sparker-1.0-SNAPSHOT.jar \
  "APP" 10.211.55.6 9042

