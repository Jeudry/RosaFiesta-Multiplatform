.PHONY: help local-up local-down local-logs local-restart orb-up orb-down orb-logs run-local run-orb run-prod build test clean docker-clean verify

help: ## Muestra esta ayuda
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

# Docker Compose Tradicional
local-up: ## Inicia los servicios locales con Docker Compose
	@./start-local.sh

local-down: ## Detiene los servicios locales
	@./stop-local.sh

local-logs: ## Muestra los logs de los servicios locales
	@docker-compose logs -f

local-restart: ## Reinicia los servicios locales
	@docker-compose restart

# Orb Stack (Recomendado para macOS)
orb-up: ## Inicia los servicios locales con Orb Stack
	@echo "🚀 Iniciando servicios con Orb Stack..."
	@docker-compose -f docker-compose.orb.yml up -d
	@echo "✅ Servicios iniciados con Orb Stack"

orb-down: ## Detiene los servicios de Orb Stack
	@docker-compose -f docker-compose.orb.yml down

orb-logs: ## Muestra los logs de Orb Stack
	@docker-compose -f docker-compose.orb.yml logs -f

orb-restart: ## Reinicia los servicios de Orb Stack
	@docker-compose -f docker-compose.orb.yml restart

# Ejecutar aplicación
run-local: ## Ejecuta la aplicación con perfil local (Docker Compose)
	@./gradlew :app:bootRun --args='--spring.profiles.active=local'

run-orb: ## Ejecuta la aplicación con perfil orb (Orb Stack)
	@./gradlew :app:bootRun --args='--spring.profiles.active=orb'

run-prod: ## Ejecuta la aplicación con perfil de producción
	@./gradlew :app:bootRun

build: ## Compila el proyecto
	@./gradlew build

test: ## Ejecuta los tests
	@./gradlew test

clean: ## Limpia los archivos de compilación
	@./gradlew clean

docker-clean: ## Detiene servicios y elimina volúmenes (BORRA DATOS)
	@docker-compose down -v

db-shell: ## Abre shell de PostgreSQL
	@docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta

redis-shell: ## Abre shell de Redis
	@docker exec -it rosafiesta-redis redis-cli -a rosafiesta_redis_password

docker-ps: ## Muestra el estado de los contenedores
	@docker-compose ps

status: ## Verifica el estado de todos los servicios
	@./check-status.sh

verify: ## Verifica la configuración del proyecto
	@./verify-setup.sh

# Liquibase - Database Migrations
MIGRATIONS_DIR := app/src/main/resources/db/changelog/migrations
MASTER_CHANGELOG := app/src/main/resources/db/changelog/db.changelog-master.yaml

liquibase-list: ## Lista todas las migraciones
	@echo "📄 Migraciones disponibles:"
	@find $(MIGRATIONS_DIR) -name "*.yaml" -type f | sort | sed 's|.*/||' | nl

liquibase-create: ## Crea una nueva migración (uso: make liquibase-create NAME=add-user-field)
	@if [ -z "$(NAME)" ]; then \
		echo "❌ Error: Debes proporcionar un nombre"; \
		echo "Uso: make liquibase-create NAME=add-user-field"; \
		exit 1; \
	fi
	@LAST_NUM=$$(find $(MIGRATIONS_DIR) -name "*.yaml" -type f | sort | tail -1 | sed 's/.*\///;s/-.*//' | sed 's/^0*//' || echo "0"); \
	NEXT_NUM=$$(printf "%03d" $$((LAST_NUM + 1))); \
	FILENAME="$$NEXT_NUM-$(NAME).yaml"; \
	FILEPATH="$(MIGRATIONS_DIR)/$$FILENAME"; \
	if [ -f "$$FILEPATH" ]; then \
		echo "❌ Error: El archivo ya existe: $$FILENAME"; \
		exit 1; \
	fi; \
	echo "databaseChangeLog:" > "$$FILEPATH"; \
	echo "  - changeSet:" >> "$$FILEPATH"; \
	echo "      id: $$NEXT_NUM-$(NAME)" >> "$$FILEPATH"; \
	echo "      author: $$(git config user.name || echo 'rosafiesta-team')" >> "$$FILEPATH"; \
	echo "      comment: TODO - Add description" >> "$$FILEPATH"; \
	echo "      changes:" >> "$$FILEPATH"; \
	echo "        # Add your changes here" >> "$$FILEPATH"; \
	echo "        # Examples:" >> "$$FILEPATH"; \
	echo "        # - addColumn:" >> "$$FILEPATH"; \
	echo "        #     tableName: users" >> "$$FILEPATH"; \
	echo "        #     schemaName: user_service" >> "$$FILEPATH"; \
	echo "        #     columns:" >> "$$FILEPATH"; \
	echo "        #       - column:" >> "$$FILEPATH"; \
	echo "        #           name: new_field" >> "$$FILEPATH"; \
	echo "        #           type: VARCHAR(255)" >> "$$FILEPATH"; \
	echo "      rollback:" >> "$$FILEPATH"; \
	echo "        # Add rollback logic here" >> "$$FILEPATH"; \
	echo "        # - dropColumn:" >> "$$FILEPATH"; \
	echo "        #     tableName: users" >> "$$FILEPATH"; \
	echo "        #     schemaName: user_service" >> "$$FILEPATH"; \
	echo "        #     columnName: new_field" >> "$$FILEPATH"; \
	echo "✅ Migración creada: $$FILENAME"; \
	echo "📝 Editando automáticamente el master changelog..."; \
	echo "  - include:" >> "$(MASTER_CHANGELOG)"; \
	echo "      file: db/changelog/migrations/$$FILENAME" >> "$(MASTER_CHANGELOG)"; \
	echo "✅ Registrada en $(MASTER_CHANGELOG)"; \
	echo ""; \
	echo "📂 Ubicación: $$FILEPATH"; \
	echo ""; \
	echo "🔧 Próximos pasos:"; \
	echo "   1. Edita el archivo de migración"; \
	echo "   2. Ejecuta: make liquibase-validate"; \
	echo "   3. Prueba con: make liquibase-test"

