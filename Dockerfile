# Etapa 1 - Build com Maven
FROM maven:3.9.10-eclipse-temurin-17 as builder

WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml .
COPY src ./src

# Gera o .jar
RUN mvn clean package -DskipTests

# Etapa 2 - Imagem enxuta para rodar o app
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o jar gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a API
ENTRYPOINT ["java", "-jar", "app.jar"]
