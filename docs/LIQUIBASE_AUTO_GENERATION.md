# Liquibase Auto-Generation from JPA Entities

## 🤖 What is Semi-Automatic Migration Generation?

Instead of **manually writing** every changeset from scratch, you can:

1. **Define your entities** in JPA/Hibernate (which you already do)
2. **Generate DDL** from those entities automatically
3. **Use the DDL as reference** to create Liquibase migrations
4. **Review and convert** to proper changesets
5. **Apply** them like normal migrations

This is **semi-automatic** because:
- ✅ DDL generation is automatic (Hibernate does it)
- 🔧 You convert DDL to Liquibase YAML (semi-manual step)
- ✅ You add rollbacks and improve the migration
- ✅ Apply automatically like any migration

**Why semi-automatic?**
- Hibernate knows exactly what schema your entities need
- You get DDL as a blueprint
- You maintain control over the final migration
- Best of both worlds: automation + control

## 🎯 Two Generation Modes

### Mode 1: Diff (Recommended) 🌟

**Compares current database with JPA entities**

```bash
make liquibase-diff
# or
make lb-diff
```

**What it does:**
1. Connects to your local database
2. Reads your JPA entities
3. **Generates only the differences**
4. Creates a changeset with changes needed

**Best for:**
- Adding new fields to existing tables
- Adding new tables
- Modifying existing schema
- Day-to-day development

**Example:**
```bash
$ make lb-diff

🔄 Generación automática de migración desde entidades JPA

Este comando:
  1. Compara la BD actual con tus entidades JPA
  2. Genera un changeset con las diferencias
  3. Crea archivo listo para revisar y ajustar

📋 Verificando prerequisitos...
✅ PostgreSQL está corriendo

📝 Archivo de salida: app/src/main/resources/db/changelog/migrations/006-hibernate-diff-20251203-120000.yaml

📦 Compilando proyecto...
✅ Compilación exitosa

🔄 Ejecutando Liquibase diff...
✅ Diff generado exitosamente!

📄 Archivo: 006-hibernate-diff-20251203-120000.yaml

🔧 Próximos pasos:
   1. Revisa el archivo generado
   2. Edita si es necesario (agrega comentarios, ajusta nombres)
   3. Agrega sección de rollback
   4. Registra en db.changelog-master.yaml:
      make liquibase-register-manual FILE=006-hibernate-diff-20251203-120000
   5. Valida: make liquibase-validate
   6. Prueba: make liquibase-test
```

### Mode 2: Generate Complete Schema

**Generates entire schema from entities (no DB comparison)**

```bash
make liquibase-generate-schema
# or
make lb-generate
```

**What it does:**
1. Reads ALL your JPA entities
2. Generates complete schema DDL
3. Converts to Liquibase changesets
4. No database connection needed

**Best for:**
- Initial schema generation
- Comparing with existing migrations
- Documentation
- Setting up new environments

## 📋 Complete Workflow

### Scenario: You Added a New Field to User Entity

**Step 1: Update Entity**

```kotlin
@Entity
@Table(name = "users", schema = "user_service")
class UserEntity(
    // ...existing fields...
    
    // NEW FIELD
    @Column(name = "phone_number", length = 20)
    var phoneNumber: String? = null,
    
    @Column(name = "phone_verified")
    var phoneVerified: Boolean = false
)
```

**Step 2: Generate Diff**

```bash
$ make lb-diff
```

This creates: `006-hibernate-diff-20251203-120000.yaml`

**Step 3: Review Generated File**

```yaml
databaseChangeLog:
  - changeSet:
      id: 1701619200000-1
      author: hibernate
      changes:
        - addColumn:
            tableName: users
            schemaName: user_service
            columns:
              - column:
                  name: phone_number
                  type: VARCHAR(20)
              - column:
                  name: phone_verified
                  type: BOOLEAN
                  defaultValueBoolean: false
```

**Step 4: Improve the Generated File**

Edit to add better metadata and rollback:

```yaml
databaseChangeLog:
  - changeSet:
      id: 006-add-phone-to-users
      author: jeudry
      comment: Add phone number and verification status to users
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
                  constraints:
                    nullable: false
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
```

**Step 5: Register in Master Changelog**

```bash
$ make lb-register FILE=006-hibernate-diff-20251203-120000

✅ Archivo registrado en master changelog
📄 006-hibernate-diff-20251203-120000.yaml
```

Or rename file first to something cleaner:
```bash
mv app/src/main/resources/db/changelog/migrations/006-hibernate-diff-20251203-120000.yaml \
   app/src/main/resources/db/changelog/migrations/006-add-phone-to-users.yaml

make lb-register FILE=006-add-phone-to-users
```

**Step 6: Validate and Test**

```bash
$ make lb-validate
✅ Todas las migraciones son válidas

$ make lb-test
# App starts, migration applies automatically
```

## 🎯 When to Use Each Method

### Use Manual Creation (`make lb-create`)

