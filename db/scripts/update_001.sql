CREATE TABLE if not exists items (
	id SERIAL PRIMARY KEY,
	name text,
	description text,
	created timestamp default current_timestamp,
	done BOOLEAN default false
);

--ALTER SEQUENCE items_id_seq RESTART WITH 1;
