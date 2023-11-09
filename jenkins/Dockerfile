FROM jenkins/jenkins:latest
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml

USER root
COPY script.sh /tmp/script.sh
RUN chmod +x /tmp/script.sh
RUN /tmp/script.sh
USER jenkins

COPY casc.yaml /var/jenkins_home/casc.yaml
COPY multibranch-pipeline.groovy /var/jenkins_home/multibranch-pipeline.groovy
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt