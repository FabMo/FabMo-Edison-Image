#!/bin/bash

#Abort on first error
#set -e
set -x

cd $(dirname $0)

remove all stopped docker sessions
sessions=$(docker ps -a -q -f status=exited)
if [ "x$session" != "x" ]; then
    docker rm -v $(docker ps -a -q -f status=exited)
fi

#Remove dangling images
images=$(docker images -f "dangling=true" -q)
if [ "x$images" != "x" ]; then
    docker rmi $(docker images -f "dangling=true" -q)
fi

#Ubuntu with general packages for building
docker build --tag edison/ubuntu-build ubuntu-build

#Prepare the build user account
docker build --tag edison/user edison-user

#The Edison source code
#docker build --tag edison/source edison-source
docker build --tag edison/source fabmo-edison-source

# Prepare the build and prefetch sources
docker build --tag edison/download edison-download

# Build the edison image
docker build --tag edison/image edison-image

docker run edison/image
CONTAINERID=$(docker ps -alq)
echo "CONTAINER: " $CONTAINERID
docker cp $CONTAINERID\:/home/edison/fabmo-edison-image/edison-src/out/current/build/release.zip ./release.zip
docker cp $CONTAINERID\:/home/edison/fabmo-edison-image/edison-src/out/current/build/release-windows.zip ./release-windows.zip

