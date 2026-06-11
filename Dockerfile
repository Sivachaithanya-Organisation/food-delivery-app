# ─────────────────────────────────────────────
# Stage 1: BUILD  — Maven builds the WAR
# ─────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-11 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml first (Docker cache optimization)
# If pom.xml didn't change, Maven dependencies are NOT re-downloaded
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ─────────────────────────────────────────────
# Stage 2: RUN  — Tomcat serves the WAR
# ─────────────────────────────────────────────
FROM tomcat:9.0.83-jdk11

LABEL maintainer="devops-team"
LABEL app="food-delivery"

# Remove default Tomcat apps (cleaner)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from build stage into Tomcat webapps
COPY --from=build /app/target/food-delivery.war /usr/local/tomcat/webapps/food-delivery.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
