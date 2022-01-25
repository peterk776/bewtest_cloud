@echo on
set MAVEN_HOME=c:\dev\logaweb\tools\maven\maven3
set JAVA_HOME=c:\dev\java\jdk8u265-b01
set PATH=%PATH%;%MAVEN_HOME%\bin;
set PATH=%PATH%;%JAVA_HOME%\bin;
call mvn -DskipTests=true clean install
docker image build -t bewtest:latest .
docker compose down
docker compose up -d

rem docker container run --dns 192.168.1.61 -p 8888:8888 -e "http_proxy=pi-proxy1:8080" -e "https_proxy=pi-proxy1:8080" bewtest:latest

rem docker container run --dns 192.168.1.61 -e "http_proxy=pi-proxy1:8080" -e "https_proxy=pi-proxy1:8080" selemium-first-test:latest