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


insert into items_categories values(1,1,1);
insert into items_categories values(2,2,2);
insert into items_categories values(3,3,3);
insert into items_categories values(4,10,2);
insert into items_categories values(5,8,3);
insert into items_categories values(6,11,1);
insert into items_categories values(7,12,3);