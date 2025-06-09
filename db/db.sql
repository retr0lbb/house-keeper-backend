-- Ativando extensão pra UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Nível de acesso
CREATE TABLE IF NOT EXISTS access_level (
    id SERIAL PRIMARY KEY,
    value INT
);

-- Usuários
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    access_level_id INT,
    FOREIGN KEY (access_level_id) REFERENCES access_level(id)
);

-- Cômodos
CREATE TABLE IF NOT EXISTS rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dispositivos
CREATE TABLE IF NOT EXISTS devices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_serial VARCHAR(255) UNIQUE,
    user_id UUID NOT NULL,
    room_id UUID NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_change_at TIMESTAMP,
    status VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- Rotinas
CREATE TABLE IF NOT EXISTS routines(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trigger_type VARCHAR(50),
    is_running BOOLEAN DEFAULT FALSE,
    triggers_at TIMESTAMP
);

-- Dispositivos em rotinas
CREATE TABLE IF NOT EXISTS devices_routines (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    value JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE
);

-- Cenas
CREATE TABLE IF NOT EXISTS scenes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dispositivos em cenas
CREATE TABLE IF NOT EXISTS devices_scenes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL,
    scene_id UUID NOT NULL,
    value JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (scene_id) REFERENCES scenes(id) ON DELETE CASCADE
);

