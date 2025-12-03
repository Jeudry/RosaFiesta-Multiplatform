# Scripts Directory

Utility scripts for local development with Orb Stack.

## Daily Use Scripts

- **`start-local.sh`** - Start all Docker services (auto-detects Orb Stack/Docker)
- **`run-orb-app.sh`** - Run Spring Boot application with ORB profile
- **`check-status.sh`** - Check status of all services
- **`stop-local.sh`** - Stop Docker services

## Special Purpose

- **`reset-and-start-orb.sh`** - Reset everything and start fresh (when things break)
- **`stop-orb.sh`** - Quick stop for Orb Stack
- **`test-orb-startup.sh`** - Automated startup test

## Project Scripts

- **`sync_github.sh`** - Sync with GitHub
- **`test-setup.sh`** - Test project setup
- **`verify-setup.sh`** - Verify project configuration

## Setup

### First Time Setup

1. **Copy the environment template:**
   ```bash
   cp scripts/.env.local.example scripts/.env.local
   ```

2. **Edit `.env.local` with your credentials:**
   ```bash
   # Edit this file
   nano scripts/.env.local
   ```

3. **Add your Gmail App Password:**
   - Go to: https://myaccount.google.com/apppasswords
   - Generate an app password for "Mail"
   - Copy it to `.env.local`

**Note:** `.env.local` is gitignored and will NOT be committed.

## Usage

From project root:
```bash
./scripts/start-local.sh
./scripts/run-orb-app.sh
```

The scripts will automatically load credentials from `.env.local`.