FROM openjdk:8-jdk-alpine
COPY target/supplier-ms*.jar /tmp/supplier-ms.jar
COPY docker-resources /tmp
ENV JAVA_OPTS=""
RUN apk update && apk add netcat-openbsd
EXPOSE 8082
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]

