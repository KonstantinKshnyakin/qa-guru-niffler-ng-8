#!/bin/bash
source ./docker.properties
export COMPOSE_PROFILES=test
export PROFILE=docker
export PREFIX="${IMAGE_PREFIX}"

export HEAD_COMMIT_MESSAGE="local build"
export ARCH=$(uname -m)

docker compose down
docker_containers=$(docker ps -a -q)
docker_images=$(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'niffler')

BUILD=${1:-"run"}
BROWSER=${2:-"chrome"}

if [ ! -z "$docker_containers" ]; then
  echo "### Stop containers: $docker_containers ###"
  docker stop $docker_containers
  docker rm $docker_containers
fi

if [[ "$BUILD" == "rebuild" && -n "$docker_images" ]]; then
  echo "### Remove images: $docker_images ###"
  docker rmi "$docker_images"
  echo '### Java version ###'
  java --version
  bash ./gradlew clean
  bash ./gradlew jibDockerBuild -x :niffler-e-2-e-tests:test
fi

echo "### Selected browser: $BROWSER ###"
export BROWSER

docker build -f ./niffler-e-2-e-tests/Dockerfile -t "${PREFIX}/niffler-e-2-e-tests:latest" .

docker pull selenoid/vnc_chrome:127.0
docker pull selenoid/vnc_firefox:125.0
docker compose --profile e2e up -d
docker ps -a
