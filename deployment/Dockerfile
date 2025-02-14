# 1 Usa una imagen base ligera con JDK 17
FROM eclipse-temurin:17-jdk-alpine

# 2 Establece el directorio de trabajo
WORKDIR /app

# 3 Copia el archivo JAR generado desde Gradle
COPY applications/app-service/build/libs/apiFranchise.jar apiFranchise.jar

# 4 Expone el puerto en el que corre la API (ajústalo si es necesario)
EXPOSE 8080

USER root

# 5 Define el comando de inicio
ENTRYPOINT ["java", "-jar", "apiFranchise.jar"]