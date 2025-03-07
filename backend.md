# Documentación del Backend

## Configuración del Entorno y Ejecución
Para ejecutar el backend del proyecto, sigue estos pasos:

1. Clonar el repositorio:  
   ```bash
   git clone <https://github.com/ojllain12/HUEVICOLA_PDN>
   cd backend
   ```
2. Instalar dependencias:  
   ```bash
   mvn install
   ```
3. Configurar variables de entorno: 
   Copia el archivo de configuración predeterminado y personalízalo:
   ```bash
   cp .env.example .env
   ```
   Luego, edita el archivo `.env` con la configuración de la base de datos y otras variables necesarias.

4. Compilar y ejecutar el proyecto:  
   ```bash
   mvn spring-boot:run
   ```
   Esto ejecutará la aplicación en `http://localhost:8080`.

## Lenguaje de Programación
El backend está desarrollado en Java.

## Framework
Se utiliza **Spring Boot**, un framework basado en Java que facilita la creación de aplicaciones robustas y escalables.


