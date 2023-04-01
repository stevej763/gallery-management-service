echo "Running app in docker"
echo "Compiling"
mvn clean package -DskipTests=true -Dspring.profiles.active=docker
echo "Compilation completed"
echo "Building image"
docker build -t gallery-management-service:1.0 .
echo "image built"

CONTAINER_NAME="gallery-management"

# Check if container with the same name already exists
if docker ps -a --format "{{.Names}}" | grep -q "^${CONTAINER_NAME}$"; then
  # If container exists, stop and remove it
  docker stop $CONTAINER_NAME
  docker rm --force $CONTAINER_NAME
fi

echo "starting container"
docker run \
  --name ${CONTAINER_NAME} \
  -p 8200:8100 \
  -e SERVER_PORT=8100 \
  -e MONGO_PORT=27017 \
  -e MONGO_HOST=192.168.1.200 \
  -e MONGO_REQUIRES_AUTH=true \
  -e MONGO_USER=steve \
  -e MONGO_PASSWORD=password \
  -e MONGO_AUTH_DB=admin \
  -e DATABASE_NAME=gallery-management-docker \
  -e S3_BASE_URL="http://test.com" \
  gallery-management-service:1.0
echo "container started"
