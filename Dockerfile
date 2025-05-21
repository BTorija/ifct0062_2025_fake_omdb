# Usa una imagen base
# FROM postgres:17
# Variables de entorno para la configuración inicial
# ENV POSTGRES_USER=postgres_bea
# ENV POSTGRES_PASSWORD=password_bea

# ENV POSTGRES_DB=omdb_pelis
# Usar una imagen base de JDK de Amazon Corretto
FROM amazoncorretto:17-alpine-jdk
# Establecer el directorio de trabajo
WORKDIR /app
# Copiar el archivo JAR generado en el contenedor
COPY target/*.jar app.jar
# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8081
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
