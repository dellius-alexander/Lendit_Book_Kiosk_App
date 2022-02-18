# Default openjdk:18-ea-15-jdk-bullseye
ARG VARIANT=""
# Note: You can use any Debian/Ubuntu based image you want. 
FROM mcr.microsoft.com/vscode/devcontainers/java:0-${VARIANT}
# [Option] Use the OSS Moby Engine instead of the licensed Docker Engine
ARG USE_MOBY="true"
# [Option] Install zsh
ARG INSTALL_ZSH="true"
# [Option] Upgrade OS packages to their latest versions
ARG UPGRADE_PACKAGES="false"
# [Option] Enable non-root Docker access in container
ARG ENABLE_NONROOT_DOCKER="true"
# Install needed packages and setup non-root user. Use a separate RUN statement to add your
# own dependencies. A user of "automatic" attempts to reuse an user ID if one already exists.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ARG JAVA_VERSION=""
ARG SDKMAN_DIR=""

# GIT User Info
ARG GIT_USER=""
ARG GIT_EMAIL=""
# [Option] Install Maven
ARG INSTALL_MAVEN=""
ARG MAVEN_VERSION=""
# [Option] Install Gradle 
ARG INSTALL_GRADLE=""
ARG GRADLE_VERSION=""
# [Choice] Node.js version: none, lts/*, 16, 14, 12, 10
ARG NODE_VERSION=""
# Workspace/App directory
ARG APP_DIR=""
# Git repo
ARG GIT_REPO=""
# Git Branch
ARG GIT_BRANCH=""
# User Passwd
ARG USERCRED=""
# Additional Packages
ARG ADDITIONAL_PACKAGES=""
#####################################################################
ENV APP_DIR=${APP_DIR}
WORKDIR ${APP_DIR}
USER root
#####################################################################
# Install mycli commandline tool
# Copy scripts
COPY library-scripts/** /tmp/library-scripts/
# COPY --from=mycli:1.24.2 /usr/local/bin/mycli /usr/local/bin/
RUN mkdir -p ${APP_DIR} 
# [Optional] Uncomment this section to install additional OS packages.
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    mycli 

# Install Maven, Gradle, Node
# RUN if [ "${INSTALL_MAVEN}" == "true" ]; then su vscode -c \
#     "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi 

RUN if [ "${INSTALL_GRADLE}" == "true" ]; then su vscode -c \
    "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi
RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c \
    "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi
RUN mkdir -p ${APP_DIR} && sudo chown -R vscode:vscode ${APP_DIR}

# RUN sudo chown -R vscode:vscode ${APP_DIR}
RUN /bin/bash /tmp/library-scripts/docker-in-docker-debian.sh "true" "${USERNAME}" "${USE_MOBY}"
RUN rm -rf ~/.oh-my-zsh \
    && sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" "Y"
RUN apt-get update \
    && /bin/bash /tmp/library-scripts/common-debian.sh \
    "${INSTALL_ZSH}" "${USERNAME}" "${USER_UID}" "${USER_GID}" "${UPGRADE_PACKAGES}" "true" "true" "${ADDITIONAL_PACKAGES}"

# Install Maven
RUN /bin/bash /tmp/library-scripts/maven-debian.sh "${MAVEN_VERSION}" "${USERNAME}"

# Setup Repo
RUN cd "/home/${USERNAME}" && git clone "${GIT_REPO}" "$(basename -s .git ${GIT_REPO})" \
    && cd "$(basename -s .git ${GIT_REPO})" \
    && git config --global user.email "${GIT_EMAIL}" \
    && git config --global user.name "${GIT_USER}"  

# Setup for good measure
#
# ENV M2_HOME=/opt/apache-maven-${MAVEN_VERSION}
# ENV M2=$M2_HOME/bin
# ENV MAVEN_HOME=$M2_HOME/bin
# ENV PATH=$M2:$PATH
# ENV MAVEN_OPTS='-Xms256m -Xmx512m'
#
# Clean up
RUN apt-get autoremove -y && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*  /tmp/library-scripts/
#
RUN mkdir -p /home/$USERNAME/.vscode-server/extensions \
        /home/$USERNAME/.vscode-server-insiders/extensions \
    && chown -R $USERNAME \
        /home/$USERNAME/.vscode-server \
        /home/$USERNAME/.vscode-server-insiders
        
## [Optional] Uncomment this section to install additional OS packages.
#RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#    && apt-get -y install --no-install-recommends < ADD PACKAGES HERE >
#
# Setting the ENTRYPOINT to docker-init.sh will start up the Docker Engine 
# inside the container "overrideCommand": false is set in devcontainer.json. 
# The script will also execute CMD if you need to alter startup behaviors.
ENTRYPOINT [ "/usr/local/share/docker-init.sh" ]
CMD [ "sleep", "infinity" ]