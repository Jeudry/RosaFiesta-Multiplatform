# Using sqlFile in Liquibase - Direct SQL Approach

## 🎯 What is sqlFile?

`sqlFile` is a Liquibase changeset type that **executes SQL files directly** without converting to YAML structure.

### Traditional Approach (YAML Structure)

```yaml
databaseChangeLog:
  - changeSet:
      id: 006-add-columns
      changes:
        - addColumn:
            tableName: users
            columns:
              - column: {name: phone, type: VARCHAR(20)}
              - column: {name: bio, type: TEXT}
```

### sqlFile Approach (Direct SQL)

```yaml
databaseChangeLog:
  - changeSet:
      id: 006-add-columns
      sqlFile:
        path: sql/006-add-columns.sql  # ← Direct SQL file
```

```sql
-- sql/006-add-columns.sql
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
ALTER TABLE users ADD COLUMN bio TEXT;
```

## ✅ Advantages of sqlFile

1. **No conversion needed** - DDL from Hibernate goes directly to Liquibase
2. **Hibernate-to-Liquibase in seconds** - No manual YAML writing
3. **Easier to read** - Familiar SQL syntax
4. **Faster development** - 90% automated
5. **Less error-prone** - No YAML syntax mistakes

## 🚀 How It Works in Our Project

### Step 1: Modify Entity

```kotlin
@Entity
class User(
    // ...existing fields...
    
    @Column(name = "phone_number")
    var phoneNumber: String? = null
)
```

### Step 2: Run Generation Command

```bash
$ make lb-generate
```

**What happens automatically:**
1. ✅ Hibernate reads your entities
2. ✅ Generates DDL: `ALTER TABLE users ADD COLUMN phone_number VARCHAR(20);`
3. ✅ Creates SQL file: `sql/006-hibernate-ddl-20251203.sql`
4. ✅ Creates YAML with sqlFile reference
5. ✅ Generates intelligent rollback
6. ✅ Ready to use!

### Step 3: Review Generated Files

**SQL File (auto-generated):**
```sql
-- sql/006-hibernate-ddl-20251203-150000.sql
ALTER TABLE user_service.users ADD COLUMN phone_number VARCHAR(20);
ALTER TABLE user_service.users ADD COLUMN phone_verified BOOLEAN DEFAULT false;
```

**YAML Changeset (auto-generated):**
```yaml
# 006-from-hibernate-20251203-150000.yaml
databaseChangeLog:
  - changeSet:
      id: 006-from-hibernate-20251203-150000
      author: jeudry
      comment: Auto-generated from Hibernate entities
      sqlFile:
        path: sql/006-hibernate-ddl-20251203-150000.sql
        relativeToChangelogFile: true
        splitStatements: true
        endDelimiter: ";"
      rollback:
        - sql:
            sql: |
              ALTER TABLE user_service.users DROP COLUMN phone_number;
              ALTER TABLE user_service.users DROP COLUMN phone_verified;
```

### Step 4: Register and Apply

```bash
# Register in master changelog
$ make lb-register FILE=006-from-hibernate-20251203-150000

# Validate and test
$ make lb-validate && make lb-test

✅ Migration applied successfully!
```

## 📊 Comparison: YAML vs sqlFile

| Aspect | YAML Structure | sqlFile |
|--------|----------------|---------|
| **Conversion** | Manual YAML writing | ❌ Not needed |
| **Speed** | Slow (manual typing) | ⚡ Fast (auto-generated) |
| **Readability** | YAML syntax | 📖 Plain SQL |
| **Hibernate DDL** | Must convert | ✅ Use directly |
| **Errors** | YAML syntax errors | Fewer errors |
| **Portability** | Database-independent | Database-specific SQL |
| **Complex changes** | More verbose | Simpler SQL |
| **Our use case** | Simple changes | Complex changes ⭐ |

## 🎯 When to Use Each

### Use YAML Structure

```yaml
changes:
  - addColumn: ...
```

**When:**
- ✅ Simple change (1-2 fields)
- ✅ Want database independence
- ✅ Using Liquibase-specific features
- ✅ Want type safety

**Example:**
```bash
make lb-create NAME=add-verified-flag
# Write YAML manually
```

### Use sqlFile (Semi-Auto)

```yaml
sqlFile:
  path: sql/my-changes.sql
```

**When:**
- ✅ Complex change (5+ fields, multiple tables)
- ✅ Generated from Hibernate
- ✅ Custom SQL needed
- ✅ Want speed and automation

**Example:**
```bash
make lb-generate
# Uses SQL directly, no conversion
```

