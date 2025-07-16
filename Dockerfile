# Usa imagem do Java 17
FROM openjdk:17-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Copia o jar gerado
COPY target/user-api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