liquibase-validate: ## Valida la sintaxis de los archivos de migración
	@echo "🔍 Validando archivos de migración..."
	@VALID=true; \
	for file in $(MIGRATIONS_DIR)/*.yaml; do \
		if [ -f "$$file" ]; then \
			filename=$$(basename "$$file"); \
			if python3 -c "import yaml; yaml.safe_load(open('$$file', 'r'))" 2>/dev/null; then \
				echo "   ✅ $$filename"; \
			else \
				echo "   ❌ $$filename (YAML inválido)"; \
				VALID=false; \
			fi; \
		fi; \
	done; \
	if [ "$$VALID" = "true" ]; then \
		echo ""; \
		echo "✅ Todas las migraciones son válidas"; \
	else \
		echo ""; \
		echo "❌ Algunos archivos tienen errores"; \
		exit 1; \
	fi

liquibase-test: ## Prueba las migraciones en ambiente local
	@echo "🧪 Probando migraciones en ambiente local..."
	@echo "📦 Compilando proyecto..."
	@./gradlew build -x test -q
	@echo "🐳 Verificando servicios Docker..."
	@docker-compose -f docker-compose.orb.yml ps | grep -q "Up" || (echo "⚠️  Servicios no están corriendo. Iniciando..."; docker-compose -f docker-compose.orb.yml up -d; sleep 5)
	@echo "🚀 Ejecutando aplicación con Liquibase..."
	@echo "   (Presiona Ctrl+C para detener después de ver los logs de Liquibase)"
	@./gradlew :app:bootRun --args='--spring.profiles.active=local' 2>&1 | grep -A 20 "Liquibase" || true

liquibase-status: ## Muestra el estado de las migraciones en la BD
	@echo "📊 Estado de las migraciones en la base de datos:"
	@docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
		SELECT id, author, filename, dateexecuted, orderexecuted \
		FROM databasechangelog \
		ORDER BY orderexecuted DESC \
		LIMIT 10;" 2>/dev/null || echo "⚠️  No se pudo conectar a la BD o las tablas de Liquibase no existen"

liquibase-diff: ## Genera migración automáticamente comparando BD vs entidades JPA
	@echo "🔄 Generación automática de migración desde entidades JPA"
	@echo ""
	@echo "Este comando:"
	@echo "  1. Compara la BD actual con tus entidades JPA"
	@echo "  2. Genera un changeset con las diferencias"
	@echo "  3. Crea archivo listo para revisar y ajustar"
	@echo ""
	@./config/scripts/liquibase-generate.sh diff

liquibase-generate-schema: ## Genera schema completo desde entidades JPA (SQL directo + rollback auto)
	@echo "📋 Generación automática desde entidades JPA"
	@echo ""
	@echo "Este comando:"
	@echo "  1. Lee todas tus entidades JPA"
	@echo "  2. Genera SQL directo desde Hibernate"
	@echo "  3. Crea changeset YAML automáticamente"
	@echo "  4. Genera rollback inteligente"
	@echo ""
	@./config/scripts/liquibase-generate.sh export

liquibase-register-manual: ## Registra manualmente un archivo de migración en master (uso: make liquibase-register-manual FILE=005-my-file)
	@if [ -z "$(FILE)" ]; then \
		echo "❌ Error: Debes especificar el archivo"; \
		echo "Uso: make liquibase-register-manual FILE=005-my-migration"; \
		echo ""; \
		echo "Archivos disponibles:"; \
		find $(MIGRATIONS_DIR) -name "*.yaml" -type f | sed 's|.*/||' | nl; \
		exit 1; \
	fi
	@MIGRATION_FILE="$(MIGRATIONS_DIR)/$(FILE).yaml"; \
	if [ ! -f "$$MIGRATION_FILE" ]; then \
		echo "❌ Archivo no encontrado: $$MIGRATION_FILE"; \
		echo ""; \
		echo "Archivos disponibles:"; \
		find $(MIGRATIONS_DIR) -name "*.yaml" -type f | sed 's|.*/||' | nl; \
		exit 1; \
	fi; \
	if grep -q "$(FILE).yaml" "$(MASTER_CHANGELOG)"; then \
		echo "⚠️  El archivo ya está registrado en master changelog"; \
		exit 1; \
	fi; \
	echo "  - include:" >> "$(MASTER_CHANGELOG)"; \
	echo "      file: db/changelog/migrations/$(FILE).yaml" >> "$(MASTER_CHANGELOG)"; \
	echo "✅ Archivo registrado en master changelog"; \
	echo "📄 $(FILE).yaml"

