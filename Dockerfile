
# Use a base image with Java
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
RUN mkdir -p myApp

# Copy the packaged application jar file into the container
COPY ./build/libs/*.jar /myApp/app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/myApp/app.jar"]