# Liquibase Workflow Visualization

## 🔄 Complete Workflow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    LIQUIBASE WORKFLOW                            │
└─────────────────────────────────────────────────────────────────┘

📝 STEP 1: CREATE MIGRATION
───────────────────────────────────────────────────────────────────
$ make lb-create NAME=add-user-phone

What happens automatically:
1. ✅ Finds last migration number (e.g., 004)
2. ✅ Creates: 005-add-user-phone.yaml
3. ✅ Adds template with your Git username
4. ✅ Registers in db.changelog-master.yaml automatically!
5. ✅ Shows you the file location

Output:
  ✅ Migración creada: 005-add-user-phone.yaml
  📝 Editando automáticamente el master changelog...
  ✅ Registrada en db.changelog-master.yaml
  
  📂 Ubicación: app/src/main/resources/db/changelog/migrations/005-add-user-phone.yaml
  
  🔧 Próximos pasos:
     1. Edita el archivo de migración
     2. Ejecuta: make liquibase-validate
     3. Prueba con: make liquibase-test

───────────────────────────────────────────────────────────────────
📝 STEP 2: EDIT MIGRATION FILE
───────────────────────────────────────────────────────────────────
$ vim app/src/main/resources/db/changelog/migrations/005-add-user-phone.yaml

Add your changes:
databaseChangeLog:
  - changeSet:
      id: 005-add-user-phone
      author: jeudry
      comment: Add phone number to users
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

───────────────────────────────────────────────────────────────────
✅ STEP 3: VALIDATE
───────────────────────────────────────────────────────────────────
$ make lb-validate

What happens:
1. ✅ Checks YAML syntax of ALL migration files
2. ✅ Reports any errors immediately
3. ✅ Prevents running broken migrations

Output:
  🔍 Validando archivos de migración...
     ✅ 001-create-schemas.yaml
     ✅ 002-create-user-service-tables.yaml
     ✅ 003-create-chat-service-tables.yaml
     ✅ 004-create-notification-service-tables.yaml
     ✅ 005-add-user-phone.yaml
  
  ✅ Todas las migraciones son válidas

───────────────────────────────────────────────────────────────────
🧪 STEP 4: TEST
───────────────────────────────────────────────────────────────────
$ make lb-test

What happens:
1. ✅ Builds the project
2. ✅ Checks if Docker services are running
3. ✅ Starts them if needed
4. ✅ Runs application with local profile
5. ✅ Liquibase executes automatically
6. ✅ Shows Liquibase logs

Output:
  🧪 Probando migraciones en ambiente local...
  📦 Compilando proyecto...
  🐳 Verificando servicios Docker...
  🚀 Ejecutando aplicación con Liquibase...
  
  [Application logs show:]
  Liquibase: Successfully acquired change log lock
  Liquibase: Reading resource: db/changelog/db.changelog-master.yaml
  Liquibase: Running Changeset: db/changelog/migrations/005-add-user-phone.yaml::005-add-user-phone::jeudry
  Liquibase: Columns phone_number(varchar(20)) added to users
  Liquibase: ChangeSet db/changelog/migrations/005-add-user-phone.yaml::005-add-user-phone::jeudry ran successfully in 45ms
  Liquibase: Successfully released change log lock

───────────────────────────────────────────────────────────────────
📊 STEP 5: VERIFY
───────────────────────────────────────────────────────────────────
$ make lb-status

What happens:
1. ✅ Connects to database
2. ✅ Queries databasechangelog table
3. ✅ Shows last 10 applied migrations

Output:
  📊 Estado de las migraciones en la base de datos:
              id              |  author |              filename                | dateexecuted        | orderexecuted 
  ----------------------------+---------+--------------------------------------+---------------------+---------------
   005-add-user-phone         | jeudry  | db/changelog/migrations/005-...      | 2025-12-03 15:30:00 |             5
   004-create-notification... | team    | db/changelog/migrations/004-...      | 2025-12-03 15:29:55 |             4
   003-create-chat-service... | team    | db/changelog/migrations/003-...      | 2025-12-03 15:29:50 |             3

───────────────────────────────────────────────────────────────────
✅ DONE! Commit and Push
───────────────────────────────────────────────────────────────────
$ git add .
$ git commit -m "feat: add phone number field to users"
$ git push

