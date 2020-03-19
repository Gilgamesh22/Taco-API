FROM centos:7 as micronaut

SHELL ["/bin/bash", "-c", "-l"]

RUN yum update -y

# install JAVA and setup JAVA
RUN yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel; \
    export JAVA_HOME=/Library/Java/Home; \
    export PATH="$PATH:$JAVA_HOME/bin"

# install SDKMAN
RUN yum install -y curl which unzip zip; \
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"

# install Micronaut
RUN sdk update; \
    sdk install micronaut

# following is the sulotion to the "grails commands return “No profile found for name web”" problem
# https://github.com/mozart-analytics/grails-docker/issues/1#issuecomment-292817386
# https://stackoverflow.com/a/43312062/3738647
VOLUME ["/gradle"]
ENV GRADLE_USER_HOME /gradle