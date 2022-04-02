FROM  dalexander2israel/lendit_book_kiosk:dev-0.2
#####################################################################
# BUILD ARGUMENTS [ --build-args NAME=VALUE ]
ARG GIT_USER=""
ARG GIT_EMAIL=""
ARG GIT_REPO=""
ARG GIT_BRANCH=""
ARG USERNAME=vscode

## [Optional] Uncomment this section to install additional OS packages.
#RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#    && apt-get -y install --no-install-recommends < ADD PACKAGES HERE >
#
# MySQL USER config file COPIED to .my.cnf dotfile
COPY .secret/my.cnf* /home/"${USERNAME}"/.my.cnf 
#
# RUN mkdir -p /home/"${USERNAME}"/.m2 
RUN chown ${USERNAME}:${USERNAME} -R /home/${USERNAME}/
#
# Setting the ENTRYPOINT to docker-init.sh will start up the Docker Engine 
# inside the container "overrideCommand": false is set in devcontainer.json. 
# The script will also execute CMD if you need to alter startup behaviors.
ENTRYPOINT [ "/usr/local/share/docker-init.sh" ]
CMD [ "sleep", "infinity" ]