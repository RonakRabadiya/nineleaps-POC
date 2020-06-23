FROM openjdk:8-jdk-alpine
COPY target/discovery-ms*.jar discovery-ms.jar
ENV JAVA_OPTS=""
EXPOSE 8761
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/.urandom -jar discovery-ms.jar"]