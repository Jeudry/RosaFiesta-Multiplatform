# Makefile Usage Examples

## 🎯 Common Workflows

### Starting a Development Session

```bash
# 1. Start Docker services
make orb-up

# 2. Check everything is running
make docker-ps

# 3. Run the application
make run-local
```

### Creating a Database Migration

```bash
# 1. Create migration (automatically registers in master changelog)
make liquibase-create NAME=add-user-bio-field

# 2. Edit the generated file
# Edit: app/src/main/resources/db/changelog/migrations/00X-add-user-bio-field.yaml

# 3. Validate syntax
make liquibase-validate

# 4. Test the migration
make liquibase-test

# 5. Check it was applied
make liquibase-status
```

### Working with Existing Migrations

```bash
# List all migrations
make liquibase-list

# Validate all migration files
make liquibase-validate

# Check which migrations are applied in DB
make liquibase-status
```

### Resetting Local Database

```bash
# Complete reset (deletes all data)
make liquibase-reset-local

# Then run app to apply migrations
make run-local
```

## 🔧 Detailed Examples

### Example 1: Add Phone Column to Users

```bash
# Create migration
make liquibase-create NAME=add-phone-to-users
# Output: ✅ Migración creada: 005-add-phone-to-users.yaml
#         ✅ Registrada en db.changelog-master.yaml

# Edit the file (it opens automatically in your editor or edit manually)
# File: app/src/main/resources/db/changelog/migrations/005-add-phone-to-users.yaml

# Content example:
cat > app/src/main/resources/db/changelog/migrations/005-add-phone-to-users.yaml << 'EOF'
databaseChangeLog:
  - changeSet:
      id: 005-add-phone-to-users
      author: jeudry
      comment: Add phone number field to users table
      changes:
        - addColumn:
            tableName: users
            schemaName: user_service
            columns:
              - column:
                  name: phone_number
                  type: VARCHAR(20)
                  constraints:
                    nullable: true
              - column:
                  name: phone_verified
                  type: BOOLEAN
                  defaultValueBoolean: false
      rollback:
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: phone_verified
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: phone_number
EOF

# Validate
make liquibase-validate
# Output: ✅ 005-add-phone-to-users.yaml

# Test
make liquibase-test
# This will start the app and show Liquibase logs
```

### Example 2: Create Index

```bash
# Create migration
make lb-create NAME=add-index-users-email
# (lb-create is short alias for liquibase-create)

# Edit the file
cat > app/src/main/resources/db/changelog/migrations/006-add-index-users-email.yaml << 'EOF'
databaseChangeLog:
  - changeSet:
      id: 006-add-index-users-email
      author: jeudry
      comment: Add performance index on users email for faster lookups
      changes:
        - createIndex:
            indexName: idx_users_email_lookup
            schemaName: user_service
            tableName: users
            columns:
              - column:
                  name: email
            unique: false
      rollback:
        - dropIndex:
            indexName: idx_users_email_lookup
            schemaName: user_service
            tableName: users
EOF

# Validate and test
make lb-validate && make lb-test
```

### Example 3: Add New Table

```bash
# Create migration
make lb-create NAME=create-user-preferences-table

# Edit
cat > app/src/main/resources/db/changelog/migrations/007-create-user-preferences-table.yaml << 'EOF'
databaseChangeLog:
  - changeSet:
      id: 007-create-user-preferences-table
      author: jeudry
      comment: Create table for user preferences
      changes:
        - createTable:
            tableName: user_preferences
            schemaName: user_service
            columns:
              - column:
                  name: id
                  type: BIGINT
                  autoIncrement: true
                  constraints:
                    primaryKey: true
                    primaryKeyName: pk_user_preferences
              - column:
                  name: user_id
                  type: UUID
                  constraints:
                    nullable: false
                    unique: true
                    foreignKeyName: fk_preferences_user
                    references: user_service.users(id)
              - column:
                  name: language
                  type: VARCHAR(10)
                  defaultValue: 'en'
              - column:
                  name: timezone
                  type: VARCHAR(50)
                  defaultValue: 'UTC'
              - column:
                  name: notifications_enabled
                  type: BOOLEAN
                  defaultValueBoolean: true
              - column:
                  name: created_at
                  type: TIMESTAMP WITH TIME ZONE
                  defaultValueComputed: CURRENT_TIMESTAMP
              - column:
                  name: updated_at
                  type: TIMESTAMP WITH TIME ZONE
                  defaultValueComputed: CURRENT_TIMESTAMP
      rollback:
        - dropTable:
            tableName: user_preferences
            schemaName: user_service
EOF

# Validate and test
make lb-validate && make lb-test
```

## 🐳 Docker Workflows

### Quick Start

```bash
# Start everything
make orb-up

# View logs
make orb-logs

# Check status
make docker-ps
```

### Database Access

```bash
# Open PostgreSQL shell
make db-shell

# Example queries in psql:
\dt user_service.*          # List tables in user_service schema
SELECT * FROM users LIMIT 5;
\q                           # Exit

# Open Redis shell
make redis-shell

# Example commands in redis-cli:
KEYS *
GET some_key
EXIT
```

### Troubleshooting

```bash
# Restart services
make orb-restart

# Stop services
make orb-down

# Nuclear option: delete everything and start fresh
make liquibase-reset-local
```

## 📊 Checking Migration Status

```bash
# Show last 10 applied migrations
make liquibase-status

# Output example:
#  id                    | author           | filename                           | dateexecuted        | orderexecuted
# -----------------------+------------------+------------------------------------+---------------------+---------------
#  005-add-phone-to-users| jeudry           | db/changelog/migrations/005-...    | 2025-12-03 10:30:00 | 5
#  004-create-notification| rosafiesta-team | db/changelog/migrations/004-...    | 2025-12-03 10:29:55 | 4
```

## 🔍 Validation and Verification

```bash
# Validate all migrations
make liquibase-validate

# Verify entire Liquibase setup
make liquibase-verify

# List all migrations
make liquibase-list
```

## 🔄 Rollback Examples

### Example 1: Rollback Last Migration (Bug Found)

```bash
# Check current status
make lb-status

# View what will be rolled back
make lb-rollback-sql

# Perform rollback
make lb-rollback
# Type 'yes' to confirm

# Fix the migration file
vim app/src/main/resources/db/changelog/migrations/005-add-user-phone.yaml

# Test again
make lb-test
```

### Example 2: Rollback Multiple Migrations

```bash
# Last 3 migrations have issues
make lb-rollback-count COUNT=3
# Type 'yes' to confirm

# Verify
make lb-status

# Now create corrected versions
make lb-create NAME=corrected-migration-1
# ... edit and test
```

### Example 3: Rollback To Specific Point

```bash
# Want to go back to migration 003
make lb-rollback-to ID=003-create-chat-service-tables
# Type 'yes' to confirm

# This removes 004, 005, etc.
# But keeps 003 and earlier

# Verify
make lb-status
```

## 🆘 Help Commands

```bash
# Show all Make commands
make help

# Show Liquibase-specific help
make liquibase-help
```

## ⚡ Quick Reference

| Command | Description |
|---------|-------------|
| `make orb-up` | Start Docker services |
| `make run-local` | Run application locally |
| `make lb-create NAME=...` | Create new migration |
| `make lb-validate` | Validate migrations |
| `make lb-test` | Test migrations |
| `make lb-status` | Check DB status |
| `make lb-list` | List migrations |
| `make liquibase-reset-local` | Reset local DB |

## 🎓 Pro Tips

1. **Always validate before testing:**
   ```bash
   make lb-validate && make lb-test
   ```

2. **Check status after applying migrations:**
   ```bash
   make lb-test
   # Wait for app to start, then Ctrl+C
   make lb-status
   ```

3. **Use short aliases for speed:**
   ```bash
   make lb-create NAME=my-change
   make lb-validate
   ```

4. **Create descriptive names:**
   ```bash
   # Good
   make lb-create NAME=add-user-avatar-url
   make lb-create NAME=create-payments-table
   
   # Bad
   make lb-create NAME=update
   make lb-create NAME=fix
   ```

5. **Always include rollback:**
   Every migration should have a rollback section that undoes the changes.