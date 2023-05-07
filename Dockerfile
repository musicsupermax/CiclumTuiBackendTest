 # Use an official Java runtime as a parent image
FROM adoptopenjdk/openjdk11:alpine-jre
# Set the working directory to /app
WORKDIR /app
# Copy the project jar into the container at /app
COPY target/vcs-repo-0.0.1-SNAPSHOT.jar /app
# Set environment variables
ENV PORT 8080
# Expose the port the application will run on
EXPOSE $PORT
# Run the application
CMD ["java", "-jar", "vcs-repo-0.0.1-SNAPSHOT.jar"]