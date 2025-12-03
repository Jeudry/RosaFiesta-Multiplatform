#!/bin/zsh

# Liquibase Hibernate Auto-Generation Script
# Generates Liquibase changesets from JPA entities using Hibernate DDL export

set -e

PROJECT_ROOT="/Users/sargon/Documents/Coding/KMP/RosaFiesta"
cd "$PROJECT_ROOT"

echo "🔄 Generación Semi-Automática de Migraciones desde Entidades JPA"
echo "================================================================"
echo ""

# Function to show usage
show_usage() {
    echo "Uso: $0 [opción]"
    echo ""
    echo "Opciones:"
    echo "  diff          Genera script DDL desde entidades y compara con BD"
    echo "  export        Exporta schema DDL completo desde entidades"
    echo "  help          Muestra esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0 diff       # Genera DDL y muestra cómo convertirlo en migración"
    echo "  $0 export     # Exporta schema completo en DDL"
    echo ""
    echo "Nota: Este es un proceso SEMI-AUTOMÁTICO."
    echo "      El script genera el DDL, tú lo conviertes en migración Liquibase."
}

# Function to check prerequisites
check_prerequisites() {
    echo "📋 Verificando prerequisitos..."
    
    # Compile project
    echo "📦 Compilando proyecto..."
    ./gradlew :app:compileKotlin -q || {
        echo "❌ Error al compilar proyecto"
        exit 1
    }
    echo "✅ Proyecto compilado"
    echo ""
}

# Function to generate DDL export with automatic changeset
generate_ddl_export() {
    echo "📋 Generando DDL y changeset Liquibase desde entidades JPA..."
    echo ""
    
    check_prerequisites
    
    # Get next migration number
    MIGRATIONS_DIR="app/src/main/resources/db/changelog/migrations"
    SQL_DIR="$MIGRATIONS_DIR/sql"
    
    # Create SQL directory if it doesn't exist
    mkdir -p "$SQL_DIR"
    
    LAST_NUM=$(find "$MIGRATIONS_DIR" -name "*.yaml" -type f | sort | tail -1 | sed 's/.*\///;s/-.*//' | sed 's/^0*//' || echo "0")
    NEXT_NUM=$(printf "%03d" $((LAST_NUM + 1)))
    
    TIMESTAMP=$(date +%Y%m%d-%H%M%S)
    
    # SQL file path (relative to migrations directory)
    SQL_FILENAME="${NEXT_NUM}-hibernate-ddl-${TIMESTAMP}.sql"
    SQL_FILE="$SQL_DIR/$SQL_FILENAME"
    
    # YAML changeset file
    MIGRATION_FILE="$MIGRATIONS_DIR/${NEXT_NUM}-from-hibernate-${TIMESTAMP}.yaml"
    
    echo "📝 Generando DDL desde Hibernate..."
    
    # Create temporary application.properties for DDL generation
    TMP_PROPS=$(mktemp)
    cat > "$TMP_PROPS" << EOF
spring.jpa.properties.jakarta.persistence.schema-generation.scripts.action=create
spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-target=$SQL_FILE
spring.jpa.properties.hibernate.hbm2ddl.delimiter=;
spring.datasource.url=jdbc:postgresql://localhost:5432/rosafiesta
spring.datasource.username=rosafiesta_user
spring.datasource.password=rosafiesta_password
EOF
    
    # Run app in schema generation mode
    echo "🚀 Ejecutando generación de schema..."
    SPRING_CONFIG_LOCATION="file:$TMP_PROPS" ./gradlew :app:bootRun --args='--spring.jpa.hibernate.ddl-auto=none' > /dev/null 2>&1 &
    APP_PID=$!
    
    # Wait for schema generation
    sleep 5
    kill $APP_PID 2>/dev/null || true
    rm "$TMP_PROPS"
    
    if [ -f "$SQL_FILE" ]; then
        echo "✅ DDL generado exitosamente!"
        echo ""
        
        # Generate smart rollback
        echo "🤖 Generando rollback inteligente..."
        ROLLBACK_SQL=$(generate_smart_rollback "$SQL_FILE")
        
        if [ -z "$ROLLBACK_SQL" ]; then
            ROLLBACK_SQL="              -- TODO: Add rollback statements\n              -- Review the generated SQL and write the inverse operations"
        fi
        
        # Create YAML changeset that uses sqlFile
        cat > "$MIGRATION_FILE" << EOF
databaseChangeLog:
  - changeSet:
      id: ${NEXT_NUM}-from-hibernate-${TIMESTAMP}
      author: $(git config user.name || echo 'hibernate-generator')
      comment: Auto-generated from Hibernate entities - REVIEW ROLLBACK BEFORE APPLYING!
      sqlFile:
        path: sql/${SQL_FILENAME}
        relativeToChangelogFile: true
        splitStatements: true
        endDelimiter: ";"
      rollback:
        # ⚠️ AUTO-GENERATED ROLLBACK - REVIEW BEFORE APPLYING!
        - sql:
            sql: |
$ROLLBACK_SQL
EOF
        
        echo "✅ Changeset YAML creado con rollback automático!"
        echo ""
        echo "📄 Archivos generados:"
        echo "   SQL:  $SQL_FILE"
        echo "   YAML: $MIGRATION_FILE"
        echo ""
        echo "✨ Lo que se generó automáticamente:"
        echo "   ✅ DDL forward desde Hibernate"
        echo "   ✅ Changeset YAML con sqlFile"
        echo "   ✅ Rollback inteligente (generado automáticamente)"
        echo ""
        echo "🔧 Próximos pasos:"
        echo ""
        echo "1️⃣  Revisa el SQL generado:"
        echo "   cat $SQL_FILE"
        echo ""
        echo "2️⃣  IMPORTANTE: Revisa el rollback auto-generado:"
        echo "   vim $MIGRATION_FILE"
        echo "   (El rollback fue generado pero debes verificarlo)"
        echo ""
        echo "3️⃣  Registra en master changelog:"
        echo "   make lb-register FILE=${NEXT_NUM}-from-hibernate-${TIMESTAMP}"
        echo ""
        echo "4️⃣  Valida y prueba:"
        echo "   make lb-validate && make lb-test"
        echo ""
        echo "💡 Ventajas de este enfoque:"
        echo "   ✅ SQL directo (Hibernate → Liquibase sin conversión manual)"
        echo "   ✅ Changeset ya creado automáticamente"
        echo "   ✅ Rollback generado inteligentemente"
        echo "   ✅ 90% automático - solo revisas y ajustas"
    else
        echo "⚠️  No se generó archivo DDL."
        echo "   Intenta correr la app manualmente con ddl-auto=create"
    fi
}

