CREATE TABLE if not exists categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE if not exists items_categories (
    item_id integer not null references items,
    categories_id integer not null references categories,
    primary key (item_id, categories_id)
);

insert into categories(name) values ('Tasks');
insert into categories(name) values ('Practice');
insert into categories(name) values ('Exam');


insert into items_categories values(1,1);
insert into items_categories values(2,2);
insert into items_categories values(3,3);
