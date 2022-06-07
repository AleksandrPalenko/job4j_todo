CREATE TABLE if not exists items (
	id SERIAL PRIMARY KEY,
	name text,
	description text,
	created timestamp default current_timestamp,
	done BOOLEAN default false,
	users_id int references users(id)
);

--ALTER SEQUENCE items_id_seq RESTART WITH 1;
CREATE TABLE if not exists users (
    id serial primary key,
    email varchar (2000),
    password varchar (2000)
);

--ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);