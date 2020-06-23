FROM openjdk:8-jdk-alpine
COPY target/config-server-ms*.jar /tmp/config-server-ms.jar
COPY docker-resources /tmp
ENV JAVA_OPTS=""
EXPOSE 8888
RUN apk update && apk add netcat-openbsd
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]