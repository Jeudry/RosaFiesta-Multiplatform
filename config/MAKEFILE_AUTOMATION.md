# Makefile Liquibase Automation - Developer Guide

## 🚀 What's New?

The Liquibase workflow has been **fully automated** using Make commands. No more manual editing of `db.changelog-master.yaml` or running multiple scripts!

## ⚡ The ONE Command Workflow

### Before (Manual) 😓

```bash
# 1. Create migration file manually
./config/scripts/liquibase.sh create add-user-phone

# 2. Edit db.changelog-master.yaml manually
vim app/src/main/resources/db/changelog/db.changelog-master.yaml
# Add:
#   - include:
#       file: db/changelog/migrations/00X-add-user-phone.yaml

# 3. Edit the migration file
vim app/src/main/resources/db/changelog/migrations/00X-add-user-phone.yaml

# 4. Validate
./config/scripts/liquibase.sh validate

# 5. Test
docker-compose -f docker-compose.orb.yml up -d
./gradlew :app:bootRun --args='--spring.profiles.active=local'
```

### After (Automated) 🎉

```bash
# 1. Create migration (auto-registers!)
make lb-create NAME=add-user-phone

# 2. Edit the generated file
vim app/src/main/resources/db/changelog/migrations/00X-add-user-phone.yaml

# 3. Validate and test
make lb-validate && make lb-test
```

**That's it!** The master changelog is updated automatically. 🎊

## 🎯 Key Features

### 1. Auto-Registration

When you create a migration with `make liquibase-create`, it:
- ✅ Creates the migration file with template
- ✅ Automatically registers it in `db.changelog-master.yaml`
- ✅ Uses your Git username as author
- ✅ Calculates the next sequential number

### 2. Validation

```bash
make liquibase-validate
```

Validates **all** migration files for YAML syntax errors before you waste time running the app.

### 3. Easy Testing

```bash
make liquibase-test
```

- Builds the project
- Checks Docker services
- Starts them if needed
- Runs the application
- Shows Liquibase execution logs

### 4. Status Checking

```bash
make liquibase-status
```

Connects to the database and shows the last 10 applied migrations directly from `databasechangelog` table.

### 5. Quick Reset

```bash
make liquibase-reset-local
```

Nuclear option: drops everything, recreates fresh database, ready for clean migrations.

## 📋 Complete Command Reference

### Short Aliases (Fast typing)

```bash
make lb-create NAME=my-change      # Create migration
make lb-list                       # List migrations
make lb-validate                   # Validate files
make lb-test                       # Test locally
make lb-status                     # Check DB status
```

### Full Commands (Explicit)

```bash
make liquibase-create NAME=my-change
make liquibase-list
make liquibase-validate
make liquibase-test
make liquibase-status
make liquibase-verify
make liquibase-help
make liquibase-reset-local
```

## 🎓 Real-World Examples

### Example 1: Add Column

```bash
# Create
make lb-create NAME=add-user-timezone

# Output:
# ✅ Migración creada: 005-add-user-timezone.yaml
# 📝 Editando automáticamente el master changelog...
# ✅ Registrada en db.changelog-master.yaml
# 
# 📂 Ubicación: app/src/main/resources/db/changelog/migrations/005-add-user-timezone.yaml
# 
# 🔧 Próximos pasos:
#    1. Edita el archivo de migración
#    2. Ejecuta: make liquibase-validate
#    3. Prueba con: make liquibase-test

# Edit the file (add your changes)
# ...

# Validate
make lb-validate
# Output:
# 🔍 Validando archivos de migración...
#    ✅ 001-create-schemas.yaml
#    ✅ 002-create-user-service-tables.yaml
#    ✅ 003-create-chat-service-tables.yaml
#    ✅ 004-create-notification-service-tables.yaml
#    ✅ 005-add-user-timezone.yaml
# 
# ✅ Todas las migraciones son válidas

# Test
make lb-test
# Starts app, applies migration, shows logs
```

### Example 2: Create Index

```bash
# One command to create
make lb-create NAME=index-users-email-lookup

# Edit file, then validate and test together
make lb-validate && make lb-test
```

### Example 3: Check What's Applied

```bash
make lb-status

# Output:
# 📊 Estado de las migraciones en la base de datos:
#              id              |     author      |                filename                | dateexecuted        | orderexecuted 
# -----------------------------+-----------------+----------------------------------------+---------------------+---------------
#  005-add-user-timezone       | jeudry          | db/changelog/migrations/005-...        | 2025-12-03 15:30:00 |             5
#  004-create-notification...  | rosafiesta-team | db/changelog/migrations/004-...        | 2025-12-03 15:29:55 |             4
#  003-create-chat-service...  | rosafiesta-team | db/changelog/migrations/003-...        | 2025-12-03 15:29:50 |             3
```

## 🔧 Integration with Development Workflow

### Daily Development

```bash
# Morning: start services
make orb-up

# Run app
make run-local

# Need to change schema?
make lb-create NAME=add-feature-x
# Edit migration file
make lb-validate && make lb-test

# Check it worked
make lb-status
```

### Before Committing

```bash
# Validate everything
make lb-validate
make lb-verify

# Ensure it works fresh
make liquibase-reset-local
make run-local
# Wait for app to start successfully

# Commit
git add .
git commit -m "feat: add feature-x migration"
```

## 🆚 Comparison: Scripts vs Make

| Task | Old Way (Scripts) | New Way (Make) |
|------|------------------|----------------|
| Create migration | `./config/scripts/liquibase.sh create NAME` | `make lb-create NAME=...` |
| Register in master | ✋ Manual edit | ✅ Automatic |
| List migrations | `./config/scripts/liquibase.sh list` | `make lb-list` |
| Validate | `./config/scripts/liquibase.sh validate` | `make lb-validate` |
| Test | Start docker + gradlew + profile | `make lb-test` |
| Check DB status | Manual psql query | `make lb-status` |
| Reset DB | Multiple docker commands | `make liquibase-reset-local` |

## 💡 Pro Tips

### 1. Chain Commands

```bash
# Create, validate, test in one go
make lb-create NAME=my-feature && \
  make lb-validate && \
  make lb-test
```

### 2. Use Tab Completion

```bash
make liqui<TAB>    # Completes to liquibase-
make lb-<TAB>      # Shows all short aliases
```

### 3. Check Help Anytime

```bash
make help             # All Make commands
make liquibase-help   # Liquibase-specific help
```

### 4. Verify Before Deploying

```bash
make lb-verify        # Checks configuration
make lb-validate      # Validates files
make lb-test          # Tests execution
```

## 🚨 Important Notes

### Auto-Registration

The `make lb-create` command **automatically adds** your migration to `db.changelog-master.yaml`. You don't need to edit it manually!

### Git Username

The command uses your Git username as the author:
```bash
git config user.name  # This will be used as author
```

If not set, it defaults to `rosafiesta-team`.

### Sequential Numbering

The system automatically calculates the next migration number:
- If last migration is `004-xxx.yaml`
- Next will be `005-yyy.yaml`

## 📚 Additional Resources

- [Makefile Examples](MAKEFILE_EXAMPLES.md) - Detailed examples
- [Liquibase Guide](../docs/LIQUIBASE.md) - Complete documentation
- [Quick Reference](LIQUIBASE_QUICK_REF.md) - Command cheat sheet

## 🎉 Summary

**Before:** Manual, error-prone, multiple steps
**After:** One command, automatic, foolproof

The Makefile automation makes Liquibase migrations as easy as:

```bash
make lb-create NAME=my-change
# Edit file
make lb-validate && make lb-test
```

That's it! Happy migrating! 🚀