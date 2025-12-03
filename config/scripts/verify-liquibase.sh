#!/bin/zsh

# Liquibase Verification Script
# This script validates that Liquibase migrations are properly configured

set -e

echo "🔍 Liquibase Configuration Verification"
echo "========================================"
echo ""

PROJECT_ROOT="/Users/sargon/Documents/Coding/KMP/RosaFiesta"
CHANGELOG_DIR="$PROJECT_ROOT/app/src/main/resources/db/changelog"

# Check if changelog directory exists
if [ ! -d "$CHANGELOG_DIR" ]; then
    echo "❌ Changelog directory not found: $CHANGELOG_DIR"
    exit 1
fi
echo "✅ Changelog directory exists"

# Check if master changelog exists
MASTER_CHANGELOG="$CHANGELOG_DIR/db.changelog-master.yaml"
if [ ! -f "$MASTER_CHANGELOG" ]; then
    echo "❌ Master changelog not found: $MASTER_CHANGELOG"
    exit 1
fi
echo "✅ Master changelog exists"

# List all migration files
echo ""
echo "📄 Migration files:"
find "$CHANGELOG_DIR/migrations" -name "*.yaml" -type f | sort | while read file; do
    filename=$(basename "$file")
    echo "   - $filename"
done

# Check application configurations
echo ""
echo "🔧 Application configurations:"
RESOURCES_DIR="$PROJECT_ROOT/app/src/main/resources"

for profile in application application-local application-dev application-orb application-prod; do
    config_file="$RESOURCES_DIR/${profile}.yml"
    if [ -f "$config_file" ]; then
        if grep -q "liquibase:" "$config_file"; then
            enabled=$(grep -A 1 "liquibase:" "$config_file" | grep "enabled:" | awk '{print $2}')
            if [ "$profile" = "application" ]; then
                echo "   ✅ $profile: Liquibase configured (enabled: $enabled)"
            else
                echo "   ✅ $profile: Liquibase configured (enabled: $enabled)"
            fi
        else
            echo "   ⚠️  $profile: Liquibase not configured"
        fi
    fi
done

# Check Liquibase dependency
echo ""
echo "📦 Dependencies:"
if grep -q "liquibase-core" "$PROJECT_ROOT/app/build.gradle.kts"; then
    echo "   ✅ Liquibase dependency found in app/build.gradle.kts"
else
    echo "   ❌ Liquibase dependency NOT found in app/build.gradle.kts"
    exit 1
fi

echo ""
echo "✅ All Liquibase configuration checks passed!"
echo ""
echo "📝 To run migrations:"
echo "   Local:  ./gradlew :app:bootRun --args='--spring.profiles.active=local'"
echo "   Orb:    ./gradlew :app:bootRun --args='--spring.profiles.active=orb'"
echo "   Dev:    ./gradlew :app:bootRun --args='--spring.profiles.active=dev'"
echo "   Prod:   ./gradlew :app:bootRun --args='--spring.profiles.active=prod'"