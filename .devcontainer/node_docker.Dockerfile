FROM selenium/node-docker:4.1.3-20220405
COPY ./nodeDocker/config.toml* /opt/bin/
#ENV DOCKER_HOST=127.0.0.1:2375
RUN ls -lia /opt/bin/