FROM openjdk:8
EXPOSE 8098
WORKDIR /eventsProject
COPY target/eventsProject-1.0.0-SNAPSHOT.jar /eventsProject
CMD ["java", "-jar", "eventsProject-1.0.0-SNAPSHOT.jar"]