# -----------------------------------------------------------
# 1) Build stage: Maven + Java 8, plus make+g++ but NO python
# -----------------------------------------------------------
    FROM maven:3.6.3-jdk-8 AS build

    RUN apt-get update && apt-get install -y \
        make \
        g++ \
     && rm -rf /var/lib/apt/lists/*
    
    WORKDIR /app
    
    # Copy pom.xml first to cache dependencies
    COPY pom.xml ./
    RUN mvn dependency:go-offline
    
    # Copy the rest (Java source, frontend, etc.)
    COPY src src
    COPY frontend frontend
    
    # Build the application (including frontend via frontend-maven-plugin)
    RUN mvn clean package -DskipTests
    
    # -----------------------------------------------------------
    # 2) Final stage: minimal Java 8 runtime
    # -----------------------------------------------------------
    FROM openjdk:8-jre
    
    WORKDIR /app
    
    # Copy compiled jar from build stage
    COPY --from=build /app/target/SpeechDrop-1.0-SNAPSHOT.jar speechdrop.jar
    
    EXPOSE 8080
    
    ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "speechdrop.jar"]