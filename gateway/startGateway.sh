
VERSION=0.0.1-SNAPSHOT
PORT=7000
IMAGE_NAME=gateway-${PORT}

# shellcheck disable=SC2164
CUR_DIR=$(cd "$(dirname "$0")"; pwd)

# 删除 CONTAINER
# shellcheck disable=SC2006
container_id=`docker ps -a|grep ${IMAGE_NAME}|awk '{print $1}'`
if [ -n "${container_id}" ]; then
	docker rm -f "${container_id}"
fi

# 删除 IMAGE
# shellcheck disable=SC2006
old_image_id=`docker images -a|grep ${IMAGE_NAME}|awk '{print $3}'`
if [[ -n $old_image_id ]]; then
    docker rmi -f "${old_image_id}"
fi

# 构建
docker build -t ${IMAGE_NAME}:${VERSION} --build-arg jar_file=target/project-gateway-${VERSION}.jar .

# 启动
docker run --name ${IMAGE_NAME} -d -p ${PORT}:7000 -v "${CUR_DIR}"/target/lib:/home/lib ${IMAGE_NAME}:${VERSION}