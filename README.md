# RosaFiesta Backend

Backend API for the RosaFiesta application built with Spring Boot and Kotlin.

## 🏗️ Architecture

The project is organized in a modular structure:

- **app**: Main application module with Spring Boot configuration
- **common**: Shared utilities and common code
- **user**: User management module
- **chat**: Chat functionality module
- **notification**: Notification services module

## 🚀 Quick Start

### Option A: IntelliJ IDEA (Recommended for Debugging) ⭐

1. **Start local services** (choose one):
   ```bash
   docker-compose up -d                              # Docker Compose tradicional
   docker-compose -f docker-compose.orb.yml up -d   # Orb Stack (mejor para macOS)
   ```

2. **Open project in IntelliJ IDEA**

3. **Select run configuration:**
   - **RosaFiestaApi [LOCAL]** 🏠 - Docker Compose tradicional
   - **RosaFiestaApi [ORB]** 🌟 - Orb Stack (mejor rendimiento en macOS)
   - **RosaFiestaApi [DEV]** 🔧 - Servicios remotos de desarrollo
   - **RosaFiestaApi [PROD]** 🚀 - Servicios de producción

4. **Click Debug 🐛 (Shift + F9)** o **Run ▶️ (Shift + F10)**

5. **Set breakpoints and debug!**

📚 **Documentation:**
- [Orb Stack Setup](docs/ORB_STACK_SETUP.md) - Complete local development setup
- [Quick Start Guide](docs/QUICK_START_ORB.md) - Get started quickly

### Option B: Command Line with Makefile ⚡ (Recommended)

1. **Start local infrastructure:**
   ```bash
   make orb-up
   ```

2. **Run the application:**
   ```bash
   make run-local
   ```

3. **Stop local services:**
   ```bash
   make orb-down
   ```

**See all commands:**
```bash
make help
```

For more details, see [docs/ORB_STACK_SETUP.md](docs/ORB_STACK_SETUP.md)

### Option C: Command Line (Scripts)

Using scripts directly:

```bash
# Start
./scripts/start-local.sh

# Run
./gradlew :app:bootRun --args='--spring.profiles.active=local'

# Stop
./scripts/stop-local.sh
```

### Production/Remote Services

Run with default profile (uses Supabase, Redis Cloud, CloudAMQP):
```bash
./gradlew :app:bootRun
```

Make sure you have the required environment variables set:
- `POSTGRES_PASSWORD`
- `REDIS_PASSWORD`
- `RABBITMQ_PASSWORD`
- `JWT_SECRET_BASE64`
- `MAIL_FROM_EMAIL`
- `MAIL_PASSWORD`
- `SUPABASE_SERVICE_KEY`

## 🗄️ Database Migrations (Liquibase)

The project uses **Liquibase** for automated database migrations with **Makefile automation**:

### Quick Start

**Manual Creation:**
```bash
# Create new migration (auto-registers in master changelog!)
make liquibase-create NAME=add-user-phone
# or short alias:
make lb-create NAME=add-user-phone
```

**Automatic Generation from JPA Entities:** 🤖
```bash
# Generate migration by comparing DB vs entities
make liquibase-diff
# or: make lb-diff

# Review generated file and improve it
# Then register it
make lb-register FILE=006-hibernate-diff-20251203

# Validate and test
make lb-validate && make lb-test
```

**Common Commands:**
```bash
make lb-validate       # Validate YAML syntax
make lb-test           # Test migration locally
make lb-status         # Check migration status in database
```

### How It Works

1. **Create** migration with one command → **auto-registers** in master changelog
2. **Edit** the generated YAML file with your changes
3. **Validate** syntax before running
4. **Test** locally with full stack startup
5. **Deploy** - migrations run automatically on app startup

### Key Features

- ✅ **Zero manual editing** of master changelog
- ✅ **Automatic validation** of YAML syntax
- ✅ **One-command testing** with full stack
- ✅ **Database visibility** with status checks
- ✅ **Auto-generation** from JPA entities - semi-automatic migrations 🤖
- ✅ **Safe rollbacks** - undo migrations when needed
- ✅ **Multiple rollback options** - one, many, or to specific point

### Rollback Support

```bash
# Undo last migration
make liquibase-rollback-one
# or: make lb-rollback

# Undo multiple migrations
make lb-rollback-count COUNT=3

# Undo to specific migration
make lb-rollback-to ID=003-create-chat

# View rollback SQL before executing
make lb-rollback-sql
```

