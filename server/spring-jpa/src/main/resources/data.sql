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

insert into customers(age, name, email) values (1, 'name1', 'email22'),
                                                (2,'name2', 'email');
insert into accounts(balance, currency, number, customer_id) values (0, 'UAH', '3118658a-7c98-4731-b8fa-9cc4b8da683a', 1),
                                                        (1000, 'USD', '45ac5366-65ab-4b69-b3d2-03686b0e45f3', 1),
                                                        (1000, 'USD', '5115e126-5d78-418e-b191-d32d51a4d7de', 2);
insert into employers(number, address) values ('emplyer1', 'address1'),
                                            ('employer2', 'addr2');
insert into customers_employers(customer_id, employer_id) values (1,1),
                                                                 (1,2),
                                                                 (2,2);
