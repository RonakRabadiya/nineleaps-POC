FROM openjdk:8-jdk-alpine
COPY target/product-ms*.jar /tmp/product-ms.jar
COPY docker-resources /tmp
ENV JAVA_OPTS=""
RUN apk update && apk add netcat-openbsd
EXPOSE 8082
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]

