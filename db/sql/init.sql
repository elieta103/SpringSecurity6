create table customers(
                          id    bigserial primary key,
                          email varchar(70)  not null,
                          pwd   varchar(500) not null
);

insert into customers (email, pwd) values
                                       ('account@debuggeandoieas.com', '$2a$10$JqGzpopBBEGqo13BKBsSxeKEBbMd8BL/1wyT4QuGF1n0IG4y2w0Xq'),
                                       ('cards@debuggeandoieas.com', '$2a$10$JqGzpopBBEGqo13BKBsSxeKEBbMd8BL/1wyT4QuGF1n0IG4y2w0Xq'),
                                       ('loans@debuggeandoieas.com', '$2a$10$JqGzpopBBEGqo13BKBsSxeKEBbMd8BL/1wyT4QuGF1n0IG4y2w0Xq'),
                                       ('balance@debuggeandoieas.com', '$2a$10$JqGzpopBBEGqo13BKBsSxeKEBbMd8BL/1wyT4QuGF1n0IG4y2w0Xq');


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


create table partners (   id bigserial primary key,
                          client_id varchar(256),
                          client_name varchar(256),
                          client_secret varchar(256),
                          scopes varchar(256),
                          grant_types varchar(256),
                          authentication_methods varchar(256),
                          redirect_uri varchar(256),
                          redirect_uri_logout varchar(256)
);

insert into partners(
    client_id,
    client_name,
    client_secret,
    scopes,
    grant_types,
    authentication_methods,
    redirect_uri,
    redirect_uri_logout)
values ('debuggeandoideas',
            'debuggeando ideas',
            '$2a$10$1kNUbcQWgRLIEQTAdd4exOewc3JVgAmQZhUwbCNy3h7C1VHx8Q.Ne',
            'read,write',
            'authorization_code,refresh_token',
            'client_secret_basic,client_secret_jwt',
            'https://oauthdebugger.com/debug',
            'https://springone.io/authorized')



