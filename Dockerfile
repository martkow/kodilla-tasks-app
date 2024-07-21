# Use the openjdk 17 image
FROM openjdk:17-jdk-alpine

# Set the ARG variable for the database URL
ARG MYSQL_DB_URL
ENV MYSQL_DB_URL ${MYSQL_DB_URL?notset}

# Install JDK 17
RUN apk add --no-cache openjdk17

# Set the working directory
WORKDIR /usr/src/app

# Copy files to the container
COPY . .

# Add user and group, and set permissions
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser
RUN chown -R 1001:1001 /usr/src/app
USER 1001

# Expose port 8080
EXPOSE 8080

# Copy application properties and set appropriate values
RUN cat /usr/src/app/src/main/resources/application-mogenius.properties > /usr/src/app/src/main/resources/application.properties

# Set permissions for the gradlew file
RUN chmod +x gradlew

# Check the value of the MYSQL_DB_URL variable
RUN echo ${MYSQL_DB_URL}

# Check the Java version
RUN java -version

# Build the application
RUN ./gradlew build -PMYSQL_DB_URL="jdbc:${MYSQL_DB_URL}"

# Set the entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/build/libs/tasks-0.0.1-SNAPSHOT.jar"]
