-- Ativando extensão pra UUIDs, se necessário
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

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
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Faltava DEFAULT
    name VARCHAR(40) NOT NULL UNIQUE
);

-- Tabela de unidades de controle
CREATE TABLE control_unity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40) NOT NULL
);

-- Relacionamento entre usuários e unidade de controle
CREATE TABLE users_control_unity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT,

    UNIQUE (user_id, control_unity_id)
);

-- Cômodos
CREATE TABLE rooms(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40),
    control_unity_id UUID NOT NULL,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE
);

-- Dispositivos
CREATE TABLE devices(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40) NOT NULL,
    type VARCHAR(80),
    device_serial VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status VARCHAR(20),
    control_unity_id UUID NOT NULL,
    user_id UUID NOT NULL,
    room_id UUID,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE SET NULL,

    UNIQUE (control_unity_id, device_serial)
);

-- Permissões por cômodo
CREATE TABLE users_rooms(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    control_unity_id UUID NOT NULL,
    user_id UUID NOT NULL,
    room_id UUID NOT NULL,
    role_id UUID NOT NULL,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,

    UNIQUE (user_id, room_id, control_unity_id)
);

-- Permissões por dispositivo
CREATE TABLE users_devices(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    control_unity_id UUID NOT NULL,
    user_id UUID NOT NULL,
    device_id UUID NOT NULL,
    role_id UUID NOT NULL,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,

    UNIQUE (user_id, device_id, control_unity_id)
);

-- Cenas
CREATE TABLE scenes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    control_unity_id UUID NOT NULL,
    user_id UUID NOT NULL,
    name VARCHAR(40) NOT NULL,
    description TEXT,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Permissões por cena
CREATE TABLE users_scenes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    scene_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (scene_id) REFERENCES scenes(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,

    UNIQUE (user_id, scene_id, control_unity_id)
);

-- Dispositivos em cenas
CREATE TABLE devices_scenes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL,
    scene_id UUID NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (scene_id) REFERENCES scenes(id) ON DELETE CASCADE
);

-- Rotinas
CREATE TABLE routines(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    control_unity_id UUID NOT NULL,
    triggers_at TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    weekly_frequency INT,

    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id)
);

-- Dispositivos em rotinas
CREATE TABLE devices_routine(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    triggers_at TIME,
    action VARCHAR(40),

    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE
);

-- Permissões por rotina
CREATE TABLE users_routines(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    routine_id UUID NOT NULL,
    control_unity_id UUID NOT NULL,
    role_id UUID NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE,
    FOREIGN KEY (control_unity_id) REFERENCES control_unity(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,

    UNIQUE (user_id, routine_id, control_unity_id)
);


-- TESTE PARA SABER SE A ESTRUTURA ESTA CORRETA

 -- Um usuario chamado jhon quer automatizar sua casa que tem mais 2 pessoas sua esposa e seu filho
 -- voce adicionou todos os dispositivo da casa e criou 2 cenarios um de noite romantica e um de dia normal, junto desses voce criou um comodo para seu filho
 -- que de la ele pode modificar todos os dispositivo no comodo.


insert into users(username, email, password, phone)
values('jhon', 'jhon@doe.com', '123123', '0800'),
('melinda', 'melinda@doe.com', '321321', '0801'),
('marcus', 'maik@doe.com', '123123', '0802');

insert into control_unity(name) values ('home');

insert into roles(name) values ('admin'), ('guest'), ('child');

select * from users;
select * from roles;
select * from control_unity;

insert into users_control_unity(user_id, control_unity_id, role_id)
values
('bd4ac9e1-e311-4207-a6d1-414972ebced2', '058a458a-3088-4bfc-99cf-106e30784095', '1e907bef-85a9-422c-9014-c3ef01829b6d'),
('797fe685-9d4c-4bec-9b30-6774069e1cd5', '058a458a-3088-4bfc-99cf-106e30784095', '7ec5b00c-8ea0-4d2f-a863-f017ed11283c'),
('115fb049-b9a7-444f-9a4d-5b415e7b2a57', '058a458a-3088-4bfc-99cf-106e30784095', 'dd9b3714-5f62-4074-a921-73b49cf746a1')

insert into devices(device_serial, control_unity_id, user_id)
values
('light-0010001','058a458a-3088-4bfc-99cf-106e30784095', 'bd4ac9e1-e311-4207-a6d1-414972ebced2'),
('air-20107601','058a458a-3088-4bfc-99cf-106e30784095', 'bd4ac9e1-e311-4207-a6d1-414972ebced2'),
('room-light-0010001','058a458a-3088-4bfc-99cf-106e30784095', 'bd4ac9e1-e311-4207-a6d1-414972ebced2')

insert into scenes(control_unity_id, user_id, name, description)
values
('058a458a-3088-4bfc-99cf-106e30784095', 'bd4ac9e1-e311-4207-a6d1-414972ebced2', 'Noite romantica', 'quando estivermos a sos podemos aproveitar demais'),
('058a458a-3088-4bfc-99cf-106e30784095', 'bd4ac9e1-e311-4207-a6d1-414972ebced2', 'dia normal', 'dia comum de aula e trabalho');

insert into rooms(name, control_unity_id) values ('Quarto Maik', '058a458a-3088-4bfc-99cf-106e30784095');
select * from rooms;
select * from devices;

UPDATE devices
SET room_id = '94ad9704-5792-4b6e-98a3-b72a0963e885'
WHERE id = 'e0db532d-5dcb-44b7-9cf3-26e4f1257e69';