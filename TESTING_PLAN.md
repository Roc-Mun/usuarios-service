# TESTING_PLAN.md — Microservicio Usuarios

## Pruebas Unitarias y Cobertura de Reglas de Negocio

Este documento resume las reglas de negocio críticas del microservicio de Usuarios y el estado actual de cobertura mediante pruebas unitarias.

El microservicio fue probado en cuatro capas principales: modelo, servicio, repositorio y controlador.

---

## Reglas de Negocio Críticas

1. No se debe permitir registrar dos usuarios con el mismo correo electrónico.
2. Todo usuario nuevo debe quedar en estado `activo`.
3. Si no se informa un rol al crear un usuario, el sistema debe asignar el rol `cliente` por defecto.
4. Solo se permiten roles válidos: `cliente`, `organizador` o `stand`.
5. Un usuario puede ser desactivado cambiando su estado a `inactivo`.

---

## Cobertura Actual

| Regla / Capa | Estado | Casos Cubiertos |
|---|---|---|
| Modelo Usuario | ✅ Cubierta | Constructor vacío, constructor completo, getters/setters, equals y hashCode |
| Crear usuario correctamente | ✅ Cubierta | Creación exitosa con datos válidos |
| Correo duplicado | ✅ Cubierta | Lanza excepción si el correo ya existe |
| Rol por defecto | ✅ Cubierta | Si el rol viene nulo, se asigna `cliente` |
| Rol inválido | ✅ Cubierta | Lanza excepción si el rol no pertenece a los roles permitidos |
| Desactivar usuario | ✅ Cubierta | Cambia el estado del usuario a `inactivo` |
| Repositorio Usuario | ✅ Cubierta | `save`, `findById`, `findAll`, `findByCorreo` |
| Controlador Usuario | ✅ Cubierta | Respuestas HTTP 200, 201 y 404 mediante MockMvc |

---

## Clases de Test Implementadas

| Capa | Clase de Test | Herramientas |
|---|---|---|
| Modelo | `UsuarioTest` | JUnit 5 |
| Servicio | `UsuarioServiceTest` | JUnit 5, Mockito, `@Mock`, `@InjectMocks` |
| Repositorio | `UsuarioRepositoryTest` | `@DataJpaTest`, H2 en memoria |
| Controlador | `UsuarioControllerTest` | MockMvc, `standaloneSetup` |

---

## Ejecución de Pruebas

Comando utilizado:

```bash
mvn test