## 🔧 sqlFile Options

```yaml
sqlFile:
  path: sql/my-migration.sql           # Path to SQL file
  relativeToChangelogFile: true        # Relative to YAML file location
  splitStatements: true                # Split on ; delimiter
  endDelimiter: ";"                    # Statement separator
  stripComments: false                 # Keep SQL comments
  encoding: UTF-8                      # File encoding
```

## ⚠️ Important Notes

### 1. Rollback Still Manual

Even with sqlFile, **you must provide rollback**:

```yaml
sqlFile:
  path: sql/006-add-columns.sql
rollback:
  - sql:
      sql: |
        ALTER TABLE users DROP COLUMN phone_number;
        ALTER TABLE users DROP COLUMN phone_verified;
```

Our script **generates suggested rollbacks**, but you should review them.

### 2. File Organization

```
migrations/
├── 001-create-schemas.yaml
├── 002-create-users.yaml
├── 003-add-profile.yaml           # ← References SQL file
└── sql/
    └── 003-add-profile.sql        # ← Actual SQL
```

### 3. Both Coexist Perfectly

```yaml
# db.changelog-master.yaml
databaseChangeLog:
  - include:
      file: migrations/001-create-schemas.yaml        # YAML structure
  - include:
      file: migrations/002-create-users.yaml          # YAML structure
  - include:
      file: migrations/003-add-profile.yaml           # sqlFile ✨
  - include:
      file: migrations/004-add-verified.yaml          # YAML structure
```

**No conflicts! Liquibase executes both the same way.**

## 💡 Best Practices

### 1. Review Generated SQL

```bash
# Always review before applying
cat app/src/main/resources/db/changelog/migrations/sql/006-xxx.sql
```

### 2. Verify Rollback

```yaml
rollback:
  # Generated automatically, but REVIEW IT!
  - sql:
      sql: |
        -- Check this is correct
        ALTER TABLE users DROP COLUMN phone_number;
```

### 3. Use Descriptive Names

```bash
# ❌ Bad
006-hibernate-ddl-20251203.sql

# ✅ Good (rename after generation)
006-add-user-profile-fields.sql
006-add-user-profile-fields.yaml
```

### 4. Test Rollback Too

```bash
# Apply migration
make lb-test

# Test rollback
make lb-rollback

# Reapply
make lb-test
```

## 🎓 Examples

### Example 1: Add Multiple Columns

```bash
# 1. Modify entity
@Entity
class User(
    @Column(name = "phone") var phone: String?,
    @Column(name = "bio") var bio: String?,
    @Column(name = "avatar_url") var avatarUrl: String?
)

# 2. Generate
make lb-generate

# Output:
# ✅ sql/006-hibernate-ddl-xxx.sql (3 ALTER TABLE statements)
# ✅ 006-from-hibernate-xxx.yaml (references SQL file + rollback)

# 3. Register and test
make lb-register FILE=006-from-hibernate-xxx
make lb-validate && make lb-test
```

### Example 2: Create New Table

```bash
# 1. Create new entity
@Entity
@Table(name = "user_settings")
class UserSettings(
    @Id var id: Long,
    @Column(name = "user_id") var userId: UUID,
    @Column(name = "theme") var theme: String,
    @Column(name = "language") var language: String
)

# 2. Generate
make lb-generate

# Output:
# ✅ SQL with CREATE TABLE
# ✅ YAML with sqlFile
# ✅ Rollback with DROP TABLE

# 3. Review and apply
cat sql/007-xxx.sql
vim 007-from-hibernate-xxx.yaml
make lb-register FILE=007-from-hibernate-xxx
make lb-test
```

## 📚 Summary

### What sqlFile Does:

1. ✅ **Executes SQL directly** - No YAML conversion
2. ✅ **Works with Hibernate DDL** - Use generated SQL as-is
3. ✅ **Faster development** - 90% automated
4. ✅ **Coexists with YAML** - Use both in same project

### What Our Implementation Adds:

1. ✅ **Auto-generates SQL file** from Hibernate
2. ✅ **Auto-generates YAML changeset** with sqlFile reference
3. ✅ **Auto-generates rollback** (intelligent guessing)
4. ✅ **One command workflow** - `make lb-generate`

### Result:

**Hibernate → Liquibase in 1 command!** 🚀

No manual conversion. No YAML writing. Just review and apply.

---

📚 **See also:**
- `docs/SEMI_AUTOMATIC_SUMMARY.md` - Complete guide
- `config/scripts/liquibase-generate.sh` - Generation script