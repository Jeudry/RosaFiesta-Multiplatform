# Liquibase Rollback Guide

## 🔄 What is Rollback?

Rollback allows you to **undo database migrations** that have already been applied. This is critical when:
- A migration has a bug
- You need to test different versions
- Something goes wrong in production
- You want to return to a previous state

## ⚠️ Important Concepts

### Liquibase Tracks Two Things:

1. **What to apply** - The `changes:` section in YAML
2. **How to undo** - The `rollback:` section in YAML

**Every migration MUST have a rollback section!**

### Example Migration with Rollback

```yaml
databaseChangeLog:
  - changeSet:
      id: 005-add-user-phone
      author: jeudry
      changes:
        - addColumn:
            tableName: users
            schemaName: user_service
            columns:
              - column:
                  name: phone_number
                  type: VARCHAR(20)
      rollback:
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: phone_number
```

## 🛠️ Rollback Commands

### 1. Rollback Last Migration

```bash
make liquibase-rollback-one
# or
make lb-rollback
```

**What it does:**
- Shows the last applied migration
- Asks for confirmation
- Removes it from `databasechangelog` table
- **Note:** You must manually run the rollback SQL or rerun with updated migration

**Example output:**
```
⚠️  ADVERTENCIA: Esto deshará la última migración aplicada

📊 Migraciones actualmente aplicadas:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 005-add-user-phone           | jeudry | 2025-12-03 16:00:00
 004-create-notification...   | team   | 2025-12-03 15:59:55
 003-create-chat-service...   | team   | 2025-12-03 15:59:50

¿Deshacer la última migración? (escribe 'yes' para confirmar): yes
🔄 Ejecutando rollback...
✅ Rollback registrado.
```

### 2. Rollback Multiple Migrations

```bash
make liquibase-rollback-count COUNT=3
# or
make lb-rollback-count COUNT=3
```

**What it does:**
- Undoes the last 3 migrations
- Shows which migrations will be affected
- Asks for confirmation
- Removes them from tracking table

**Example:**
```bash
$ make lb-rollback-count COUNT=2

⚠️  ADVERTENCIA: Esto deshará las últimas 2 migraciones

📊 Migraciones que serán deshechas:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 005-add-user-phone           | jeudry | 2025-12-03 16:00:00
 004-create-notification...   | team   | 2025-12-03 15:59:55

¿Continuar con el rollback? (escribe 'yes' para confirmar): yes
```

### 3. Rollback To Specific Migration

```bash
make liquibase-rollback-to ID=003-create-chat
# or
make lb-rollback-to ID=003-create-chat
```

**What it does:**
- Undoes ALL migrations after the specified one
- Keeps the specified migration and earlier ones
- Useful for reverting to a known good state

**Example:**
```bash
$ make lb-rollback-to ID=003-create-chat

⚠️  ADVERTENCIA: Esto deshará todas las migraciones después de: 003-create-chat

📊 Migraciones que serán deshechas:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 005-add-user-phone           | jeudry | 2025-12-03 16:00:00
 004-create-notification...   | team   | 2025-12-03 15:59:55

¿Continuar con el rollback? (escribe 'yes' para confirmar): yes
```

### 4. View Rollback SQL

```bash
make liquibase-rollback-sql
# or
make lb-rollback-sql
```

**What it does:**
- Shows the rollback SQL from the YAML file
- **Doesn't execute anything**
- Useful for reviewing before actual rollback

**Example output:**
```
📜 SQL de rollback para la última migración:

📄 Archivo: app/src/main/resources/db/changelog/migrations/005-add-user-phone.yaml

Sección de rollback:
-------------------
      rollback:
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: phone_number
```

## 📋 Complete Rollback Workflow

### Scenario: Migration Has a Bug

```bash
# 1. Check what's currently applied
make lb-status

📊 Estado de las migraciones en la base de datos:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 005-add-user-phone           | jeudry | 2025-12-03 16:00:00  ← BUGGY
 004-create-notification...   | team   | 2025-12-03 15:59:55
 003-create-chat-service...   | team   | 2025-12-03 15:59:50

# 2. View the rollback SQL
make lb-rollback-sql

📜 SQL de rollback para la última migración:
      rollback:
        - dropColumn:
            tableName: users
            schemaName: user_service
            columnName: phone_number

# 3. Perform rollback
make lb-rollback
# Type 'yes' to confirm

✅ Rollback registrado.

# 4. Verify rollback worked
make lb-status

📊 Estado de las migraciones en la base de datos:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 004-create-notification...   | team   | 2025-12-03 15:59:55  ← Now last
 003-create-chat-service...   | team   | 2025-12-03 15:59:50

# 5. Fix the migration file
vim app/src/main/resources/db/changelog/migrations/005-add-user-phone.yaml
# Fix the bug

# 6. Validate
make lb-validate

✅ Todas las migraciones son válidas

# 7. Test again
make lb-test

# Application starts, Liquibase sees 005 is not applied, applies it again
✅ Migration successful!

# 8. Verify
make lb-status

📊 Estado de las migraciones en la base de datos:
              id              | author | dateexecuted        
------------------------------+--------+---------------------
 005-add-user-phone           | jeudry | 2025-12-03 16:30:00  ← Fixed!
 004-create-notification...   | team   | 2025-12-03 15:59:55
```

