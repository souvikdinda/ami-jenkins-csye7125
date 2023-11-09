#!/bin/bash

# Update and upgrage packages
apt update
apt install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

# Installing NodeJS
curl -sL https://deb.nodesource.com/setup_16.x -o /tmp/nodesource_setup.sh
bash /tmp/nodesource_setup.sh
apt install nodejs -y
apt install npm -y
apt install gh -y
node -v
npm install -g semantic-release@17.4.4
npm install -g @semantic-release/git@9.0.0
npm install -g @semantic-release/exec@5.0.0
npm install -g conventional-changelog-conventionalcommits
npm install -g npm-cli-login

# Installing Docker
apt install docker.io -y
systemctl start docker
systemctl enable docker
usermod -aG docker $USER
usermod -aG docker jenkins

# Installing Helm
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
