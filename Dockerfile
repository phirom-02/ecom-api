FROM openjdk:22-jdk-oracle as build

WORKDIR /app

COPY target/artifacts/ecom_api_jar/ecom-api.jar /app/ecom-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ecom-api.jar"]