#!/bin/bash
echo "build and run bewtest"

bpath=/home/peterk776/dev/bewtest
cd $bpath

bewtestDir="bewtest_cloud"
if [ -d $bewtestDir ]; then
  echo "using existing dir"
  cd bewtest_cloud
  git checkout .
else
  echo "File does not exist"
  mkdir bewtest_cloud
  git clone https://github.com/peterk776/bewtest_cloud.git
fi

echo "building..."
mvn clean install -DskipTests=true
echo "stop existing containers..."
sudo docker-compose down
sudo docker image rm bewtest
echo "build image for bewtest..."
sudo docker image build -t bewtest:latest .
sudo docker-compose pull
sudo docker-compose up -d