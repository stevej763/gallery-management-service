echo "Deploying app to docker"

CONTAINER_NAME="gallery-manager"

if docker ps -a --format "{{.Names}}" | grep -q "^${CONTAINER_NAME}$"; then
  docker stop $CONTAINER_NAME
  docker rm --force $CONTAINER_NAME
fi

echo "starting container: ${CONTAINER_NAME}"
docker run \
  --name ${CONTAINER_NAME} \
  -d \
  -p 8300:8100 \
  -e SERVER_PORT=8100 \
  -e MONGO_PORT=27017 \
  -e MONGO_HOST=192.168.1.200 \
  -e MONGO_REQUIRES_AUTH=true \
  -e MONGO_USER=steve \
  -e MONGO_PASSWORD=password \
  -e MONGO_AUTH_DB=admin \
  -e DATABASE_NAME=gallery-management-docker \
  -e S3_HOSTNAME="http://192.168.1.200:9000" \
  -e S3_BUCKET_NAME="gallery-docker" \
  steve763/gallery-manager:latest
echo "container started"
