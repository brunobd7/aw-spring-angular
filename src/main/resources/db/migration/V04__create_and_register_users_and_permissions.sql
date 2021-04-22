create table users(
    id integer primary key,
    name varchar(50) not null,
    email varchar(50) not null,
    password varchar(150) not null
);

create table permission(
    id integer primary key ,
    description varchar(50) not null
);

create table users_permission(
--     id serial primary key,
    id_user integer not null ,
    id_permission integer not null,
    primary key (id_user,id_permission),
    foreign key (id_user) references users(id),
    foreign key (id_permission) references permission(id)
);

--INSERTS CARGA
INSERT INTO users (id, name, email, password) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO users (id, name, email, password) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permission (id, description) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permission (id, description) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permission (id, description) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permission (id, description) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permission (id, description) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permission (id, description) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permission (id, description) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permission (id, description) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO users_permission (id_user, id_permission) values (1, 1);
INSERT INTO users_permission (id_user, id_permission) values (1, 2);
INSERT INTO users_permission (id_user, id_permission) values (1, 3);
INSERT INTO users_permission (id_user, id_permission) values (1, 4);
INSERT INTO users_permission (id_user, id_permission) values (1, 5);
INSERT INTO users_permission (id_user, id_permission) values (1, 6);
INSERT INTO users_permission (id_user, id_permission) values (1, 7);
INSERT INTO users_permission (id_user, id_permission) values (1, 8);

-- maria
INSERT INTO users_permission (id_user, id_permission) values (2, 2);
INSERT INTO users_permission (id_user, id_permission) values (2, 5);
INSERT INTO users_permission (id_user, id_permission) values (2, 8);