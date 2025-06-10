CREATE EXTENSION IF NOT EXISTS "pgcrypto";

--Dominios Simples
CREATE DOMAIN email_address AS VARCHAR(255)
CHECK (
    VALUE ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
);

CREATE DOMAIN level_description AS VARCHAR(70)
CHECK (VALUE IN ('admin', 'basic'));

CREATE DOMAIN entity_name AS VARCHAR(255)
CHECK (length(trim(value)) >= 2);


--real deal tables
CREATE TABLE IF NOT EXISTS access_level (
    id SERIAL PRIMARY KEY,
    value INT,
    description level_description
);

-- Usuários
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email email_address UNIQUE,
    password VARCHAR(255),
    access_level_id INT,
    FOREIGN KEY (access_level_id) REFERENCES access_level(id)
);

-- Cômodos
CREATE TABLE IF NOT EXISTS rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name entity_name,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dispositivos
CREATE TABLE IF NOT EXISTS devices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_serial VARCHAR(255) UNIQUE,
    user_id UUID NOT NULL,
    room_id UUID,
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
    name entity_name,
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


--sessao auditoria

CREATE TABLE deleted_users (
    id UUID,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    access_level_id INT,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE deleted_devices (
    id UUID,
    device_serial VARCHAR(255),
    user_id UUID,
    room_id UUID,
    added_at TIMESTAMP,
    last_change_at TIMESTAMP,
    status VARCHAR(20),
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE devices_update_history (
    id UUID,
    old_device_serial VARCHAR(255),
    old_user_id UUID,
    old_room_id UUID,
    old_status VARCHAR(20),
    old_last_change_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE OR REPLACE FUNCTION audit_user_deletion()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO deleted_users (id, name, email, password, access_level_id)
    VALUES (OLD.id, OLD.name, OLD.email, OLD.password, OLD.access_level_id);
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION audit_device_deletion()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO deleted_devices (id, device_serial, user_id, room_id, added_at, last_change_at, status)
    VALUES (OLD.id, OLD.device_serial, OLD.user_id, OLD.room_id, OLD.added_at, OLD.last_change_at, OLD.status);
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION audit_device_update()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO devices_update_history (id, old_device_serial, old_user_id, old_room_id, old_status, old_last_change_at)
    VALUES (OLD.id, OLD.device_serial, OLD.user_id, OLD.room_id, OLD.status, OLD.last_change_at);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_audit_user_delete
BEFORE DELETE ON users
FOR EACH ROW
EXECUTE FUNCTION audit_user_deletion();


CREATE TRIGGER trg_audit_device_delete
BEFORE DELETE ON devices
FOR EACH ROW
EXECUTE FUNCTION audit_device_deletion();


CREATE TRIGGER trg_audit_device_update
BEFORE UPDATE ON devices
FOR EACH ROW
EXECUTE FUNCTION audit_device_update();


