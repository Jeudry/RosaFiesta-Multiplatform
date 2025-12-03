#!/bin/zsh

# Liquibase Helper Script
# Provides convenient commands for working with Liquibase migrations

set -e

PROJECT_ROOT="/Users/sargon/Documents/Coding/KMP/RosaFiesta"
cd "$PROJECT_ROOT"

MIGRATIONS_DIR="$PROJECT_ROOT/app/src/main/resources/db/changelog/migrations"

show_help() {
    echo "Liquibase Helper Script"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  list              List all migration files"
    echo "  create <name>     Create a new migration file"
    echo "  validate          Validate migration files"
    echo "  status <profile>  Show migration status (local|dev|orb|prod)"
    echo "  help              Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 list"
    echo "  $0 create add-user-profile-fields"
    echo "  $0 validate"
    echo "  $0 status local"
}

list_migrations() {
    echo "📄 Migration files:"
    echo ""
    find "$MIGRATIONS_DIR" -name "*.yaml" -type f | sort | while read file; do
        filename=$(basename "$file")
        echo "   $filename"
    done
}

create_migration() {
    if [ -z "$1" ]; then
        echo "❌ Error: Migration name is required"
        echo "Usage: $0 create <name>"
        exit 1
    fi

    # Get next migration number
    LAST_NUM=$(find "$MIGRATIONS_DIR" -name "*.yaml" -type f | sort | tail -1 | grep -o '^[0-9]*' | head -1)
    if [ -z "$LAST_NUM" ]; then
        NEXT_NUM="001"
    else
        NEXT_NUM=$(printf "%03d" $((10#$LAST_NUM + 1)))
    fi

    MIGRATION_NAME="$1"
    FILENAME="${NEXT_NUM}-${MIGRATION_NAME}.yaml"
    FILEPATH="$MIGRATIONS_DIR/$FILENAME"

    if [ -f "$FILEPATH" ]; then
        echo "❌ Error: Migration file already exists: $FILENAME"
        exit 1
    fi

    # Create migration template
    cat > "$FILEPATH" << EOF
databaseChangeLog:
  - changeSet:
      id: ${NEXT_NUM}-${MIGRATION_NAME}
      author: rosafiesta-team
      comment: TODO - Add description
      changes:
        # Add your changes here
        # Example:
        # - addColumn:
        #     tableName: users
        #     schemaName: user_service
        #     columns:
        #       - column:
        #           name: new_field
        #           type: VARCHAR(255)
      rollback:
        # Add rollback logic here
        # Example:
        # - dropColumn:
        #     tableName: users
        #     schemaName: user_service
        #     columnName: new_field
EOF

    echo "✅ Created migration file: $FILENAME"
    echo ""
    echo "📝 Next steps:"
    echo "   1. Edit the file: $FILEPATH"
    echo "   2. Add it to db.changelog-master.yaml"
    echo "   3. Test with: ./gradlew :app:bootRun --args='--spring.profiles.active=local'"
}

validate_migrations() {
    echo "🔍 Validating migration files..."
    echo ""
    
    # Check if Python is available
    if ! command -v python3 &> /dev/null; then
        echo "⚠️  Python3 not found, skipping YAML validation"
        return
    fi

    find "$MIGRATIONS_DIR" -name "*.yaml" -type f | sort | while read file; do
        filename=$(basename "$file")
        if python3 -c "import yaml; yaml.safe_load(open('$file', 'r'))" 2>/dev/null; then
            echo "   ✅ $filename"
        else
            echo "   ❌ $filename (invalid YAML)"
        fi
    done
}

show_status() {
    PROFILE=${1:-local}
    
    echo "📊 Migration status for profile: $PROFILE"
    echo ""
    echo "Starting application to check migration status..."
    echo "(This will connect to the database and show which migrations have been applied)"
    echo ""
    
    # This would require running the app, which we won't do automatically
    echo "Run manually:"
    echo "  ./gradlew :app:bootRun --args='--spring.profiles.active=$PROFILE'"
    echo ""
    echo "Then check the logs for Liquibase execution messages."
}

# Main command dispatcher
case "$1" in
    list)
        list_migrations
        ;;
    create)
        create_migration "$2"
        ;;
    validate)
        validate_migrations
        ;;
    status)
        show_status "$2"
        ;;
    help|"")
        show_help
        ;;
    *)
        echo "❌ Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac