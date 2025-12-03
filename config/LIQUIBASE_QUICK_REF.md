# Liquibase Quick Reference

## Common Commands

### 🚀 Using Make (Recommended)

```bash
# Create new migration (auto-registers in master)
make liquibase-create NAME=add-user-bio-field
# or short alias:
make lb-create NAME=add-user-bio-field

# List all migrations
make liquibase-list
# or: make lb-list

# Validate migration files
make liquibase-validate
# or: make lb-validate

# Test migrations locally
make liquibase-test
# or: make lb-test

# Check migration status in DB
make liquibase-status
# or: make lb-status

# Rollback (undo migrations)
make liquibase-rollback-one                    # Undo last migration
# or: make lb-rollback

make liquibase-rollback-count COUNT=2          # Undo N migrations
# or: make lb-rollback-count COUNT=2

make liquibase-rollback-to ID=003-create-chat  # Undo to specific migration
# or: make lb-rollback-to ID=003-create-chat

make liquibase-rollback-sql                    # Show rollback SQL for last migration
# or: make lb-rollback-sql

# Semi-automatic generation from JPA entities 🤖
make liquibase-diff                        # Get hints for diff
# or: make lb-diff

make liquibase-generate-schema             # Export DDL from entities
# or: make lb-generate

make liquibase-register-manual FILE=xxx    # Register manually created file
# or: make lb-register FILE=xxx

# Semi-automatic workflow:
# 1. make lb-generate          → Generates DDL from entities
# 2. Review DDL file           → See what Hibernate expects
# 3. make lb-create NAME=xxx   → Create migration
# 4. Copy/convert DDL to YAML  → Use DDL as reference
# 5. make lb-validate          → Validate
# 6. make lb-test              → Test

# Verify Liquibase setup
make liquibase-verify

# Reset local database
make liquibase-reset-local
```

### 🛠️ Using Scripts (Alternative)

```bash
# List all migrations
./config/scripts/liquibase.sh list

# Create a new migration (manual registration required)
./config/scripts/liquibase.sh create add-user-bio-field

# Validate migration files
./config/scripts/liquibase.sh validate
```

### 📦 Manual Process

```bash
# 1. Start Docker services
docker compose -f docker-compose.orb.yml up -d

# 2. Run application (migrations run automatically)
./gradlew :app:bootRun --args='--spring.profiles.active=local'

# 3. Check logs for: "Liquibase: Successfully acquired change log lock"
```

## Migration File Structure

```yaml
databaseChangeLog:
  - changeSet:
      id: 00X-descriptive-name
      author: your-name
      comment: What this migration does
      changes:
        # Your changes here
      rollback:
        # How to undo the changes
```

## Common Changes

### Add Column
```yaml
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
```

### Create Index
```yaml
- createIndex:
    indexName: idx_users_bio
    schemaName: user_service
    tableName: users
    columns:
      - column:
          name: bio
```

### Add Foreign Key
```yaml
- addForeignKeyConstraint:
    baseTableName: orders
    baseColumnNames: user_id
    constraintName: fk_orders_user
    referencedTableName: users
    referencedColumnNames: id
```

### Drop Column
```yaml
- dropColumn:
    tableName: users
    schemaName: user_service
    columnName: old_field
```

## Rollback Operations

### Undo Last Migration
```bash
make liquibase-rollback-one
# or: make lb-rollback

# Shows which migration will be undone
# Asks for confirmation
# Removes from databasechangelog
```

### Undo Multiple Migrations
```bash
make liquibase-rollback-count COUNT=3
# or: make lb-rollback-count COUNT=3

# Undoes the last 3 migrations
# Shows what will be undone
# Asks for confirmation
```

### Undo To Specific Migration
```bash
make liquibase-rollback-to ID=003-create-chat
# or: make lb-rollback-to ID=003-create-chat

# Undoes all migrations after 003-create-chat
# Keeps 003-create-chat and earlier
# Shows what will be undone
```

### View Rollback SQL
```bash
make liquibase-rollback-sql
# or: make lb-rollback-sql

# Shows the rollback SQL from the YAML file
# Doesn't execute anything
# Useful for reviewing before rollback
```

### Example Rollback Workflow
```bash
# 1. Check current status
make lb-status

# 2. View what will be rolled back
make lb-rollback-sql

# 3. Perform rollback
make lb-rollback

# 4. Verify
make lb-status
```

## Troubleshooting

### Migration Failed - Rollback and Fix
```bash
# 1. Rollback the failed migration
make lb-rollback

# 2. Fix the migration file
vim app/src/main/resources/db/changelog/migrations/00X-my-migration.yaml

# 3. Validate
make lb-validate

# 4. Test again
make lb-test
```

### Clear failed migration
```sql
-- Connect to database
docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta

-- Remove failed changeset
DELETE FROM databasechangelog WHERE id = 'failed-changeset-id';
```

### Reset local database
```bash
docker compose -f docker-compose.orb.yml down -v
docker compose -f docker-compose.orb.yml up -d
./gradlew :app:bootRun --args='--spring.profiles.active=local'
```

## Schema Organization

- `user_service` - User management tables
- `chat_service` - Chat functionality tables  
- `notification_service` - Notification tables
- `public` - Liquibase tracking tables

## Files Location

- Migrations: `app/src/main/resources/db/changelog/migrations/`
- Master changelog: `app/src/main/resources/db/changelog/db.changelog-master.yaml`
- Scripts: `config/scripts/liquibase.sh`
- Documentation: `docs/LIQUIBASE.md`