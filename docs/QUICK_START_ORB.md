# 🎯 Quick Start - Local Development with Orb Stack

## ⚡ Essential Commands

### Start Everything from Scratch
```bash
./reset-and-start-orb.sh
```

### Run the Application
```bash
./run-orb-app.sh
```

### Stop Services
```bash
docker-compose -f docker-compose.orb.yml down
```

## 🏗️ Local Architecture

```
┌─────────────────────────────────────────────┐
│         Your Mac (Host)                     │
│                                             │
│  ┌─────────────────────────────────────┐   │
│  │  Spring Boot Application            │   │
│  │  (Port 8080)                        │   │
│  │  Profile: orb                       │   │
│  └────────┬────────────────────────────┘   │
│           │                                 │
│           │ Connects via direct IPs         │
│           │                                 │
│  ┌────────▼────────────────────────────┐   │
│  │   Orb Stack (Docker)                │   │
│  │                                      │   │
│  │  ┌────────────────────────────┐     │   │
│  │  │ PostgreSQL                 │     │   │
│  │  │ 192.168.97.2:5432         │     │   │
│  │  │ DB: rosafiesta            │     │   │
│  │  └────────────────────────────┘     │   │
│  │                                      │   │
│  │  ┌────────────────────────────┐     │   │
│  │  │ Redis                      │     │   │
│  │  │ 192.168.97.3:6379         │     │   │
│  │  └────────────────────────────┘     │   │
│  │                                      │   │
│  │  ┌────────────────────────────┐     │   │
│  │  │ RabbitMQ                   │     │   │
│  │  │ 192.168.97.4:5672         │     │   │
│  │  │ Management: :15672         │     │   │
│  │  └────────────────────────────┘     │   │
│  └─────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
```

## 📋 Startup Checklist

1. ✅ **Orb Stack installed and running**
2. ✅ **Docker services started**
   ```bash
   docker ps | grep rosafiesta
   ```
3. ✅ **Environment variables configured**
   ```bash
   export MAIL_FROM_EMAIL=your-email@gmail.com
   export MAIL_PASSWORD="your-app-password"
   ```
4. ✅ **Application running at http://localhost:8080**

## ⚠️ Disabled Services in ORB

- **Firebase**: Push notifications are disabled
- **Supabase Storage**: Use local storage or mock

These services are not necessary for basic local development.

## 🔑 Credentials

### PostgreSQL
```
Host: 192.168.97.2
Port: 5432
Database: rosafiesta
User: postgres
Password: postgres
```

### Redis
```
Host: 192.168.97.3
Port: 6379
Password: rosafiesta_redis_password
```

### RabbitMQ
```
Host: 192.168.97.4
Port: 5672
User: rosafiesta_user
Password: rosafiesta_password
Virtual Host: rosafiesta

Management UI: http://localhost:15672
```

## 🐛 Common Troubleshooting

### Error: "Could not resolve placeholder"
**Solution**: Verify all environment variables are configured
```bash
export MAIL_FROM_EMAIL=your-email@gmail.com
export MAIL_PASSWORD="your-app-password"
```

### Error: "password authentication failed"
**Solution**: Recreate containers from scratch
```bash
./reset-and-start-orb.sh
```

### Error: "schema does not exist"
**Solution**: Schemas are created automatically by Liquibase on first startup.

If you need to recreate them manually:
```bash
docker exec rosafiesta-postgres psql -U postgres -d rosafiesta -c \
  "CREATE SCHEMA IF NOT EXISTS chat_service; \
   CREATE SCHEMA IF NOT EXISTS user_service; \
   CREATE SCHEMA IF NOT EXISTS notification_service;"
```

**Note**: With Liquibase enabled, database migrations are handled automatically.

### Container IPs changed
**Solution**: Get new IPs and update `application-orb.yml`
```bash
docker inspect rosafiesta-postgres -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'
docker inspect rosafiesta-redis -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'
docker inspect rosafiesta-rabbitmq -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'
```

## 📊 Monitoring Commands

### View all services logs
```bash
docker-compose -f docker-compose.orb.yml logs -f
```

### View specific service logs
```bash
docker logs -f rosafiesta-postgres
docker logs -f rosafiesta-redis
docker logs -f rosafiesta-rabbitmq
```

### Check health status
```bash
docker-compose -f docker-compose.orb.yml ps
```

### Connect to PostgreSQL
```bash
docker exec -it rosafiesta-postgres psql -U postgres -d rosafiesta
```

### Connect to Redis
```bash
docker exec -it rosafiesta-redis redis-cli -a rosafiesta_redis_password
```

## 🎯 IntelliJ IDEA

### Available Configurations
- **RosaFiestaApi [ORB]**: Runs the application with ORB profile
- **Docker: Start Local Services (Orb Stack)**: Starts Docker services

### Run with Debug
1. Select `RosaFiestaApi [ORB]`
2. Click the debug icon (🐛)
3. Place breakpoints where needed

## 💡 Useful Tips

1. **Clean Data**: Use `./reset-and-start-orb.sh` when you want to start with empty database
2. **Performance**: Local services are much faster than remote ones
3. **Debugging**: You can see all SQL queries with DEBUG logging enabled
4. **RabbitMQ UI**: Monitor queues and messages at http://localhost:15672

## 📚 Complete Documentation

See `ORB_STACK_SETUP.md` for detailed documentation.

---

**Ready to start?** Run:
```bash
./reset-and-start-orb.sh && ./run-orb-app.sh
```