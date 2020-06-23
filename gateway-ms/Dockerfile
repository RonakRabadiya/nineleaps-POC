FROM openjdk:8-jdk-alpine
COPY target/gateway-ms*.jar /tmp/gateway-ms.jar
COPY docker-resources /tmp
ENV JAVA_OPTS=""
EXPOSE 8765
RUN apk update && apk add netcat-openbsd
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]