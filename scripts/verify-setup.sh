#!/bin/bash

# Script de verificación de configuración local
# Verifica que todos los componentes estén correctamente configurados

set -e

echo "🔍 Verificando configuración de RosaFiesta..."
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar archivos de configuración
echo "📄 Verificando archivos de configuración..."

files=(
    "docker-compose.yml"
    "docker-compose.orb.yml"
    "app/src/main/resources/application.yml"
    "app/src/main/resources/application-local.yml"
    "app/src/main/resources/application-orb.yml"
    "app/src/main/resources/application-dev.yml"
)

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file - NOT FOUND"
        exit 1
    fi
done

echo ""

# Verificar Run Configurations
echo "🚀 Verificando Run Configurations de IntelliJ..."

configs=(
    ".idea/runConfigurations/RosaFiestaApi__LOCAL_.xml"
    ".idea/runConfigurations/RosaFiestaApi__ORB_.xml"
    ".idea/runConfigurations/RosaFiestaApi__DEV_.xml"
    ".idea/runConfigurations/RosaFiestaApi__PROD_.xml"
)

for config in "${configs[@]}"; do
    if [ -f "$config" ]; then
        echo -e "${GREEN}✓${NC} $(basename "$config" .xml)"
    else
        echo -e "${RED}✗${NC} $(basename "$config" .xml) - NOT FOUND"
        exit 1
    fi
done

echo ""

# Verificar Docker
echo "🐳 Verificando Docker..."

if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗${NC} Docker no está instalado"
    exit 1
fi

echo -e "${GREEN}✓${NC} Docker está instalado"

# Verificar si Docker está corriendo
if ! docker info &> /dev/null; then
    echo -e "${RED}✗${NC} Docker no está corriendo"
    exit 1
fi

echo -e "${GREEN}✓${NC} Docker está corriendo"

echo ""

# Verificar contenedores (si están corriendo)
echo "📦 Verificando contenedores..."

containers=(
    "rosafiesta-postgres"
    "rosafiesta-redis"
    "rosafiesta-rabbitmq"
)

running_containers=0

for container in "${containers[@]}"; do
    if docker ps --format '{{.Names}}' | grep -q "^${container}$"; then
        echo -e "${GREEN}✓${NC} $container está corriendo"
        ((running_containers++))
    else
        echo -e "${YELLOW}○${NC} $container no está corriendo"
    fi
done

if [ $running_containers -eq 0 ]; then
    echo ""
    echo -e "${YELLOW}ℹ${NC}  Ningún contenedor está corriendo. Inicia los servicios con:"
    echo "   docker-compose up -d             # Docker Compose tradicional"
    echo "   docker-compose -f docker-compose.orb.yml up -d  # Orb Stack"
fi

echo ""

# Verificar puertos
echo "🔌 Verificando puertos..."

ports=(
    "5432:PostgreSQL"
    "6379:Redis"
    "5672:RabbitMQ"
    "15672:RabbitMQ Management"
)

for port_info in "${ports[@]}"; do
    port="${port_info%%:*}"
    service="${port_info##*:}"
    
    if lsof -i ":$port" &> /dev/null; then
        echo -e "${GREEN}✓${NC} Puerto $port ($service) está en uso"
    else
        echo -e "${YELLOW}○${NC} Puerto $port ($service) está libre"
    fi
done

echo ""
echo "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" "=" 
echo -e "${GREEN}✅ Verificación completada${NC}"
echo ""
echo "Próximos pasos:"
echo "1. Inicia los servicios:"
echo "   ${GREEN}docker-compose up -d${NC}  # o usa Orb Stack"
echo ""
echo "2. Abre IntelliJ IDEA y selecciona una Run Configuration:"
echo "   - RosaFiestaApi [LOCAL] para Docker Compose"
echo "   - RosaFiestaApi [ORB] para Orb Stack"
echo ""
echo "3. Presiona Shift+F9 para debuggear"
echo ""
echo "📚 Consulta PERFILES.md para más información"