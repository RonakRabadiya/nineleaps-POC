FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/RonakRabadiya/nineleaps-POC.git
RUN ls > tempfile
RUN cat tempfile

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY --from=clone /app/nineleaps-POC/product-ms /app
RUN mvn dependency:go-offline -B 
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/product-ms*.jar /tmp/product-ms.jar
COPY docker-resources /tmp
ENV JAVA_OPTS=""
RUN apk update && apk add netcat-openbsd
EXPOSE 8082
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]
