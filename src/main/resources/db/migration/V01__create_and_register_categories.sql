create table category
(
	id BIGSERIAL
		constraint category_pk
			primary key,
	name varchar(50)
);

INSERT INTO category (name) values ('Lazer');
INSERT INTO category (name) values ('Alimentação');
INSERT INTO category (name) values ('Supermercado');
INSERT INTO category (name) values ('Farmácia');
INSERT INTO category (name) values ('Outros');