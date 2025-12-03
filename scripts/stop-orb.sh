#!/bin/bash

echo "🛑 Deteniendo servicios de Orb Stack para RosaFiesta..."

docker-compose -f docker-compose.orb.yml down

echo "✅ Servicios detenidos"
echo ""
echo "💡 Para eliminar también los datos, usa:"
echo "   docker-compose -f docker-compose.orb.yml down -v"