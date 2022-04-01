FROM hashicorp/vault:latest
#COPY ./vault/vault.conf* /tmp/config
ENV VAULT_ADDR='http://127.0.0.1:8200'