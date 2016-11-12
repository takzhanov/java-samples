#!/usr/bin/env bash
export PATH=$JAVA_HOME/bin:$PATH

#java -Dlog4j.configurationFile=cfg/log4j2.xml -jar server.jar 8080

#так запускает тестер
java -jar server.jar

#так можно запускать сервер и клиент в консоли сразу
#java -cp server.jar com.github.takzhanov.stepic.hw07.MultiThreadedServer &
#java -cp server.jar com.github.takzhanov.stepic.hw07.LoadClient

