FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml pom.xml

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "package"]

CMD [ "/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/CanonicalAPIMock-0.0.1-SNAPSHOT.jar" ]