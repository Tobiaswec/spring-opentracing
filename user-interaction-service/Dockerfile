FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} interaction.jar
ENTRYPOINT java -jar interaction.jar

EXPOSE 8080