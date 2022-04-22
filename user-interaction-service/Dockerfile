FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} interaction.jar
ENTRYPOINT java -Dspring.profiles.active=dev -jar interaction.jar

EXPOSE 8080