📚 **Learn more:** [Liquibase Documentation](docs/LIQUIBASE.md) | [Auto-Generation Guide](docs/LIQUIBASE_AUTO_GENERATION.md) | [Manual vs Semi-Auto](docs/COEXISTENCE_MANUAL_VS_SEMIAUTO.md) | [Rollback Guide](config/LIQUIBASE_ROLLBACK_GUIDE.md) | [Makefile Examples](config/MAKEFILE_EXAMPLES.md) | [Quick Reference](config/LIQUIBASE_QUICK_REF.md)

## 🔧 Technology Stack

- **Language**: Kotlin
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Database Migrations**: Liquibase (automated via Makefile)
- **Cache**: Redis
- **Message Queue**: RabbitMQ
- **Security**: Spring Security + JWT
- **Email**: Spring Mail (Gmail SMTP)
- **Cloud Storage**: Supabase
- **Push Notifications**: Firebase Cloud Messaging

## 📦 Local Development Stack

The `docker-compose.yml` provides:
- **PostgreSQL 16**: Local database
- **Redis 7**: Local cache
- **RabbitMQ 3**: Local message broker with management UI

## 🛠️ Build & Test

### Using Make (Recommended) ⭐

The project includes a comprehensive Makefile for easy development:

```bash
# Show all available commands
make help

# Docker services
make orb-up              # Start Orb Stack services
make orb-down            # Stop services
make orb-logs            # View logs

# Run application
make run-local           # Run with local profile
make run-orb             # Run with Orb profile

# Build & test
make build               # Build project
make test                # Run tests

# Database migrations (Liquibase)
make liquibase-create NAME=add-user-field   # Create new migration
make liquibase-list                         # List all migrations
make liquibase-validate                     # Validate migration files
make liquibase-test                         # Test migrations locally
make liquibase-status                       # Show migration status in DB

# Auto-generation from JPA entities 🤖
make liquibase-diff                         # Generate diff: DB vs entities
make liquibase-generate-schema              # Generate complete schema
make liquibase-register-manual FILE=xxx     # Register generated file

# Rollback (undo migrations)
make liquibase-rollback-one                 # Undo last migration
make liquibase-rollback-count COUNT=2       # Undo N migrations
make liquibase-rollback-to ID=003-create-chat  # Undo to specific migration
make liquibase-rollback-sql                 # Show rollback SQL

# Short aliases
make lb-create NAME=my-migration   # Short alias for liquibase-create
make lb-list                       # Short alias for liquibase-list
make lb-validate                   # Short alias for liquibase-validate
make lb-rollback                   # Short alias for liquibase-rollback-one
make lb-rollback-count COUNT=2     # Short alias for liquibase-rollback-count
```

### Using Gradle Directly

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Create a JAR
./gradlew :app:bootJar
```

The JAR will be created in `app/build/libs/`

## 📝 Configuration Profiles

- **default**: Production configuration with remote services
- **local**: Development configuration with Docker services

Switch profiles using:
```bash
--spring.profiles.active=local
```

Or set environment variable:
```bash
export SPRING_PROFILES_ACTIVE=local
```

## 🔐 Environment Variables

Copy `.env.example` to `.env` and fill in your credentials:
```bash
cp .env.example .env
```

**Note**: Never commit the `.env` file to version control.

## 📚 API Documentation

The API will be available at:
- Local: http://localhost:8080
- Production: Your deployed URL

## 🐳 Docker Services

### Access RabbitMQ Management UI:
http://localhost:15672
- Username: `rosafiesta_user`
- Password: `rosafiesta_password`

### Connect to PostgreSQL:
```bash
docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta
```

### Connect to Redis:
```bash
docker exec -it rosafiesta-redis redis-cli -a rosafiesta_redis_password
```

## 📖 Additional Documentation

- [Orb Stack Setup](docs/ORB_STACK_SETUP.md) - Local development environment
- [Quick Start Guide](docs/QUICK_START_ORB.md) - Getting started
- [Liquibase Migrations](docs/LIQUIBASE.md) - Database schema management
- [Security Guide](docs/SECURITY.md) - Security best practices

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Test locally with `./scripts/start-local.sh`
4. Submit a pull request

## 📄 License

[Your License Here]