liquibase-rollback-one: ## Deshace la última migración (⚠️  CUIDADO)
	@echo "⚠️  ADVERTENCIA: Esto deshará la última migración aplicada"
	@echo ""
	@echo "📊 Migraciones actualmente aplicadas:"
	@docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
		SELECT id, author, dateexecuted \
		FROM databasechangelog \
		ORDER BY orderexecuted DESC \
		LIMIT 3;" 2>/dev/null || echo "⚠️  No se pudo conectar a la BD"
	@echo ""
	@read -p "¿Deshacer la última migración? (escribe 'yes' para confirmar): " confirm; \
	if [ "$$confirm" = "yes" ]; then \
		echo "🔄 Ejecutando rollback..."; \
		docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
			DELETE FROM databasechangelog \
			WHERE orderexecuted = (SELECT MAX(orderexecuted) FROM databasechangelog);" && \
		echo "✅ Rollback registrado. Ahora ejecuta el rollback SQL manualmente o usa:"; \
		echo "   make liquibase-rollback-sql"; \
	else \
		echo "❌ Operación cancelada"; \
	fi

liquibase-rollback-count: ## Deshace N migraciones (uso: make liquibase-rollback-count COUNT=2)
	@if [ -z "$(COUNT)" ]; then \
		echo "❌ Error: Debes especificar el número de migraciones"; \
		echo "Uso: make liquibase-rollback-count COUNT=2"; \
		exit 1; \
	fi
	@echo "⚠️  ADVERTENCIA: Esto deshará las últimas $(COUNT) migraciones"
	@echo ""
	@echo "📊 Migraciones que serán deshechas:"
	@docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
		SELECT id, author, dateexecuted \
		FROM databasechangelog \
		ORDER BY orderexecuted DESC \
		LIMIT $(COUNT);" 2>/dev/null || echo "⚠️  No se pudo conectar a la BD"
	@echo ""
	@read -p "¿Continuar con el rollback? (escribe 'yes' para confirmar): " confirm; \
	if [ "$$confirm" = "yes" ]; then \
		echo "🔄 Ejecutando rollback de $(COUNT) migraciones..."; \
		for i in $$(seq 1 $(COUNT)); do \
			docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
				DELETE FROM databasechangelog \
				WHERE orderexecuted = (SELECT MAX(orderexecuted) FROM databasechangelog);" || break; \
		done && \
		echo "✅ Rollback completado. Verifica con: make liquibase-status"; \
	else \
		echo "❌ Operación cancelada"; \
	fi

