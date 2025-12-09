# Módulos User - Arquitectura Modular

## Estructura

Los módulos de usuario han sido modularizados siguiendo los principios de Clean Architecture y Domain-Driven Design (DDD). La estructura está organizada jerárquicamente bajo la carpeta `user`:

```
user/
├── domain/               # Capa de Dominio (10 archivos)
├── infra/                # Capa de Infraestructura (16 archivos)
├── service/              # Capa de Aplicación/Servicios (3 archivos)
└── api/                  # Capa de Presentación/API (16 archivos)
```

**Gradle Modules**: `user.domain`, `user.infra`, `user.service`, `user.api`

**Configuración en settings.gradle.kts**:
```kotlin
include("user:domain")
include("user:infra")
include("user:service")
include("user:api")
```

## Módulos

### 1. user.domain
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

### 2. user.infra
**Propósito**: Implementa la infraestructura necesaria para persistencia, seguridad, mensajería y rate limiting.

**Paquetes**:
- `com.rosafiesta.api.infra.database` - Entidades JPA, repositorios y mappers
- `com.rosafiesta.api.infra.security` - Componentes de seguridad (password encoding, token generation)
- `com.rosafiesta.api.infra.rate_limiting` - Rate limiters por IP y email
- `com.rosafiesta.api.infra.configs` - Configuraciones de infraestructura

**Dependencias**:
- `user.domain` (api)
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

### 3. user.service
**Propósito**: Contiene la lógica de negocio y casos de uso de la aplicación.

**Paquetes**:
- `com.rosafiesta.api.service` - Servicios de aplicación (AuthService, EmailVerificationService, PasswordResetService)

**Dependencias**:
- `user.domain` (api)
- `user.infra` (api)
- `common` (implementation)
- Spring Boot Security
- JWT

**Características**:
- Orquesta las operaciones de negocio
- Usa repositorios de infra
- Publica eventos de dominio
- Maneja transacciones

### 4. user.api
**Propósito**: Expone la API REST, maneja validación de entrada y conversión de DTOs.

**Paquetes**:
- `com.rosafiesta.api.api.controller` - Controladores REST
- `com.rosafiesta.api.api.dtos` - DTOs para request/response
- `com.rosafiesta.api.api.mappers` - Conversión entre DTOs y modelos de dominio
- `com.rosafiesta.api.api.config` - Configuración de Spring (filtros JWT, rate limiting, CORS)
- `com.rosafiesta.api.api.exception_handling` - Manejo global de excepciones
- `com.rosafiesta.api.api.utils` - Utilidades de la API

**Dependencias**:
- `user.domain` (api)
- `user.service` (api)
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

## Flujo de Dependencias

```
user.api (presentación)
 ├─→ user.service (aplicación)
 │    ├─→ user.infra (infraestructura)
 │    │    └─→ user.domain (dominio)
 │    └─→ user.domain
 └─→ user.domain

Cada módulo también depende de:
 └─→ common (módulo compartido)
```

## Ventajas de esta Arquitectura

1. **Separación de Responsabilidades**: Cada módulo tiene un propósito claro y bien definido.

2. **Organización Jerárquica**: Similar a la estructura de Android/KMP multi-módulo donde `user` es una carpeta organizadora.

3. **Nomenclatura Clara**: `user.domain` es más intuitivo que `user-domain`.

4. **Mantenibilidad**: Los cambios en una capa no afectan a las demás si se respetan las interfaces.

5. **Testabilidad**: Es fácil hacer tests unitarios de cada capa de forma aislada.

6. **Escalabilidad**: Se pueden agregar nuevas funcionalidades sin afectar el código existente.

7. **Compilación Incremental**: Gradle puede compilar solo los módulos que cambiaron.

8. **Respeto de Spring Boot**: La estructura de paquetes `com.rosafiesta.api` se mantiene en todos los módulos.

## Notas Importantes

- **Paquetes**: Todos los módulos mantienen la ruta base `com.rosafiesta.api` para garantizar que Spring Boot funcione correctamente con el component scanning.

- **Visibilidad**: El módulo `user.infra` expone Spring Data JPA como `api` para que los servicios puedan usar anotaciones como `@Transactional` y métodos de repositorio.

- **Estructura Jerárquica**: La carpeta `user` NO es un módulo Gradle, solo es una carpeta organizadora. Los módulos reales son `user:domain`, `user:infra`, `user:service` y `user:api`.

## Uso

Para usar los módulos user en otro módulo (como `app`), agrega las dependencias necesarias:

```kotlin
dependencies {
    // Solo los módulos que necesites
    implementation(projects.user.domain)  // Modelos y excepciones
    implementation(projects.user.infra)   // Repositorios y seguridad
    implementation(projects.user.service) // Lógica de negocio
    implementation(projects.user.api)     // Controllers y configuración
}
```

**Ventajas**:
- ✅ Organización jerárquica clara (`user.domain` en vez de `user-domain`)
- ✅ Similar a Android y KMP multi-módulo
- ✅ `user` es una carpeta organizadora, no un módulo
- ✅ Solo dependes de lo que realmente necesitas
- ✅ Type-safe project accessors: `projects.user.domain`

## Aplicar a Otros Módulos

Esta misma estructura se puede aplicar a `chat` y `notification`:

```
chat/
├── domain/
├── infra/
├── service/
└── api/

notification/
├── domain/
├── infra/
├── service/
└── api/
```