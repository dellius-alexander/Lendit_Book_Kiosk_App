#!/usr/bin/env bash
set -e
ENVFILE=".env"
export $(cat ${ENVFILE} | grep -v '#' | awk '/=/ {print $1}')
#
docker run \
    -it -d \
    -e MYSQL_DATABASE="${MYSQL_DATABASE}" \
    -e MYSQL_HOST="${MYSQL_HOST}" \
    -e MYSQL_ROOT_PASSWD="${MYSQL_PASSWD}" \
    -e MYSQL_USER="${MYSQL_USER}" \
    -e MYSQL_PORT="${MYSQL_PORT}" \
    -p "${MYSQL_PORT}:${MYSQL_PORT}" -p 33060:33060 \
    --name Lendit_Book_Kiosk_db \
    lendit_book_kiosk_app_devcontainer_app:latest \
    && docker logs -f Lendit_Book_Kiosk_db
    