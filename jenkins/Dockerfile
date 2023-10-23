FROM jenkins/jenkins:latest

ARG github_creds
ARG github_username
ARG quay_username
ARG quay_creds

ENV GITHUB_USERNAME=$github_username
ENV GITHUB_CREDS=$github_creds
ENV QUAY_USERNAME=$quay_username
ENV QUAY_CREDS=$quay_creds

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml
COPY casc.yaml /var/jenkins_home/casc.yaml
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
# COPY jenkins-cli.jar /var/jenkins_home/war/WEB-INF/jenkins-cli.jar
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
# COPY seed_job.groovy /var/jenkins_home/seed_job.groovy

# USER root
# RUN chown -R jenkins:jenkins /var/jenkins_home