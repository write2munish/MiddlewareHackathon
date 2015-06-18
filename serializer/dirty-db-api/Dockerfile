FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

ENV port=8080
ENV jdbc_url=jdbc:mysql://dirtydb.cdxtfiwyq2na.eu-west-1.rds.amazonaws.com:3306/changelog
ENV jdbc_user=dirty_user
ENV jdbc_password=dirty_password

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "package"]

EXPOSE 8080
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/dirty-db-api-1.0.jar"]
