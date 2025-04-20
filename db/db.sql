-- Tabela de usuários
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL
);

-- Tabela de papéis
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE
);

-- Tabela de unidades de controle
CREATE TABLE control_unity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40) NOT NULL
);

-- Relacionamento entre usuários e unidade de controle
CREATE TABLE users_control_unity (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id INT NOT NULL,

    -- Relacionamentos (FKs)
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT,

    -- Impede duplicação do mesmo usuário na mesma unidade
    UNIQUE (user_id, control_unity_id)
);

-- GERAR A TABELA ROOMS ANTES PARA QUE NAO DE PROBLEMA DE RELACIONAMENTO

CREATE TABLE rooms(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    control_unity_id UUID NOT NULL,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE
);

CREATE TABLE devices(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_serial VARCHAR(255) NOT NULL UNIQUE,
    created_at CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status VARCHAR(20),

     control_unity_id UUID NOT NULL,
     user_id UUID NOT NULL,
     room_id UUID,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE SET NULL,
);

CREATE TABLE users_rooms(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at CURRENT_TIMESTAMP,

    control_unity_id UUID NOT NULL,
    user_id UUID NOT NULL,
    room_id INT NOT NULL,
    role_id INT NOT NULL,


    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON CASCADE DELETE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON CASCADE DELETE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON CASCADE DELETE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON CASCADE DELETE

);

CREATE TABLE users_devices(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at CURRENT_TIMESTAMP,

    user_id UUID NOT NULL,
    device_id INT NOT NULL,
    role_id INT NOT NULL,


    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON CASCADE DELETE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON CASCADE DELETE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON CASCADE DELETE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON CASCADE DELETE

    UNIQUE(user_id, device_id)
);

CREATE TABLE scenes(
    id SERIAL PRIMARY KEY,
    control_unity_id NOT NULL,
    user_id UUID NOT NULL,
    name VARCHAR(40) not null,
    description TEXT
);

CREATE TABLE users_scenes(

);