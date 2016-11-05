#!/usr/bin/env bash
export PATH=$JAVA_HOME/bin:$PATH

#java -Dlog4j.configurationFile=cfg/log4j2.xml -jar server.jar 8080

#так запускает тестер
java -jar server.jar
