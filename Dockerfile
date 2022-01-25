# syntax=docker/dockerfile:1

FROM openjdk:latest
COPY target/bewtest-0.0.1-SNAPSHOT.jar /usr/src/bewtest-0.0.1-SNAPSHOT.jar

# CMD java -cp /usr/src/test_selenium-1.0-SNAPSHOT-jar-with-dependencies.jar org.pko.SeleniumDockerPositionTest 172.17.0.2 172.16.200.121
# CMD java -cp /usr/src/test_selenium-1.0-SNAPSHOT-jar-with-dependencies.jar org.pko.SeleniumDockerPositionTest 172.17.0.2 loga-22-2.pi-asp.de
#with selenium-chrome hostname for compose
CMD java -jar /usr/src/bewtest-0.0.1-SNAPSHOT.jar