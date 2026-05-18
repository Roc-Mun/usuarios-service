# Usuario-Service

## Descripción
Microservicio encargado de gestionar usuarios, autenticación básica, roles y estado de acceso dentro de la plataforma ReadyStand.

## Funcionalidades

- Registrar usuarios
- Listar usuarios
- Buscar usuario por ID
- Actualizar usuario
- Desactivar usuario
- Gestión de roles
- Validaciones de datos
- Manejo de excepciones
- Comunicación con otros microservicios mediante API REST

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- MySQL
- Maven
- Docker
- Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Endpoints principales

-Obtener usuarios
GET /api/v2/usuarios

-Obtener usuario por ID
GET /api/v2/usuarios/{id}

-Crear usuario
POST /api/v2/usuarios

-Actualizar usuario
PUT /api/v2/usuarios/{id}

-Desactivar usuario
PATCH /api/v2/usuarios/{id}/estado

## Validaciones

-Validación de campos obligatorios

-Validación de correo único

-Validación de roles

-Manejo global de errores con Bean Validation
