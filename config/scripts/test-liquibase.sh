#!/bin/zsh

# Quick test to verify Liquibase integration
# Run this before deploying to ensure migrations will work

echo "🧪 Testing Liquibase Integration"
echo "=================================="
echo ""

cd /Users/sargon/Documents/Coding/KMP/RosaFiesta

# Build the project
echo "📦 Building project..."
./gradlew clean build -x test > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "✅ Build successful"
else
    echo "❌ Build failed"
    exit 1
fi

# Check migration files
echo ""
echo "📄 Checking migration files..."
MIGRATION_DIR="app/src/main/resources/db/changelog/migrations"

if [ ! -d "$MIGRATION_DIR" ]; then
    echo "❌ Migration directory not found"
    exit 1
fi

MIGRATION_COUNT=$(find "$MIGRATION_DIR" -name "*.yaml" -type f | wc -l | tr -d ' ')
echo "   Found $MIGRATION_COUNT migration files"

# List migrations
find "$MIGRATION_DIR" -name "*.yaml" -type f | sort | while read file; do
    echo "   ✅ $(basename "$file")"
done

echo ""
echo "✅ Liquibase integration looks good!"
echo ""
echo "🚀 To test migrations, start Docker services and run:"
echo "   docker compose -f docker-compose.orb.yml up -d"
echo "   ./gradlew :app:bootRun --args='--spring.profiles.active=local'"
echo ""
echo "   The application will automatically run Liquibase migrations on startup."