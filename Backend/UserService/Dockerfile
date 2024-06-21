# Use an official Eclipse Temurin runtime as a parent image
FROM eclipse-temurin:21.0.1_12-jre-alpine

# Set the working directory inside the container
WORKDIR /application

# Install curl
RUN apk --no-cache add curl

# Copy the Spring Boot fat jar into the container
COPY target/*.jar app.jar

# Copy the Firebase service account JSON file into the container
COPY src/main/resources/social-media-426720-firebase-adminsdk-6mlkv-fab9e28ace.json /application

# Expose port 8081 for the application
EXPOSE 8081

# Set the entry point to start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