## 🎯 Common Use Cases

### Use Case 1: Testing Different Approaches

```bash
# Try approach A
make lb-create NAME=add-feature-approach-a
# Edit, test
make lb-test

# Doesn't work well, rollback
make lb-rollback

# Try approach B
make lb-create NAME=add-feature-approach-b
# Edit, test
make lb-test

# Much better! Keep this one
```

### Use Case 2: Production Hotfix

```bash
# Production has migrations 001-010 applied
# Migration 010 has a critical bug

# 1. On local, rollback to before the bug
make lb-rollback-to ID=009-before-bug

# 2. Create hotfix
make lb-create NAME=010-hotfix-critical-issue

# 3. Test thoroughly
make lb-validate && make lb-test

# 4. Deploy
# The production DB will:
#   - Keep 001-009 (already applied)
#   - Skip 010 (different ID now, won't apply)
#   - Apply 010-hotfix (new migration)
```

### Use Case 3: Reverting Feature Branch

```bash
# Feature branch added 3 migrations
# Feature is being cancelled

# Rollback all 3
make lb-rollback-count COUNT=3

# Verify
make lb-status

# Continue with main branch migrations
```

## ⚠️ Important Warnings

### 1. Rollback Removes Tracking, Not Data

When you run `make lb-rollback`, it:
- ✅ Removes the migration from `databasechangelog` table
- ❌ **DOES NOT automatically run the rollback SQL**

You must:
1. Run the rollback command to untrack it
2. Restart the app or manually run rollback SQL if needed
3. Fix the migration
4. Apply it again

### 2. Production Rollbacks Are Risky

In production:
- **Data loss** - Rollback might delete data
- **Downtime** - Application might fail during rollback
- **Partial state** - Rollback might not complete

**Best practice:**
- Test rollbacks in local/staging first
- Have a backup before production rollback
- Consider forward fix instead of rollback

### 3. Can't Rollback If No Rollback Section

If your migration doesn't have a `rollback:` section, you must:
- Add it manually
- Or manually write SQL to undo the changes

## 📝 Writing Good Rollbacks

### Rule 1: Every Change Must Be Reversible

```yaml
changes:
  - addColumn:
      tableName: users
      columns:
        - column:
            name: phone_number
            type: VARCHAR(20)

rollback:
  - dropColumn:
      tableName: users
      columnName: phone_number
```

### Rule 2: Rollback Undoes in Reverse Order

```yaml
changes:
  - createTable:
      tableName: orders
  - addForeignKeyConstraint:
      baseTableName: orders
      constraintName: fk_orders_user

rollback:
  # First drop FK (can't drop table with FK)
  - dropForeignKeyConstraint:
      baseTableName: orders
      constraintName: fk_orders_user
  # Then drop table
  - dropTable:
      tableName: orders
```

### Rule 3: Consider Data Loss

```yaml
changes:
  - dropColumn:
      tableName: users
      columnName: old_field
      
rollback:
  # Can add column back, but DATA IS LOST!
  - addColumn:
      tableName: users
      columns:
        - column:
            name: old_field
            type: VARCHAR(255)
  # Data won't magically come back
```

**Better approach:** Deprecate instead of delete
```yaml
changes:
  - addColumn:
      tableName: users
      columns:
        - column:
            name: old_field_deprecated
            type: BOOLEAN
            defaultValueBoolean: true

rollback:
  - dropColumn:
      tableName: users
      columnName: old_field_deprecated
```

## 🔍 Checking Rollback Status

### Check What Can Be Rolled Back

```bash
make lb-status
```

Shows all applied migrations that can be rolled back.

### View Specific Migration's Rollback

```bash
make liquibase-show-rollback FILE=005-add-user-phone
```

Shows the rollback section of a specific migration file.

## 🚀 Quick Reference

| Command | Description |
|---------|-------------|
| `make lb-rollback` | Undo last migration |
| `make lb-rollback-count COUNT=N` | Undo N migrations |
| `make lb-rollback-to ID=xxx` | Undo to specific point |
| `make lb-rollback-sql` | View rollback SQL |
| `make lb-status` | See what's applied |

## 💡 Pro Tips

1. **Always have rollback:** Even if it's just a comment explaining why rollback isn't possible

2. **Test rollback too:** Don't just test apply, test rollback works

3. **Forward fix in production:** Often safer to fix forward than rollback

4. **Document data loss:** If rollback causes data loss, document it in comments

5. **Use transactions:** For complex rollbacks, wrap in transaction

## 📚 Examples

See `config/EXAMPLE_REAL_WORLD.md` for complete examples with rollback.