✅ Simple changes you understand well
✅ Renaming columns (Hibernate diff won't detect)
✅ Data migrations
✅ Custom SQL needed
✅ Complex constraints

### Use Auto-Generation (`make lb-diff`)

✅ Adding multiple fields
✅ Adding new entities/tables
✅ Changing column types
✅ Adding indexes (defined in entity)
✅ Large schema changes
✅ You want to verify nothing was missed

### Use Complete Generation (`make lb-generate`)

✅ Initial project setup
✅ Verifying current schema matches entities
✅ Documentation purposes
✅ Comparing with existing migrations

## 💡 Pro Tips

### 1. Always Review Generated Code

Automatic generation is smart but not perfect:
- ❌ May generate verbose names
- ❌ May not add meaningful comments
- ❌ Won't generate rollback sections
- ❌ May miss renamed columns (sees as drop + add)

**Always review and improve!**

### 2. Combine Both Approaches

```bash
# Complex change with multiple tables
make lb-diff          # Generate base
# Edit file, add custom logic
make lb-validate      # Check it's valid
make lb-test          # Test it works
```

### 3. Use Diff Before Committing

```bash
# Before committing, ensure entities match DB
make lb-diff

# If diff shows nothing:
✅ Entities and DB are in sync

# If diff shows changes:
⚠️  You have unmigrated entity changes!
```

### 4. Version Control Tips

```bash
# Generated files have timestamps
006-hibernate-diff-20251203-120000.yaml

# Rename to semantic names before committing
006-add-user-phone-fields.yaml
```

## 🔧 Configuration

The Liquibase Gradle plugin is configured in `app/build.gradle.kts`:

```kotlin
liquibase {
    activities.register("diffChangelog") {
        arguments = mapOf(
            "changelogFile" to "src/main/resources/db/changelog/migrations/diff-generated.yaml",
            "url" to "jdbc:postgresql://localhost:5432/rosafiesta",
            "username" to "rosafiesta_user",
            "password" to "rosafiesta_password",
            "referenceUrl" to "hibernate:spring:com.rosafiesta.api?dialect=...",
            "referenceDriver" to "liquibase.ext.hibernate.database.connection.HibernateDriver"
        )
    }
}
```

### Key Components:

1. **Database URL**: Where to compare against
2. **Reference URL**: `hibernate:spring:` tells Liquibase to read JPA entities
3. **Hibernate Driver**: Special Liquibase extension that reads Hibernate metadata

## 📊 Comparison Table

| Feature | Manual (`lb-create`) | Auto-Diff (`lb-diff`) | Auto-Generate (`lb-generate`) |
|---------|---------------------|----------------------|-------------------------------|
| Speed | Slow (manual typing) | Fast (auto-generated) | Fast (auto-generated) |
| Accuracy | Depends on you | High (compares DB) | High (reads entities) |
| Customization | Full control | Review & edit | Review & edit |
| Rollback | You write it | You add it | You add it |
| Comments | You write them | You add them | You add them |
| Complex SQL | Easy | Add manually | Add manually |
| Data migrations | Easy | Add manually | Not supported |
| Renamed columns | Detects correctly | Sees as drop+add | Sees as drop+add |
| **Best for** | Simple changes | Most changes | Initial setup |

## 🚨 Important Warnings

### 1. Hibernate Diff Limitations

**Can't detect:**
- ❌ Renamed columns (sees as drop + recreate)
- ❌ Renamed tables
- ❌ Data migrations
- ❌ Custom constraints not in entity

**May generate:**
- ⚠️ Redundant changes
- ⚠️ Non-optimal DDL
- ⚠️ Verbose names

**Always review!**

### 2. Entity Annotations Must Be Correct

Liquibase reads from JPA annotations:

```kotlin
@Column(
    name = "phone_number",     // ← This is used
    length = 20,               // ← This too
    nullable = true            // ← And this
)
```

If annotations don't match reality → diff will be wrong!

### 3. Schema Must Be Specified

Ensure entities have schema:

```kotlin
@Table(
    name = "users",
    schema = "user_service"    // ← REQUIRED!
)
```

Without schema → migration won't target correct schema!

## 🎓 Learning Path

**Week 1: Manual Only**
- Use `make lb-create` for everything
- Learn changeset structure
- Understand rollbacks

**Week 2: Try Auto-Diff**
- Add a field, use `make lb-diff`
- Compare generated vs manual
- Learn to improve generated code

**Week 3: Hybrid Approach**
- Use `lb-diff` for complex changes
- Use `lb-create` for simple ones
- Become efficient with both

## 📚 Quick Commands Reference

```bash
# Generate diff between DB and entities
make liquibase-diff
make lb-diff

# Generate complete schema from entities
make liquibase-generate-schema
make lb-generate

# Register a manually created file
make liquibase-register-manual FILE=006-my-migration
make lb-register FILE=006-my-migration

# Normal workflow continues
make lb-validate
make lb-test
make lb-status
```

## 🎉 Summary

**Semi-automatic** means:

1. ✅ **Automatic**: Liquibase generates changesets from entities
2. 🔧 **Semi-manual**: You review, improve, add rollbacks
3. ✅ **Automatic**: Apply like normal migrations

**Benefits:**

- ⚡ **Faster**: No typing DDL from scratch
- 🎯 **Accurate**: Compares actual DB vs entities
- 🔍 **Catch mistakes**: Find unmigrated entity changes
- 📚 **Learn**: See how Liquibase represents your entities
- 🔄 **Flexible**: Combine with manual when needed

**You now have the best of both worlds!** 🚀