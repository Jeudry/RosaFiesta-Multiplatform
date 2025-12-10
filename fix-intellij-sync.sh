#!/bin/bash

# Script para forzar la sincronización de IntelliJ con el proyecto Gradle

echo "=== Deteniendo todos los daemons de Gradle ==="
./gradlew --stop

echo ""
echo "=== Limpiando cachés de build ==="
rm -rf .gradle
rm -rf build
rm -rf */build
rm -rf */*/build

echo ""
echo "=== Eliminando archivos de configuración de IntelliJ ==="
rm -rf .idea
rm -f *.iml
rm -f */*.iml
rm -f */*/*.iml

echo ""
echo "=== Rebuilding user:api module ==="
./gradlew :user:domain:build --no-build-cache
./gradlew :user:infra:build --no-build-cache  
./gradlew :user:service:build --no-build-cache
./gradlew :user:api:build --no-build-cache

echo ""
echo "=== Generando configuración de IntelliJ ==="
./gradlew idea

echo ""
echo "===================================================================="
echo "IMPORTANTE: Ahora debes hacer lo siguiente en IntelliJ:"
echo "1. File -> Invalidate Caches / Restart"
echo "2. Selecciona 'Invalidate and Restart'"
echo "3. Cuando IntelliJ se reinicie, ve a File -> Sync Project with Gradle Files"
echo "===================================================================="