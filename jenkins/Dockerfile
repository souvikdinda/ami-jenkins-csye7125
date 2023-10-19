FROM jenkins/jenkins:latest
ARG github_credentials
ENV GITHUB_CREDENTIALS=$github_credentials
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml
COPY ~/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
COPY ~/casc.yaml /var/jenkins_home/casc.yaml