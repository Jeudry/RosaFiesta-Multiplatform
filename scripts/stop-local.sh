#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${YELLOW}🛑 Stopping RosaFiesta Local Development Environment${NC}"
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}❌ Docker is not running.${NC}"
    exit 1
fi

# Detect if using Orb Stack
if docker info 2>/dev/null | grep -q "orbstack"; then
    echo -e "${BLUE}🔮 Detected Orb Stack${NC}"
    COMPOSE_FILE="docker-compose.orb.yml"
else
    echo -e "${BLUE}🐳 Using Docker Desktop${NC}"
    COMPOSE_FILE="docker-compose.yml"
fi
echo ""

# Ask if user wants to remove volumes
echo -e "${YELLOW}Do you want to remove volumes (all data will be deleted)? [y/N]${NC}"
read -r response

if [[ "$response" =~ ^([yY][eE][sS]|[yY])$ ]]; then
    echo -e "${YELLOW}📦 Stopping services and removing volumes...${NC}"
    docker-compose -f "$COMPOSE_FILE" down -v
    echo -e "${GREEN}✅ Services stopped and volumes removed${NC}"
else
    echo -e "${YELLOW}📦 Stopping services (keeping volumes)...${NC}"
    docker-compose -f "$COMPOSE_FILE" down
    echo -e "${GREEN}✅ Services stopped (data preserved)${NC}"
fi

echo ""