liquibase-rollback-to: ## Deshace hasta una migración específica (uso: make liquibase-rollback-to ID=003-create-chat)
	@if [ -z "$(ID)" ]; then \
		echo "❌ Error: Debes especificar el ID de la migración"; \
		echo "Uso: make liquibase-rollback-to ID=003-create-chat"; \
		echo ""; \
		echo "Migraciones disponibles:"; \
		make liquibase-status; \
		exit 1; \
	fi
	@echo "⚠️  ADVERTENCIA: Esto deshará todas las migraciones después de: $(ID)"
	@echo ""
	@echo "📊 Migraciones que serán deshechas:"
	@docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
		SELECT id, author, dateexecuted \
		FROM databasechangelog \
		WHERE orderexecuted > (SELECT orderexecuted FROM databasechangelog WHERE id = '$(ID)') \
		ORDER BY orderexecuted DESC;" 2>/dev/null || echo "⚠️  No se pudo conectar a la BD"
	@echo ""
	@read -p "¿Continuar con el rollback? (escribe 'yes' para confirmar): " confirm; \
	if [ "$$confirm" = "yes" ]; then \
		echo "🔄 Ejecutando rollback hasta $(ID)..."; \
		docker exec -it rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -c "\
			DELETE FROM databasechangelog \
			WHERE orderexecuted > (SELECT orderexecuted FROM databasechangelog WHERE id = '$(ID)');" && \
		echo "✅ Rollback completado. Verifica con: make liquibase-status"; \
	else \
		echo "❌ Operación cancelada"; \
	fi

liquibase-rollback-sql: ## Muestra el SQL de rollback para la última migración
	@echo "📜 SQL de rollback para la última migración:"
	@echo ""
	@LAST_MIGRATION=$$(docker exec rosafiesta-postgres psql -U rosafiesta_user -d rosafiesta -t -c "\
		SELECT filename FROM databasechangelog ORDER BY orderexecuted DESC LIMIT 1;" 2>/dev/null | xargs); \
	if [ -z "$$LAST_MIGRATION" ]; then \
		echo "❌ No se encontraron migraciones aplicadas"; \
		exit 1; \
	fi; \
	MIGRATION_FILE="app/src/main/resources/$$LAST_MIGRATION"; \
	if [ ! -f "$$MIGRATION_FILE" ]; then \
		echo "❌ Archivo no encontrado: $$MIGRATION_FILE"; \
		exit 1; \
	fi; \
	echo "📄 Archivo: $$MIGRATION_FILE"; \
	echo ""; \
	echo "Sección de rollback:"; \
	echo "-------------------"; \
	sed -n '/rollback:/,$$p' "$$MIGRATION_FILE"

liquibase-show-rollback: ## Muestra el rollback de una migración específica (uso: make liquibase-show-rollback FILE=005-add-user-phone)
	@if [ -z "$(FILE)" ]; then \
		echo "❌ Error: Debes especificar el archivo"; \
		echo "Uso: make liquibase-show-rollback FILE=005-add-user-phone"; \
		echo ""; \
		echo "Migraciones disponibles:"; \
		make liquibase-list; \
		exit 1; \
	fi
	@MIGRATION_FILE="$(MIGRATIONS_DIR)/$(FILE).yaml"; \
	if [ ! -f "$$MIGRATION_FILE" ]; then \
		echo "❌ Archivo no encontrado: $$MIGRATION_FILE"; \
		echo ""; \
		echo "Migraciones disponibles:"; \
		make liquibase-list; \
		exit 1; \
	fi; \
	echo "📄 Rollback de: $(FILE)"; \
	echo ""; \
	sed -n '/rollback:/,$$p' "$$MIGRATION_FILE"

liquibase-clear-checksums: ## Limpia los checksums (útil para desarrollo)
	@echo "🧹 Limpiando checksums de Liquibase..."
	@./gradlew :app:liquibaseClearChecksums

liquibase-reset-local: ## Reinicia completamente la BD local con migraciones limpias
	@echo "🔄 Reiniciando base de datos local..."
	@echo "⚠️  Esto eliminará TODOS los datos"
	@read -p "¿Estás seguro? (escribe 'yes' para confirmar): " confirm; \
	if [ "$$confirm" = "yes" ]; then \
		echo "🛑 Deteniendo servicios..."; \
		docker-compose -f docker-compose.orb.yml down -v; \
		echo "🚀 Iniciando servicios nuevos..."; \
		docker-compose -f docker-compose.orb.yml up -d; \
		echo "⏳ Esperando a que PostgreSQL esté listo..."; \
		sleep 5; \
		echo "✅ Listo. Ejecuta 'make run-local' para aplicar migraciones"; \
	else \
		echo "❌ Operación cancelada"; \
	fi

