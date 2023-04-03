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
  -e VAULT_HOST: 192.168.1.200 \
  -e VAULT_PORT: 8250 \
  -e VAULT_TOKEN: password \
  -e VAULT_HTTP_SCHEME: http \
  -e VAULT_BACKEND: gallery \
  -p 8300:8100 \
  steve763/gallery-manager:latest
echo "container started"