When deployed, Liquibase will automatically:
1. ✅ Detect the new changeset
2. ✅ Execute it on the production database
3. ✅ Record it in databasechangelog
4. ✅ Skip it on next runs (idempotent)
```

## 🔁 What Happens Behind the Scenes

### When You Run `make lb-create NAME=add-user-phone`

```
┌──────────────────────────────────────────────────────────────┐
│ 1. Find Last Migration                                        │
├──────────────────────────────────────────────────────────────┤
│   $ find migrations/ -name "*.yaml" | sort | tail -1         │
│   → 004-create-notification-service-tables.yaml               │
│   → Extract number: 004                                       │
└──────────────────────────────────────────────────────────────┘
                            ⬇
┌──────────────────────────────────────────────────────────────┐
│ 2. Calculate Next Number                                      │
├──────────────────────────────────────────────────────────────┤
│   004 + 1 = 005                                               │
│   Format as 3 digits: "005"                                   │
└──────────────────────────────────────────────────────────────┘
                            ⬇
┌──────────────────────────────────────────────────────────────┐
│ 3. Create Migration File                                      │
├──────────────────────────────────────────────────────────────┤
│   Filename: 005-add-user-phone.yaml                           │
│   Author: $(git config user.name)                             │
│   Template: databaseChangeLog with changeset + rollback       │
└──────────────────────────────────────────────────────────────┘
                            ⬇
┌──────────────────────────────────────────────────────────────┐
│ 4. Auto-Register in Master (THE MAGIC! ✨)                   │
├──────────────────────────────────────────────────────────────┤
│   Appends to db.changelog-master.yaml:                        │
│     - include:                                                │
│         file: db/changelog/migrations/005-add-user-phone.yaml │
└──────────────────────────────────────────────────────────────┘
```

### When You Run `make lb-test`

```
┌──────────────────────────────────────────────────────────────┐
│ 1. Build Project                                              │
├──────────────────────────────────────────────────────────────┤
│   $ ./gradlew build -x test -q                                │
│   → Compiles Kotlin code                                      │
│   → Packages resources                                        │
└──────────────────────────────────────────────────────────────┘
                            ⬇
┌──────────────────────────────────────────────────────────────┐
│ 2. Check Docker Services                                      │
├──────────────────────────────────────────────────────────────┤
│   $ docker-compose -f docker-compose.orb.yml ps               │
│   Are they running? Yes → Continue                            │
│                     No  → Start them with docker-compose up   │
└──────────────────────────────────────────────────────────────┘
                            ⬇
┌──────────────────────────────────────────────────────────────┐
│ 3. Run Application                                            │
├──────────────────────────────────────────────────────────────┤
│   $ ./gradlew :app:bootRun --args='--spring.profiles.active=local' │
│                                                               │
│   Spring Boot starts →                                        │
│   Liquibase executes automatically before app fully starts →  │
│   Shows Liquibase logs                                        │
└──────────────────────────────────────────────────────────────┘
```

## 📂 File Structure After `make lb-create`

```
app/src/main/resources/db/changelog/
├── db.changelog-master.yaml           ← UPDATED AUTOMATICALLY ✨
│   └── Includes:
│       ├── migrations/001-create-schemas.yaml
│       ├── migrations/002-create-user-service-tables.yaml
│       ├── migrations/003-create-chat-service-tables.yaml
│       ├── migrations/004-create-notification-service-tables.yaml
│       └── migrations/005-add-user-phone.yaml  ← NEW ENTRY ADDED
│
└── migrations/
    ├── 001-create-schemas.yaml
    ├── 002-create-user-service-tables.yaml
    ├── 003-create-chat-service-tables.yaml
    ├── 004-create-notification-service-tables.yaml
    └── 005-add-user-phone.yaml        ← NEW FILE CREATED
```

## 🎯 The Key Innovation

### Before (Manual) 😓
```bash
1. Create file
2. ✋ MANUALLY edit db.changelog-master.yaml
3. ✋ Add include section
4. ✋ Hope you didn't make a typo
5. Test
```

### After (Automated) 🎉
```bash
1. make lb-create NAME=my-change
   ✅ File created
   ✅ Master updated automatically
   ✅ No manual editing needed!
2. Test
```

## 💪 Why This Is Powerful

1. **Zero Manual Editing**: No touching `db.changelog-master.yaml`
2. **Instant Feedback**: Validate before running
3. **One-Command Testing**: Full stack with `make lb-test`
4. **Database Visibility**: Check status with `make lb-status`
5. **Safety**: Validation catches errors early
6. **Speed**: Short aliases (`lb-*`) for fast typing

## 🚀 Quick Command Cheat Sheet

```bash
make lb-create NAME=xyz    # Create + auto-register
make lb-validate           # Check YAML syntax
make lb-test               # Build + run + apply
make lb-status             # See what's in DB
make lb-list               # List all migrations
```

That's it! From idea to deployed migration in 3 commands! 🎊