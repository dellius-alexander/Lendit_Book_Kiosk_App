FROM dalexander2israel/lendit_book_kiosk:base_v1
#####################################################################
# BUILD ARGUMENTS [ --build-args NAME=VALUE ]
ARG GIT_USER=""
ARG GIT_EMAIL=""
ARG GIT_REPO=""
ARG GIT_BRANCH=""
ARG USERNAME=vscode
USER root
# Setup Repo
RUN mkdir -p "/home/${USERNAME}/$(basename -s .git ${GIT_REPO})" \
    && cd "/home/${USERNAME}" \
    && if [ ! -d "/home/${USERNAME}/$(basename -s .git ${GIT_REPO})" ]; then \
    git clone "${GIT_REPO}" "/home/${USERNAME}/$(basename -s .git ${GIT_REPO})"; fi \
    && cd "/home/${USERNAME}/$(basename -s .git ${GIT_REPO})" \
    # && git remote add origin ${GIT_REPO} \
    && git config --global user.email "${GIT_EMAIL}" \
    && git config --global user.name "${GIT_USER}" \
    && ls -lia -R 


RUN mkdir -p /home/$USERNAME/.vscode-server/extensions \
        /home/$USERNAME/.vscode-server-insiders/extensions \
    && chown -R $USERNAME \
        /home/$USERNAME/.vscode-server \
        /home/$USERNAME/.vscode-server-insiders

# Clean up
RUN apt-get autoremove -y && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*  /tmp/library-scripts/

## [Optional] Uncomment this section to install additional OS packages.
#RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#    && apt-get -y install --no-install-recommends < ADD PACKAGES HERE >
#
# Setting the ENTRYPOINT to docker-init.sh will start up the Docker Engine 
# inside the container "overrideCommand": false is set in devcontainer.json. 
# The script will also execute CMD if you need to alter startup behaviors.
ENTRYPOINT [ "/usr/local/share/docker-init.sh" ]
CMD [ "sleep", "infinity" ]