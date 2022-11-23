FROM openjdk:17-oracle
EXPOSE 8090
ADD target/CloudStorage-0.0.1-SNAPSHOT.jar cloudStorage.jar
ENTRYPOINT ["java", "-jar", "cloudStorage.jar"]