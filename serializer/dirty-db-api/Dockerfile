#FROM williamyeh/java8
FROM ubuntu
MAINTAINER shyam

# add webupd8 repository
RUN \
    echo "===> add webupd8 repository..."  && \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886  && \
    apt-get update  && \
    \
    \
    echo "===> install Java"  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default  && \

RUN echo "install maven and git" && \
    apt-get install -y maven git
RUN echo "clone repository" && \
    git clone https://github.com/write2munish/MiddlewareHackathon

RUN echo "build" && \
    cd MiddlewareHackathon/serializer/dirty-db-api && \
    mvn package

EXPOSE 4567
CMD ["java", "-jar", "MiddlewareHackathon/serializer/dirty-db-api/target/dirty-db-api-1.0.jar"]
