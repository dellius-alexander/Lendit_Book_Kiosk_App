#!/usr/bin/env bash

DOCKER_USER_EMAIL=${1:-""}
if [ $(cat $1 | grep -i '@' ) ]
# Download the latest binary
curl -fsSL https://github.com/docker/docker-credential-helpers/releases/download/v0.6.3/docker-credential-pass-v0.6.3-amd64.tar.gz \
-o /tmp/docker-credential-pass-v0.6.3-amd64.tar.gz
# Extract to PATH, in this case to ~/.local/bin/docker-credential-pass
tar -xf /tmp/docker-credential-pass-v0.6.3-amd64.tar.gz -C .local/bin
# Assign executable permission to Owner & Group
sudo chmod 550 ~/.local/bin/docker-credential-pass
#install pass
sudo apt-get install -y gpg pass
# clone the git repo
git clone https://github.com/zx2c4/password-store.git /tmp/password-store 
cd password-store
make install >> /var/log/password-install-$(date +'%Y-%m-%dT%H:%M:%S').log

# Create a file gen-key-script below and
# Run the command: "gpg --batch --gen-key gen-key-script"
# $ gpg2 --verbose --batch --gen-key - <<EOF
# %echo Generating a basic OpenPGP key
# Key-Type: DSA
# Key-Length: 2048
# Subkey-Type: ELG-E
# Subkey-Length: 1024
# Name-Real: Joe Tester
# Name-Comment: with stupid passphrase
# Name-Email: ${EMAIL}
# Expire-Date: 0
# Passphrase: sarai
# %pubring foo.pub
# %secring foo.sec
# # Do a commit here, so that we can later print "done" :-)
# %commit
# %echo done
# EOF
rm -rf ~/.gnupg
gpg --batch --generate-key <<EOF
%echo Generating a basic OpenPGP key
Key-Type: RSA
Key-Length: 2048
Subkey-Type: RSA
Subkey-Length: 2048
Name-Real: John Doe
Name-Comment: the container gpg revocation
Name-Email: dellius.alexander@gmail.com
Expire-Date: 0
%no-protection
# Do a commit here, so that we can later print "done" :-)
%commit
%echo done
EOF

# #initialize pass
# pass init "${DOCKER_USER_EMAIL}"

