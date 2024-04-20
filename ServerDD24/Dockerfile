# Utilizza un'immagine base di Java. Puoi scegliere una specifica versione di Java qui.
FROM openjdk:17

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il file JAR dal tuo host al filesystem del container
COPY build/libs/ServerDD24-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/java/com/example/serverdd24/serviceAccountKey.json src/main/java/com/example/serverdd24/serviceAccountKey.json

# Espone la porta su cui la tua applicazione sarà accessibile
EXPOSE 8080

# Comando per eseguire l'applicazione
CMD ["java", "-jar", "app.jar"]
