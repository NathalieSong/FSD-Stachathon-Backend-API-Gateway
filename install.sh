docker-compose down
docker image rm emart-api-gateway
docker build . -t emart-api-gateway
docker-compose up -d