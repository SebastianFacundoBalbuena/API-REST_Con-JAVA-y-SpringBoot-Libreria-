#DockerFile

FROM eclipse-temurin:17-jre

# 📌 "Crea una carpeta llamada /app y entra en ella"
WORKDIR /app  

# 📌 "Toma el archivo JAR de tu aplicación y lo copia al contenedor, renombrándolo a app.jar"
COPY target/library_api-0.0.1-SNAPSHOT.jar app.jar


# 📌 "Dice que la app usará el puerto 8080"
EXPOSE 8080


# 📌 "Comando para ejecutar tu app cuando el contenedor inicie"
CMD [ "java", "-jar", "app.jar" ]

