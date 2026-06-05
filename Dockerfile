# ==========================================
# Etapa 1: Construcción (Build)
# ==========================================
# Usamos directamente la imagen oficial del JDK 25
FROM eclipse-temurin:25-jdk AS builder

# Le instalamos Maven rápidamente usando el gestor de paquetes de Linux
RUN apt-get update && apt-get install -y maven

# Creamos un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto saltando las pruebas
RUN mvn clean package -DskipTests

# ==========================================
# Etapa 2: Ejecución (Run)
# ==========================================
# Usamos una imagen ligera solo con el JRE 25 para ejecutar
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copiamos SOLAMENTE el .jar generado en la Etapa 1
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto estándar de Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]