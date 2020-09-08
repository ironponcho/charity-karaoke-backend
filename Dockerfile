FROM openjdk:14-jdk-alpine

RUN addgroup -S ckb && adduser -S ck -G ckb

USER ckb:ckb

ARG JAR_FILE=target/artifacts/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]