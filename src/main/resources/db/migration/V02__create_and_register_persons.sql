create table person
(
	id BIGSERIAL
		constraint people_pk
			primary key,
	name varchar(50),
	active boolean,

	public_place varchar(50),
	number varchar (10),
	complement varchar(50),
	district varchar(50),
	cep varchar (8),
	city varchar(50),
	state varchar(50)
);

INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Enkel',false,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Golan',false,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Zuyni',false,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Tunor',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Ruaka',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Boinr',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Cubes',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Sadil',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Toati',true,null,null,null,null,null,null,null);
INSERT INTO person (name, active, public_place, number, complement, district, cep, city, state) VALUES ('Kidie',true,null,null,null,null,null,null,null);