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
  steve763/gallery-manager:latest
echo "container started"