liquibase-verify: ## Verifica la configuración de Liquibase
	@echo "🔍 Verificando configuración de Liquibase..."
	@echo ""
	@echo "📁 Directorio de migraciones:"
	@if [ -d "$(MIGRATIONS_DIR)" ]; then \
		echo "   ✅ $(MIGRATIONS_DIR)"; \
	else \
		echo "   ❌ No existe: $(MIGRATIONS_DIR)"; \
	fi
	@echo ""
	@echo "📄 Master changelog:"
	@if [ -f "$(MASTER_CHANGELOG)" ]; then \
		echo "   ✅ $(MASTER_CHANGELOG)"; \
	else \
		echo "   ❌ No existe: $(MASTER_CHANGELOG)"; \
	fi
	@echo ""
	@echo "📦 Dependencia de Liquibase:"
	@if grep -q "liquibase-core" app/build.gradle.kts; then \
		echo "   ✅ Encontrada en app/build.gradle.kts"; \
	else \
		echo "   ❌ No encontrada en app/build.gradle.kts"; \
	fi
	@echo ""
	@echo "🔧 Configuración en perfiles:"
	@for profile in application application-local application-dev application-orb application-prod; do \
		config_file="app/src/main/resources/$$profile.yml"; \
		if [ -f "$$config_file" ]; then \
			if grep -q "liquibase:" "$$config_file"; then \
				echo "   ✅ $$profile"; \
			else \
				echo "   ⚠️  $$profile (sin configuración)"; \
			fi; \
		fi; \
	done
	@echo ""
	@echo "✅ Verificación completada"

liquibase-help: ## Muestra ayuda detallada de comandos Liquibase
	@echo "📚 Comandos de Liquibase Disponibles"
	@echo "====================================="
	@echo ""
	@echo "🆕 Crear nueva migración:"
	@echo "   make liquibase-create NAME=add-user-phone"
	@echo ""
	@echo "📋 Listar migraciones:"
	@echo "   make liquibase-list"
	@echo ""
	@echo "✅ Validar archivos:"
	@echo "   make liquibase-validate"
	@echo ""
	@echo "🧪 Probar migraciones:"
	@echo "   make liquibase-test"
	@echo ""
	@echo "📊 Ver estado en BD:"
	@echo "   make liquibase-status"
	@echo ""
	@echo "🤖 Generación automática desde entidades JPA:"
	@echo "   make liquibase-diff                            # Compara BD vs entidades"
	@echo "   make liquibase-generate-schema                 # Genera schema completo"
	@echo "   make liquibase-register-manual FILE=xxx        # Registra archivo manualmente"
	@echo ""
	@echo "↩️  Rollback (deshacer migraciones):"
	@echo "   make liquibase-rollback-one                    # Deshace la última"
	@echo "   make liquibase-rollback-count COUNT=2          # Deshace N migraciones"
	@echo "   make liquibase-rollback-to ID=003-create-chat  # Deshace hasta una específica"
	@echo "   make liquibase-rollback-sql                    # Muestra SQL de rollback"
	@echo ""
	@echo "🔄 Reiniciar BD local:"
	@echo "   make liquibase-reset-local"
	@echo ""
	@echo "🔍 Verificar configuración:"
	@echo "   make liquibase-verify"
	@echo ""
	@echo "📖 Flujo de trabajo típico:"
	@echo "   1. make liquibase-create NAME=add-new-field"
	@echo "   2. Edita el archivo generado"
	@echo "   3. make liquibase-validate"
	@echo "   4. make liquibase-test"
	@echo "   5. git commit & push"
	@echo ""
	@echo "↩️  Si algo sale mal:"
	@echo "   1. make liquibase-rollback-one    # Deshace última migración"
	@echo "   2. Corrige el archivo"
	@echo "   3. make liquibase-test            # Prueba de nuevo"

# Atajos comunes
up: local-up ## Alias para local-up
down: local-down ## Alias para local-down
logs: local-logs ## Alias para local-logs
restart: local-restart ## Alias para local-restart
run: run-local ## Alias para run-local
lb-create: liquibase-create ## Alias corto para liquibase-create
lb-list: liquibase-list ## Alias corto para liquibase-list
lb-validate: liquibase-validate ## Alias corto para liquibase-validate
lb-test: liquibase-test ## Alias corto para liquibase-test
lb-status: liquibase-status ## Alias corto para liquibase-status
lb-diff: liquibase-diff ## Alias corto para liquibase-diff
lb-generate: liquibase-generate-schema ## Alias corto para liquibase-generate-schema
lb-register: liquibase-register-manual ## Alias corto para liquibase-register-manual
lb-rollback: liquibase-rollback-one ## Alias corto para liquibase-rollback-one
lb-rollback-count: liquibase-rollback-count ## Alias corto para liquibase-rollback-count
lb-rollback-to: liquibase-rollback-to ## Alias corto para liquibase-rollback-to
lb-rollback-sql: liquibase-rollback-sql ## Alias corto para liquibase-rollback-sql