# Function to generate smart rollback from DDL
generate_smart_rollback() {
    SQL_FILE="$1"
    
    if [ ! -f "$SQL_FILE" ]; then
        echo "-- TODO: Add rollback statements"
        return
    fi
    
    # Read SQL file and generate inverse operations
    while IFS= read -r line; do
        # Skip empty lines and comments
        if [[ -z "$line" || "$line" =~ ^[[:space:]]*-- ]]; then
            continue
        fi
        
        # ADD COLUMN -> DROP COLUMN
        if [[ "$line" =~ ALTER[[:space:]]+TABLE[[:space:]]+([^[:space:]]+)[[:space:]]+ADD[[:space:]]+COLUMN[[:space:]]+([^[:space:]]+) ]]; then
            table="${BASH_REMATCH[1]}"
            column="${BASH_REMATCH[2]}"
            echo "        ALTER TABLE $table DROP COLUMN $column;"
        
        # CREATE TABLE -> DROP TABLE
        elif [[ "$line" =~ CREATE[[:space:]]+TABLE[[:space:]]+([^[:space:]]+) ]]; then
            table="${BASH_REMATCH[1]}"
            echo "        DROP TABLE $table CASCADE;"
        
        # CREATE INDEX -> DROP INDEX
        elif [[ "$line" =~ CREATE[[:space:]]+INDEX[[:space:]]+([^[:space:]]+) ]]; then
            index="${BASH_REMATCH[1]}"
            echo "        DROP INDEX $index;"
        
        # ADD CONSTRAINT -> DROP CONSTRAINT
        elif [[ "$line" =~ ADD[[:space:]]+CONSTRAINT[[:space:]]+([^[:space:]]+) ]]; then
            constraint="${BASH_REMATCH[1]}"
            # Extract table name from previous context if possible
            echo "        -- DROP CONSTRAINT $constraint; (specify table)"
        fi
    done < "$SQL_FILE"
}

# Function to show diff hint
generate_diff_hint() {
    echo "🔍 Generación de Diff (Semi-Manual)"
    echo ""
    
    check_prerequisites
    
    # Get next migration number
    MIGRATIONS_DIR="app/src/main/resources/db/changelog/migrations"
    LAST_NUM=$(find "$MIGRATIONS_DIR" -name "*.yaml" -type f | sort | tail -1 | sed 's/.*\///;s/-.*//' | sed 's/^0*//' || echo "0")
    NEXT_NUM=$(printf "%03d" $((LAST_NUM + 1)))
    
    echo "📝 Para generar diff entre BD y entidades:"
    echo ""
    echo "Opción A: Usar spring.jpa.hibernate.ddl-auto=validate"
    echo "────────────────────────────────────────────────────────"
    echo "1. Corre tu app con perfil local:"
    echo "   make run-local"
    echo ""
    echo "2. Si hay diferencias, Hibernate mostrará error al inicio"
    echo "   El error describe qué falta o qué sobra"
    echo ""
    echo "3. Crea migración basándote en el error:"
    echo "   make lb-create NAME=fix-schema-diff"
    echo ""
    echo ""
    echo "Opción B: Comparar manualmente"
    echo "────────────────────────────────────────────────────────"
    echo "1. Exporta schema actual de BD:"
    echo "   docker exec rosafiesta-postgres pg_dump -U rosafiesta_user \\"
    echo "     -d rosafiesta --schema-only > current-schema.sql"
    echo ""
    echo "2. Genera DDL desde entidades:"
    echo "   $0 export"
    echo ""
    echo "3. Compara ambos archivos:"
    echo "   diff current-schema.sql ${NEXT_NUM}-hibernate-ddl-*.sql"
    echo ""
    echo "4. Crea migración con las diferencias:"
    echo "   make lb-create NAME=sync-entities-with-db"
    echo ""
    echo ""
    echo "Opción C: Usar herramienta externa (recomendado para diffs grandes)"
    echo "────────────────────────────────────────────────────────────────────"
    echo "Puedes usar herramientas como:"
    echo "  - pgAdmin (visual diff)"
    echo "  - DBeaver (compare schemas)"
    echo "  - Liquibase CLI con extensión Hibernate (requiere setup adicional)"
    echo ""
}

# Main script logic
case "${1:-help}" in
    export)
        generate_ddl_export
        ;;
    diff)
        generate_diff_hint
        ;;
    help|"")
        show_usage
        ;;
    *)
        echo "❌ Opción desconocida: $1"
        echo ""
        show_usage
        exit 1
        ;;
esac