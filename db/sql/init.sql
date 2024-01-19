create table customers(   id bigserial primary key,
                          email varchar(50) not null,
                          pwd varchar(500) not null,
                          rol varchar(20) not null);

insert into customers (email, pwd, rol) VALUES
  ('super_user@debuggeandoieas.com', 'to_be_encoded', 'admin'),
  ('gresshel_user@debuggeandoieas.com', 'to_be_encoded', 'admin'),
  ('basic_user@debuggeandoieas.com', 'to_be_encoded', 'user');

