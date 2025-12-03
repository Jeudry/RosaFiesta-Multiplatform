#!/bin/bash

echo "🚀 Iniciando RosaFiesta API con perfil ORB"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "📋 Verificando servicios de Docker..."
docker ps --format "{{.Names}}: {{.Status}}" | grep rosafiesta

if [ $? -ne 0 ]; then
  echo ""
  echo "⚠️  Los servicios de Docker no están corriendo"
  echo "   Ejecuta: docker-compose -f docker-compose.orb.yml up -d"
  exit 1
fi

echo ""
echo "✅ Servicios de Docker están corriendo"
echo ""
echo "🔧 Configurando variables de entorno..."

cd /Users/sargon/Documents/Coding/KMP/RosaFiesta

# Load environment variables from .env.local if it exists
if [ -f "scripts/.env.local" ]; then
  export $(grep -v '^#' scripts/.env.local | xargs)
fi

# Check if required environment variables are set
if [ -z "$MAIL_FROM_EMAIL" ] || [ -z "$MAIL_PASSWORD" ]; then
  echo "❌ Error: Missing required environment variables"
  echo ""
  echo "Please set the following environment variables:"
  echo "  export MAIL_FROM_EMAIL='your-email@gmail.com'"
  echo "  export MAIL_PASSWORD='your-app-password'"
  echo ""
  echo "Or create a file: scripts/.env.local with:"
  echo "  MAIL_FROM_EMAIL=your-email@gmail.com"
  echo "  MAIL_PASSWORD=your-app-password"
  exit 1
fi

export SPRING_PROFILES_ACTIVE=orb

echo "   - SPRING_PROFILES_ACTIVE=orb"
echo "   - MAIL_FROM_EMAIL=$MAIL_FROM_EMAIL"
echo "   - MAIL_PASSWORD=***"
echo ""
echo "ℹ️  Nota: Firebase está deshabilitado en el perfil ORB"
echo "   (Push notifications no funcionarán, pero todo lo demás sí)"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "🚀 Ejecutando aplicación..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

./gradlew :app:bootRun