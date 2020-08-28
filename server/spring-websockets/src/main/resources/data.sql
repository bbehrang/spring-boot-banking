/*DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;

create table accounts (
    id int auto_increment primary key,
    number varchar(200) not null unique,
    currency varchar(5) not null default ('UAH')
);
create table customers(
    id int auto_increment primary key,
    name varchar(200) not null,
    email varchar(200) not null,
    age int default (0)
);*/

insert into customers(age, name, email, password, phone, created_at, last_modified_at, version)
values (1, 'name1', 'email1', '$2y$12$lwd2bfFUhZ5LpVcCb2l9R.P6Zrrw32Nxs/R6SnQ7/DTRb.NsjLRkq', '+(380)931543601', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (2, 'name2', 'email2', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543602', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (3, 'name3', 'email3', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543603', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (4, 'name4', 'email4', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543604', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (5, 'name5', 'email5', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543605', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (6, 'name6', 'email6', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543606', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (7, 'name7', 'email7', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543607', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (8, 'name8', 'email8', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543608', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (9, 'name9', 'email9', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543609', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (10, 'name10', 'email10', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543610', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (11, 'name11', 'email11', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543611', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (12, 'name12', 'email12', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543612', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (13, 'name13', 'email13', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543613', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (14, 'name14', 'email14', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543614', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (15, 'name15', 'email15', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543615', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (16, 'name16', 'email16', '$2y$12$6DEGQxwHpKVLpiZD0MXM7u9RYOm8DRA3PDTHipO6l629l4zmyllpe', '+(380)931543616', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);


insert into accounts(balance, currency, number, customer_id, created_at, last_modified_at, version)
values
    (0, 'UAH', '3118658a-7c98-4731-b8fa-9cc4b8da683a', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1000, 'USD', '45ac5366-65ab-4b69-b3d2-03686b0e45f3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1000, 'USD', '5115e126-5d78-418e-b191-d32d51a4d7de', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
insert into employers(number, address, created_at, last_modified_at, version)
values ('emplyer1', 'address1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       ('employer2', 'addr2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
insert into customers_employers(customer_id, employer_id)
values (1, 1),
       (1, 2),
       (2, 2);
