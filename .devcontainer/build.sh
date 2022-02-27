#!/usr/bin/env bash
set -e
ENVFILE=".env"
export $(cat ${ENVFILE} | grep -v '#' | awk '/=/ {print $1}')
docker build \
       --no-cache --rm=true \
       --build-arg UPGRADE_PACKAGES=true \
       --build-arg JAVA_VERSION=11 \
       --build-arg SDKMAN_DIR=/root/.sdkman/bin/sdkman-init.sh \
       --build-arg VARIANT=bionic \
       --build-arg INSTALL_MAVEN=false \
       --build-arg NODE_VERSION=16 \
       --build-arg APP_DIR=/home/vscode/Lendit_Book_Kiosk_App \
       --build-arg GIT_REPO=https://github.com/dellius-alexander/Lendit_Book_Kiosk_App.git \
       --build-arg GIT_BRANCH=main \
       --build-arg MAVEN_VERSION=3.8.4 \
       --build-arg GIT_USER="${GIT_USER}" \
       --build-arg GIT_EMAIL="${GIT_EMAIL}" \
       --build-arg INSTALL_ZSH="true" \
       --build-arg USE_MOBY="true" \
       --build-arg USERNAME="${USERNAME}" \
       --build-arg USERCRED="" \
       --build-arg ADDITIONAL_PACKAGES="" \
       --build-arg JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 \
       -t dalexander2israel/lendit_book_kiosk:dev-0.2 \
       --file Dockerfile .

# docker push dalexander2israel/lendit_book_kiosk:base_v1