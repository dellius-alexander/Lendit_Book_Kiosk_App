#!/usr/bin/env bash
###############################################################################
MAVEN_VERSION=${1:-"3.8.4"}
USERNAME=${2:-"vscode"}
curl -fsSLo  /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz  \
https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
tar xzvf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz  -C  /opt/
ENV_EXPORTS="""\n\
export M2_HOME=/opt/apache-maven-${MAVEN_VERSION} \n\
export M2=/opt/apache-maven-${MAVEN_VERSION}/bin \n\
export PATH=/opt/apache-maven-${MAVEN_VERSION}/bin:$PATH \n\
export MAVEN_OPTS='-Xms256m -Xmx512m' \n\
"""
groupadd mvn
usermod -aG mvn ${USERNAME}
newgrp mvn
printf "${ENV_EXPORTS}" >> "/home/${USERNAME}/.zshrc"
printf "${ENV_EXPORTS}" >> "/home/${USERNAME}/.bashrc"

# source "/home/${USERNAME}/.bashrc"
# source "/home/${USERNAME}/.zshrc"

ln -sf  "/opt/apache-maven-${MAVEN_VERSION}/bin/mvn" /usr/local/bin






