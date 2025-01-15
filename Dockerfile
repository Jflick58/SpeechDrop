# Use an OpenJDK 17 base (or 1.8 if you prefer).
FROM eclipse-temurin:17-jdk

# Create and use /app as working directory.
WORKDIR /app

# Copy the built jar into the container.
COPY target/SpeechDrop-1.0-SNAPSHOT.jar speechdrop.jar
COPY config.example.json config.json

# Expose port 8080 to match the fly.toml internal_port setting.
EXPOSE 8080

# The entrypoint runs our jar, using the config file.
# You can override "config.json" with your own config, or set environment variables instead.
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "speechdrop.jar", "-conf", "config.json"]