#!/bin/bash
sleep 30
export DEBIAN_FRONTEND=noninteractive
export CHECKPOINT_DISABLE=1

# Update and upgrage packages
echo "================================="
echo "Updating yum dependencies"
echo "================================="
sudo apt update -y
sudo apt install zip unzip -y

# Installing Java 11
echo "================================="
echo "Installing Java 11"
echo "================================="
sudo apt install openjdk-11-jdk -y

# Installing NodeJS
echo "================================="
echo "Installing NodeJS"
echo "================================="
curl -sL https://deb.nodesource.com/setup_16.x -o /tmp/nodesource_setup.sh
sudo bash /tmp/nodesource_setup.sh
sudo apt install nodejs -y
sudo apt install npm -y
sudo node -v
sudo npm -v
sudo npm install -g semantic-release@17.4.4
sudo npm install -g @semantic-release/git@9.0.0
sudo npm install -g @semantic-release/exec@5.0.0
sudo npm install -g conventional-changelog-conventionalcommits
sudo npm install -g npm-cli-login
sudo apt install gh -y

# Install Jenkins
echo "================================="
echo "Installing Jenkins"
echo "================================="
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update -y
sudo apt install fontconfig -y
sudo apt install jenkins -y

# Install Jenkins Plugins
echo "================================="
echo "Installing Jenkins Plugins"
echo "================================="
wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/2.12.13/jenkins-plugin-manager-2.12.13.jar
sudo chmod +x jenkins-plugin-manager-2.12.13.jar
sudo java -jar ~/jenkins-plugin-manager-2.12.13.jar --war /usr/share/java/jenkins.war --plugin-file /tmp/plugins.txt --plugin-download-directory /var/lib/jenkins/plugins/
# sudo chmod +x /var/lib/jenkins/plugins/*.jpi

echo "================================="
echo "Placing Jenkins CASC Files"
echo "================================="
sudo cp /tmp/casc.yaml /var/lib/jenkins/casc.yaml
sudo cp /tmp/multibranch-pipeline.groovy /var/lib/jenkins/multibranch-pipeline.groovy
sudo chmod +x /var/lib/jenkins/casc.yaml /var/lib/jenkins/multibranch-pipeline.groovy
(cd /var/lib/jenkins/ && sudo chown jenkins:jenkins casc.yaml multibranch-pipeline.groovy)

# for plugin in /var/lib/jenkins/plugins/*.jpi; do
#     plugin_name=$(basename -s .jpi "$plugin")
#     sudo mkdir -p "/var/lib/jenkins/plugins/$plugin_name"
#     echo "Extracting $plugin_name"
#     sudo unzip -q "$plugin" -d "/var/lib/jenkins/plugins/$plugin_name"
# done

sudo chown -R jenkins:jenkins /var/lib/jenkins/plugins/

echo "================================="
echo "Configuring Jenkins Service"
echo "================================="
# echo 'CASC_JENKINS_CONFIG="/var/lib/jenkins/casc.yaml"' | sudo tee -a /etc/environment
# echo 'JAVA_OPTS="-Djenkins.install.runSetupWizard=false"' | sudo tee -a /etc/environment
# sudo sed -i 's/\(JAVA_OPTS=-Djava\.awt\.headless=true\)/\1 -Djenkins.install.runSetupWizard=false/' /lib/systemd/system/jenkins.service
# sudo sed -i '/Environment="JAVA_OPTS=-Djava.awt.headless=true -Djenkins.install.runSetupWizard=false"/a Environment="CASC_JENKINS_CONFIG=/var/lib/jenkins/casc.yaml"' /lib/systemd/system/jenkins.service
# sudo systemctl daemon-reload

# Start Jenkins
echo "================================="
echo "Starting Jenkins Agent"
echo "================================="
sudo systemctl start jenkins
sudo systemctl enable jenkins

# Installing Docker
echo "================================="
echo "Installing Docker"
echo "================================="
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
sudo usermod -aG docker jenkins

# Installing Helm
echo "================================="
echo "Installing Helm"
echo "================================="
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh

# Install caddy
echo "================================="
echo "Installing Caddy"
echo "================================="
sudo apt install -y debian-keyring debian-archive-keyring apt-transport-https
curl -1sLf 'https://dl.cloudsmith.io/public/caddy/stable/gpg.key' | sudo gpg --dearmor -o /usr/share/keyrings/caddy-stable-archive-keyring.gpg
curl -1sLf 'https://dl.cloudsmith.io/public/caddy/stable/debian.deb.txt' | sudo tee /etc/apt/sources.list.d/caddy-stable.list
sudo apt update -y
sudo apt install caddy -y
sudo sed -i 's/# reverse_proxy localhost:8080/reverse_proxy localhost:8080/g' /etc/caddy/Caddyfile

echo "================================="
echo "Starting Caddy"
echo "================================="
sudo systemctl start caddy
sudo systemctl enable caddy

echo "================================="
echo "Restarting Jenkins"
echo "================================="
sudo systemctl enable jenkins
sudo systemctl restart jenkins