FROM maven:3.8.6-jdk-8
COPY src /src
COPY pom.xml /
RUN mvn clean test