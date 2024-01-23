create table customers(
                          id    bigserial primary key,
                          email varchar(70)  not null,
                          pwd   varchar(500) not null
);

insert into customers (email, pwd) values
                                       ('account@debuggeandoieas.com', 'to_be_encoded'),
                                       ('cards@debuggeandoieas.com', 'to_be_encoded'),
                                       ('loans@debuggeandoieas.com', 'to_be_encoded'),
                                       ('balance@debuggeandoieas.com', 'to_be_encoded');


create table roles(
                      id  bigserial primary key,
                      role_name varchar(50),
                      description varchar(100),
                      id_customer bigint,
                      constraint fk_customer foreign key(id_customer) references customers(id)
);

insert into roles(role_name, description, id_customer) values
                                                           ('ROLE_ADMIN', 'Can view account endpoint', 1),
                                                           ('ROLE_ADMIN', 'Can view cards endpoint', 2),
                                                           ('ROLE_USER', 'Can view loans endpoint', 3),
                                                           ('ROLE_USER', 'Can view balance endpoint', 4);





