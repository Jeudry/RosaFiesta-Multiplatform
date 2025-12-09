# Módulo User - Arquitectura Modular

## Estructura

El módulo `user` ha sido modularizado siguiendo los principios de Clean Architecture y Domain-Driven Design (DDD). La estructura está dividida en 4 submódulos:

```
user/                           # Módulo agregador (expone todos los submódulos)
├── user-domain/               # Capa de Dominio (10 archivos)
├── user-infra/                # Capa de Infraestructura (16 archivos)
├── user-service/              # Capa de Aplicación/Servicios (3 archivos)
└── user-api/                  # Capa de Presentación/API (16 archivos)
```

## Módulos

### 1. user-domain
**Propósito**: Contiene los modelos de dominio, excepciones de negocio y tipos de valor.

**Paquetes**:
- `com.rosafiesta.api.domain.model` - Entidades de dominio
- `com.rosafiesta.api.domain.exception` - Excepciones de negocio

**Dependencias**:
- `common` (implementation)

**Características**:
- Sin dependencias de frameworks
- Representa el núcleo del negocio
- Inmutable y libre de efectos secundarios

### 2. user-infra
**Propósito**: Implementa la infraestructura necesaria para persistencia, seguridad, mensajería y rate limiting.

**Paquetes**:
- `com.rosafiesta.api.infra.database` - Entidades JPA, repositorios y mappers
- `com.rosafiesta.api.infra.security` - Componentes de seguridad (password encoding, token generation)
- `com.rosafiesta.api.infra.rate_limiting` - Rate limiters por IP y email
- `com.rosafiesta.api.infra.configs` - Configuraciones de infraestructura

**Dependencias**:
- `user-domain` (api)
- `common` (implementation)
- Spring Boot Data JPA (api)
- Spring Boot Data Redis
- Spring Boot AMQP
- Spring Boot Security
- Spring Boot Web
- PostgreSQL (runtime)

**Características**:
- Expone JPA como API para que los servicios puedan usarlo
- Implementa repositorios Spring Data
- Componentes de seguridad reutilizables

### 3. user-service
**Propósito**: Contiene la lógica de negocio y casos de uso de la aplicación.

**Paquetes**:
- `com.rosafiesta.api.service` - Servicios de aplicación (AuthService, EmailVerificationService, PasswordResetService)

**Dependencias**:
- `user-domain` (api)
- `user-infra` (api)
- `common` (implementation)
- Spring Boot Security
- JWT

**Características**:
- Orquesta las operaciones de negocio
- Usa repositorios de infra
- Publica eventos de dominio
- Maneja transacciones

### 4. user-api
**Propósito**: Expone la API REST, maneja validación de entrada y conversión de DTOs.

**Paquetes**:
- `com.rosafiesta.api.api.controller` - Controladores REST
- `com.rosafiesta.api.api.dtos` - DTOs para request/response
- `com.rosafiesta.api.api.mappers` - Conversión entre DTOs y modelos de dominio
- `com.rosafiesta.api.api.config` - Configuración de Spring (filtros JWT, rate limiting, CORS)
- `com.rosafiesta.api.api.exception_handling` - Manejo global de excepciones
- `com.rosafiesta.api.api.utils` - Utilidades de la API

**Dependencias**:
- `user-domain` (api)
- `user-service` (api)
- `common` (implementation)
- Spring Boot Web
- Spring Boot Security
- Spring Boot Validation
- Spring Boot Data Redis
- JWT

**Características**:
- Controllers anotados con `@RestController`
- Validación de entrada con Bean Validation
- Filtros de autenticación JWT
- Interceptors de rate limiting
- Exception handlers globales

### 5. user (Módulo Agregador)
**Propósito**: Módulo principal que reexporta todos los submódulos y actúa como punto de entrada único.

**Dependencias**:
- `user-domain` (api) - reexportado
- `user-infra` (api) - reexportado
- `user-service` (api) - reexportado
- `user-api` (api) - reexportado
- Todas las dependencias de runtime necesarias

**Características**:
- No contiene código fuente propio
- Actúa como facade para todos los módulos user
- Los consumidores solo necesitan depender de `user`

## Flujo de Dependencias

```
user (agregador)
 ├─→ user-api
 │    ├─→ user-service
 │    │    ├─→ user-infra
 │    │    │    └─→ user-domain
 │    │    └─→ user-domain
 │    └─→ user-domain
 └─→ common
```

## Ventajas de esta Arquitectura

1. **Separación de Responsabilidades**: Cada módulo tiene un propósito claro y bien definido.

2. **Mantenibilidad**: Los cambios en una capa no afectan a las demás si se respetan las interfaces.

3. **Testabilidad**: Es fácil hacer tests unitarios de cada capa de forma aislada.

4. **Escalabilidad**: Se pueden agregar nuevas funcionalidades sin afectar el código existente.

5. **Reutilización**: Los módulos de dominio e infraestructura pueden ser reutilizados en otros contextos.

6. **Compilación Incremental**: Gradle puede compilar solo los módulos que cambiaron.

7. **Respeto de Spring Boot**: La estructura de paquetes `com.rosafiesta.api` se mantiene en todos los módulos, asegurando que Spring Boot escanee correctamente los componentes.

## Notas Importantes

- **Paquetes**: Todos los módulos mantienen la ruta base `com.rosafiesta.api` para garantizar que Spring Boot funcione correctamente con el component scanning.

- **Visibilidad**: El módulo `user-infra` expone Spring Data JPA como `api` para que los servicios puedan usar anotaciones como `@Transactional` y métodos de repositorio.

- **Módulo Agregador**: El módulo `user` reexporta todos los submódulos con `api()` para que los consumidores (como el módulo `app`) puedan acceder a todos los componentes a través de una única dependencia.

## Uso

Para usar el módulo user en otro módulo, simplemente agrega la dependencia:

```kotlin
dependencies {
    implementation(projects.user)
}
```

Esto te dará acceso a todos los componentes de los cuatro submódulos (domain, infra, service, api).