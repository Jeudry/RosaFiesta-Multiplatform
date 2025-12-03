# Liquibase Database Migrations

This project uses Liquibase for database schema versioning and migrations across all environments.

## Overview

Liquibase is configured to manage all database schema changes through version-controlled migration files. This ensures:
- Consistent database schema across all environments (local, dev, orb, prod)
- Automatic schema updates on application startup
- Rollback capabilities for failed migrations
- Complete audit trail of all database changes

## Configuration

### Profiles

Liquibase is enabled in all Spring profiles:

- **local**: Uses local PostgreSQL container
- **dev**: Uses Supabase development database  
- **orb**: Uses Orb Stack PostgreSQL
- **prod**: Uses production database

### Settings

All profiles use:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate  # Hibernate validates schema but doesn't create/update
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yaml
    default-schema: public
```

## Migration Files

Migration files are located in: `app/src/main/resources/db/changelog/migrations/`

### Current Migrations

1. **001-create-schemas.yaml**: Creates the three service schemas
   - `user_service`
   - `chat_service`
   - `notification_service`

2. **002-create-user-service-tables.yaml**: User service tables
   - `users`: User accounts
   - `email_verification_tokens`: Email verification tokens
   - `password_reset_tokens`: Password reset tokens
   - `refresh_tokens`: JWT refresh tokens

3. **003-create-chat-service-tables.yaml**: Chat service tables
   - `chat_participants`: Chat users (denormalized)
   - `chats`: Chat conversations
   - `chat_participants_cross_ref`: Many-to-many junction table
   - `chat_messages`: Chat messages

4. **004-create-notification-service-tables.yaml**: Notification service tables
   - `device_tokens`: Firebase device tokens

## Creating New Migrations

### Naming Convention

Use sequential numbering with descriptive names:
```
00X-description-of-change.yaml
```

### Example Migration

```yaml
databaseChangeLog:
  - changeSet:
      id: 005-add-user-profile-fields
      author: your-name
      comment: Add profile fields to users table
      changes:
        - addColumn:
            tableName: users
            schemaName: user_service
            columns:
              - column:
                  name: bio
                  type: TEXT
              - column:
                  name: avatar_url
                  type: VARCHAR(500)
      rollback:
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: bio
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: avatar_url
```

### Steps to Add a Migration

1. Create new YAML file in `migrations/` directory
2. Add `include` entry in `db.changelog-master.yaml`:
   ```yaml
   - include:
       file: db/changelog/migrations/00X-your-migration.yaml
   ```
3. Test locally first with `local` profile
4. Deploy to other environments

## Running Migrations

### Automatic (Default)

Migrations run automatically when the application starts:

```bash
# Local
./gradlew :app:bootRun --args='--spring.profiles.active=local'

# Orb
./gradlew :app:bootRun --args='--spring.profiles.active=orb'
```

### Manual Validation

To verify migrations without running the app:

```bash
./gradlew :app:liquibaseValidate
```

## Database Initialization

For fresh databases, Liquibase will:
1. Create the `databasechangelog` and `databasechangeloglock` tables
2. Execute all migrations in order
3. Record each changeset as executed

For existing databases, Liquibase will:
1. Check which changesets have been executed
2. Only run new changesets
3. Skip already-executed changesets

## Troubleshooting

### Migration Failed

If a migration fails:
1. Check the error message in application logs
2. Fix the migration file
3. Clear the failed changeset from `databasechangelog` table:
   ```sql
   DELETE FROM databasechangelog WHERE id = 'failed-changeset-id';
   ```
4. Restart the application

### Schema Validation Failed

If Hibernate validation fails:
1. Ensure all entity annotations match the database schema
2. Check that Liquibase migrations are up to date
3. Verify the correct profile is active

### Reset Local Database

To start fresh locally:

```bash
# Stop containers
docker-compose -f docker-compose.orb.yml down -v

# Start containers (recreates database)
docker-compose -f docker-compose.orb.yml up -d

# Run application (migrations will execute)
./gradlew :app:bootRun --args='--spring.profiles.active=local'
```

## Best Practices

1. **Never modify existing migrations**: Create new ones to make changes
2. **Always include rollback**: Define how to undo the migration
3. **Test locally first**: Verify migrations work before deploying
4. **Keep migrations small**: One logical change per migration
5. **Use descriptive IDs**: Include date and description
6. **Document complex changes**: Add comments explaining why

## Resources

- [Liquibase Documentation](https://docs.liquibase.com/)
- [Liquibase YAML Format](https://docs.liquibase.com/concepts/changelogs/yaml-format.html)
- [Spring Boot Liquibase](https://docs.spring.io/spring-boot/reference/howto/data-initialization.html#howto.data-initialization.migration-tool.liquibase)