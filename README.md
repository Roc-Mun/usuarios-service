# Usuario-Service

## Descripción

Microservicio encargado de gestionar usuarios, autenticación básica, roles y estado de acceso dentro de la plataforma ReadyStand.

## Funcionalidades

* Registrar usuarios
* Listar usuarios
* Buscar usuario por ID
* Actualizar usuario
* Desactivar usuarios
* Gestión de roles
* Validaciones de datos
* Manejo de excepciones
* Comunicación con otros microservicios mediante API REST

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Validation
* Springdoc OpenAPI (Swagger)
* MySQL
* H2 Database (Testing)
* JUnit 5
* Mockito
* Maven
* Docker
* Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Ejecución de pruebas

```bash
mvn test
```

## Swagger

Disponible en:

```text
http://localhost:8081/doc/swagger-ui.html
```

## Endpoints principales

### Obtener usuarios

GET /api/v3/usuarios

### Obtener usuario por ID

GET /api/v3/usuarios/{id}

### Crear usuario

POST /api/v3/usuarios

### Actualizar usuario

PUT /api/v3/usuarios/{id}

### Asignar rol

PUT /api/v3/usuarios/{id}/rol

### Desactivar usuario

PUT /api/v3/usuarios/{id}/desactivar

## Testing

El proyecto incluye pruebas unitarias para las capas:

* Modelo
* Servicio
* Repositorio
* Controlador

Todas las pruebas deben finalizar con BUILD SUCCESS.

## Validaciones

* Validación de campos obligatorios
* Validación de correo único
* Validación de roles
* Manejo global de errores con Bean Validation
