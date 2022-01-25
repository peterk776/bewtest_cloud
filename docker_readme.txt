docker image build -t selemium-first-test:latest .
docker container run --dns 192.168.118.1 selemium-first-test:latest


#docker compose
docker compose up -d
docker compose down