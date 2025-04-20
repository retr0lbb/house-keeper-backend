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
    id UUID PRIMARY KEY gen_random_uuid(),
    name VARCHAR(40) NOT NULL UNIQUE
);

-- Tabela de unidades de controle
CREATE TABLE control_unity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40) NOT NULL
);

-- Relacionamento entre usuários e unidade de controle
CREATE TABLE users_control_unity (
    id UUID PRIMARY KEY gen_random_uuid(),
    user_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL,

    -- Relacionamentos (FKs)
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT,

    -- Impede duplicação do mesmo usuário na mesma unidade
    UNIQUE (user_id, control_unity_id)
);

-- GERAR A TABELA ROOMS ANTES PARA QUE NAO DE PROBLEMA DE RELACIONAMENTO

CREATE TABLE rooms(
    id UUID PRIMARY KEY gen_random_uuid(),
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
    room_id UUID NOT NULL,
    role_id UUID NOT NULL,


    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON CASCADE DELETE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON CASCADE DELETE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON CASCADE DELETE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON CASCADE DELETE

);

CREATE TABLE users_devices(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at CURRENT_TIMESTAMP,

    user_id UUID NOT NULL,
    device_id UUID NOT NULL,
    role_id UUID NOT NULL,


    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON CASCADE DELETE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON CASCADE DELETE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON CASCADE DELETE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON CASCADE DELETE

    UNIQUE(user_id, device_id)
);

CREATE TABLE scenes(
    id UUID PRIMARY KEY gen_random_uuid(),
    control_unity_id NOT NULL,
    user_id UUID NOT NULL,
    name VARCHAR(40) not null,
    description TEXT
);

CREATE TABLE users_scenes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,
    scene_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL,


    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON CASCADE DELETE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON CASCADE DELETE,
    FOREIGN KEY (scene_id) REFERENCES scenes(id) ON CASCADE DELETE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON CASCADE DELETE

    UNIQUE(user_id, scene_id, control_unity_id)
);

CREATE TABLE devices_scenes(
    id UUID PRIMARY KEY gen_random_uuid(),
    device_id UUID NOT NULL,
    scene_id UUID NOT NUll
);


CREATE TABLE routines(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    control_unity_id UUID NOT NULL,
    triggers_at TIME,
    created_at TIME,
    weekly_frequency INT
);

CREATE TABLE devices_routine(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    device_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    triggers_at TIME,
    action VARCHAR(40),


    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE
);

CREATE TABLE users_routines(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL

    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(routine_id) REFERENCES routines(id) ON DELETE CASCADE,
    FOREIGN KEY(control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE CASCADE
);