DROP TABLE IF EXISTS Product;
create table Product (
    price integer not null,
    id bigint generated by default as identity,
    brand varchar(30) not null,
    category varchar(30) not null,
    primary key (id)
)