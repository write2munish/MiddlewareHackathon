FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven
RUN mkdir -p /root/.aws
ADD target/credentials /root/.aws/credentials

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml pom.xml

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "package"]

CMD [ "/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/xucdm-serializer-1.0.jar", "http://ec2-54-154-230-236.eu-west-1.compute.amazonaws.com", "http://canonicalapi-env.elasticbeanstalk.com", "0", "AA", "en" ]
