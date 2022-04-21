cd persistence-service
./gradlew bootJar
cd ..
cd user-interaction-service
./gradlew bootJar
cd ..
docker-compose up --build -d