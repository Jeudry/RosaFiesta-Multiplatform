# Rollback Commands Quick Visual Guide

## 🔄 Rollback Options Visualization

```
Current Database State:
┌─────────────────────────────────────────────────────┐
│ Applied Migrations (databasechangelog table)        │
├─────────────────────────────────────────────────────┤
│ 005-add-user-phone           (Latest)               │
│ 004-create-notification-tables                      │
│ 003-create-chat-service-tables                      │
│ 002-create-user-service-tables                      │
│ 001-create-schemas           (First)                │
└─────────────────────────────────────────────────────┘
```

## Command Comparison

### 1️⃣ `make lb-rollback` - Undo Last One

```
Before:                          After:
┌─────────────────┐             ┌─────────────────┐
│ 005 (Latest) ❌ │  ───────>   │                 │
│ 004             │             │ 004 (Latest) ✅ │
│ 003             │             │ 003             │
│ 002             │             │ 002             │
│ 001             │             │ 001             │
└─────────────────┘             └─────────────────┘

Usage:
$ make lb-rollback
```

### 2️⃣ `make lb-rollback-count COUNT=3` - Undo Multiple

```
Before:                          After:
┌─────────────────┐             ┌─────────────────┐
│ 005 ❌          │  ───────>   │                 │
│ 004 ❌          │             │                 │
│ 003 ❌          │             │                 │
│ 002             │             │ 002 (Latest) ✅ │
│ 001             │             │ 001             │
└─────────────────┘             └─────────────────┘

Usage:
$ make lb-rollback-count COUNT=3
```

### 3️⃣ `make lb-rollback-to ID=003` - Undo To Specific

```
Before:                          After:
┌─────────────────┐             ┌─────────────────┐
│ 005 ❌          │  ───────>   │                 │
│ 004 ❌          │             │                 │
│ 003 ✅ (Keep)   │             │ 003 (Latest) ✅ │
│ 002             │             │ 002             │
│ 001             │             │ 001             │
└─────────────────┘             └─────────────────┘

Usage:
$ make lb-rollback-to ID=003-create-chat-service-tables
```

### 4️⃣ `make lb-rollback-sql` - View Only (No Changes)

```
┌─────────────────────────────────────────────────────┐
│ Shows the rollback SQL from YAML file               │
│ DOESN'T execute anything                            │
│ Safe to run anytime                                 │
└─────────────────────────────────────────────────────┘

Usage:
$ make lb-rollback-sql

Output:
📜 SQL de rollback para la última migración:
      rollback:
        - dropColumn:
            tableName: users
            columnName: phone_number
```

## 🎯 Decision Tree

```
Need to rollback?
    │
    ├─ Just the last one?
    │  └─> make lb-rollback
    │
    ├─ Several recent ones?
    │  └─> make lb-rollback-count COUNT=N
    │
    ├─ Back to specific point?
    │  └─> make lb-rollback-to ID=xxx
    │
    └─ Just want to see what would happen?
       └─> make lb-rollback-sql
```

## 📊 Complete Workflow Example

```
┌──────────────────────────────────────────────────────────────┐
│ SCENARIO: Migration 005 has a bug, need to fix               │
└──────────────────────────────────────────────────────────────┘

Step 1: Check Current State
─────────────────────────────────────────────────────────────
$ make lb-status

  005-add-user-phone           (BUGGY!)
  004-create-notification-tables
  003-create-chat-service-tables


Step 2: View Rollback SQL (Optional)
─────────────────────────────────────────────────────────────
$ make lb-rollback-sql

  rollback:
    - dropColumn:
        tableName: users
        columnName: phone_number


Step 3: Perform Rollback
─────────────────────────────────────────────────────────────
$ make lb-rollback

  ⚠️  ADVERTENCIA: Esto deshará la última migración aplicada
  
  📊 Migraciones actualmente aplicadas:
    005-add-user-phone
    004-create-notification-tables
  
  ¿Deshacer la última migración? (yes): yes
  ✅ Rollback registrado.


Step 4: Verify Rollback
─────────────────────────────────────────────────────────────
$ make lb-status

  004-create-notification-tables  (Now latest)
  003-create-chat-service-tables
  002-create-user-service-tables


Step 5: Fix Migration File
─────────────────────────────────────────────────────────────
$ vim app/src/.../005-add-user-phone.yaml
  [Fix the bug in the YAML file]


Step 6: Validate
─────────────────────────────────────────────────────────────
$ make lb-validate

  ✅ Todas las migraciones son válidas


Step 7: Test Again
─────────────────────────────────────────────────────────────
$ make lb-test

  [App starts]
  Liquibase: Running Changeset: 005-add-user-phone
  Liquibase: ran successfully
  ✅ Migration fixed!


Step 8: Verify Final State
─────────────────────────────────────────────────────────────
$ make lb-status

  005-add-user-phone           (FIXED! ✅)
  004-create-notification-tables
  003-create-chat-service-tables
```

## ⚠️ Important Notes

### What Rollback DOES:
✅ Removes migration from `databasechangelog` table
✅ Shows you what will be undone
✅ Asks for confirmation
✅ Safe to run (with confirmation)

### What Rollback DOESN'T Do:
❌ Automatically execute rollback SQL
❌ Restore lost data
❌ Guarantee 100% clean state

### Best Practice:
1. **View first**: Use `lb-rollback-sql` to see what will happen
2. **Confirm**: Read the confirmation prompt carefully
3. **Test**: After rollback, test that everything still works
4. **Fix**: Correct the migration file
5. **Reapply**: Test the fixed migration

## 🚀 Quick Commands Cheat Sheet

```bash
# View what will be rolled back (safe)
make lb-rollback-sql

# Rollback last migration
make lb-rollback

# Rollback 3 migrations
make lb-rollback-count COUNT=3

# Rollback to specific migration
make lb-rollback-to ID=003-create-chat

# Check current status
make lb-status
```

## 💡 When to Use Each Command

| Situation | Command | Why |
|-----------|---------|-----|
| Last migration failed | `lb-rollback` | Quick fix for immediate issue |
| Testing different approaches | `lb-rollback` | Try, rollback, try again |
| Feature branch cancelled | `lb-rollback-count` | Remove all feature migrations |
| Need known good state | `lb-rollback-to` | Go back to specific point |
| Reviewing before action | `lb-rollback-sql` | See what will happen |
| Production hotfix | `lb-rollback-to` | Revert to stable version |

Remember: **Always test rollbacks in local/dev before production!** 🎯