# Copyright (c) 2017, 2021, Oracle and/or its affiliates.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; version 2 of the License.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA

FROM oraclelinux:8-slim

ARG MYSQL_SERVER_PACKAGE=mysql-community-server-minimal-8.0.28
ARG MYSQL_SHELL_PACKAGE=mysql-shell-8.0.28

# Setup repositories for minimal packages (all versions)
RUN rpm -U https://repo.mysql.com/mysql-community-minimal-release-el8.rpm \
  && rpm -U https://repo.mysql.com/mysql80-community-release-el8.rpm

# Install server and shell 8.0
RUN microdnf update && echo "[main]" > /etc/dnf/dnf.conf \
  && microdnf install -y $MYSQL_SHELL_PACKAGE \
  && microdnf install -y --disablerepo=ol8_appstream \
   --enablerepo=mysql80-server-minimal $MYSQL_SERVER_PACKAGE \
  && microdnf clean all \
  && mkdir /docker-entrypoint-initdb.d

COPY library-scripts/prepare-image.sh* /prepare-image.sh
RUN /bin/bash /prepare-image.sh \
  && rm -f /prepare-image.sh
# set mysql unix daemon socket
ENV MYSQL_UNIX_PORT /var/lib/mysql/mysql.sock
# copy Files
COPY .secret/my.cnf* /etc/mysql/conf.d/
COPY library-scripts/docker-entrypoint.sh* /entrypoint.sh
COPY library-scripts/healthcheck.sh* /healthcheck.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
HEALTHCHECK CMD /healthcheck.sh
EXPOSE 3306 33060 33061
CMD ["mysqld"]