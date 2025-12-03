#!/bin/zsh

# Quick test of Makefile Liquibase commands
# This script verifies that the Makefile commands work correctly

echo "🧪 Testing Makefile Liquibase Commands"
echo "======================================"
echo ""

cd /Users/sargon/Documents/Coding/KMP/RosaFiesta

# Test 1: liquibase-verify
echo "1️⃣  Testing: make liquibase-verify"
make liquibase-verify
echo ""

# Test 2: liquibase-list
echo "2️⃣  Testing: make liquibase-list"
make liquibase-list
echo ""

# Test 3: liquibase-validate
echo "3️⃣  Testing: make liquibase-validate"
make liquibase-validate
echo ""

# Test 4: liquibase-help
echo "4️⃣  Testing: make liquibase-help"
make liquibase-help
echo ""

echo "✅ All tests completed!"
echo ""
echo "🎯 To test creating a migration (won't actually create):"
echo "   make liquibase-create NAME=test-migration"