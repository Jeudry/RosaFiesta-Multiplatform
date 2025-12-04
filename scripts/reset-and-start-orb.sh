#!/bin/bash
set -e

echo "🔥 REINICIO COMPLETO - Eliminando TODO y empezando desde cero"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

cd /Users/sargon/Documents/Coding/KMP/RosaFiesta

# 1. Detener TODO
echo "🛑 Deteniendo contenedores..."
docker-compose -f docker-compose.orb.yml down -v 2>/dev/null || true

# 2. Eliminar volúmenes específicos por nombre
echo "🗑️  Eliminando volúmenes antiguos..."
docker volume rm rosafiesta_postgres_data 2>/dev/null || true
docker volume rm rosafiesta_redis_data 2>/dev/null || true
docker volume rm rosafiesta_rabbitmq_data 2>/dev/null || true

# 3. Eliminar cualquier volumen huérfano
docker volume prune -f 2>/dev/null || true

# 4. Esperar un momento
sleep 2

# 5. Iniciar servicios FRESCOS
echo "🚀 Iniciando servicios desde cero..."
docker-compose -f docker-compose.orb.yml up -d

# 6. Esperar a que Postgres se inicialice completamente
echo "⏳ Esperando 25 segundos para inicialización completa..."
sleep 25

# 7. Verificar estado
echo ""
echo "📊 Estado de los contenedores:"
docker-compose -f docker-compose.orb.yml ps

# 8. Ver logs de Postgres
echo ""
echo "📝 Últimas líneas de logs de Postgres:"
docker logs rosafiesta-postgres 2>&1 | tail -15

# 9. Probar conexión CON contraseña
echo ""
echo "🧪 Probando conexión con contraseña 'postgres'..."
PGPASSWORD='postgres' psql -h postgres.rosafiesta.orb.local -p 5432 -U postgres -d rosafiesta -c "
SELECT 
  '✅ Conexión exitosa!' as status, 
  current_database() as database, 
  current_user as usuario,
  version() as version;
" 2>&1

if [ $? -eq 0 ]; then
  echo ""
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  echo "✅ ¡ÉXITO! Todos los servicios están listos"
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  echo ""
  echo "📋 Configuración:"
  echo "   PostgreSQL:"
  echo "     - Host: postgres.rosafiesta.orb.local:5432"
  echo "     - Usuario: postgres"
  echo "     - Contraseña: postgres"
  echo "     - Base de datos: rosafiesta"
  echo ""
  echo "   Redis:"
  echo "     - Host: redis.rosafiesta.orb.local:6379"
  echo "     - Contraseña: rosafiesta_redis_password"
  echo ""
  echo "   RabbitMQ:"
  echo "     - Host: rabbitmq.rosafiesta.orb.local:5672"
  echo "     - Usuario: rosafiesta_user"
  echo "     - Contraseña: rosafiesta_password"
  echo "     - Management UI: http://localhost:15672"
  echo ""
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  echo ""
echo "🎯 Puedes ejecutar tu aplicación con:"
echo ""
echo "   Opción 1 - Desde terminal:"
echo "   -------------------------"
echo "   export MAIL_FROM_EMAIL='your-email@gmail.com'"
echo "   export MAIL_PASSWORD='your-app-password'"
echo "   ./scripts/run-orb-app.sh"
  echo ""
  echo "   Opción 2 - Desde IntelliJ:"
  echo "   --------------------------"
  echo "   Run Configuration: 'RosaFiestaApi [ORB]'"
  echo ""
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  
  exit 0
else
  echo ""
  echo "❌ La conexión falló"
  echo ""
  echo "Revisa los logs completos:"
  echo "  docker logs rosafiesta-postgres"
  
  exit 1
fi