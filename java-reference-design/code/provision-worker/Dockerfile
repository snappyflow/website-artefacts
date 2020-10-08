FROM openjdk:8-jre-alpine3.9
ADD target/provision-worker-0.0.1-SNAPSHOT.jar worker.jar
EXPOSE 8083
CMD java -jar worker.jar
