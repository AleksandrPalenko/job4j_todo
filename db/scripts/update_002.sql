CREATE TABLE if not exists categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

insert into categories(name) values ('Tasks');
insert into categories(name) values ('Practice');
insert into categories(name) values ('Exam');

CREATE TABLE if not exists items_categories (
    id serial primary key,
    item_id integer not null references items,
    categories_id integer not null references categories

);
