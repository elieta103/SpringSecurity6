## Spring Security 02
- Crear una nueva rama 
  - git checkout -b "02/custom_users-inMemory_and_jdbc"

- Para usuarios en memoria
  - @Bean 
  - InMemoryUserDetailsManager inMemoryUserDetailsManager(){ ... }

- Para usuarios con DB 
  - Agregar dependencia :
    - spring-boot-starter-data-jpa

- Si utilizamos : JdbcUserDetailsManager es obligado utilizar el schema propuesto por spring-security
  - Definicion del esquema esta en :
    - https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
    - Datos de prueba(Postgres), Datos y tablas agregar en init.sql :
      create table users(
          username varchar(50) not null primary key,
          password varchar(500) not null,
          enabled boolean not null
      );
      create table authorities (
          username varchar(50) not null,
          authority varchar(50) not null,
          constraint fk_authorities_users foreign key(username) references users(username)
      );
      create unique index ix_auth_username on authorities (
                                                          username,
                                                          authority);
      insert into users (username, password, enabled) VALUES
                                                    ('admin', 'to_be_encoded', true),
                                                    ('user', 'to_be_encoded', true);
      insert into authorities (username, authority) VALUES
                                                  ('admin', 'admin'),
                                                  ('user', 'user');

- Crear base de datos con docker
  - Ver docker-compose.yml, .\db\sql\create_schema.sql, .\db\sql\data.sql
  - Verificar que no este levantado otro contendedor de postgres
    - Borrar y levantar nuevo container para refrescar cambios en el script. init.sql
    - docker-compose down, docker ps -a, docker rm container, docker rmi imagen
    - docker-compose up -d Ó docker compose up -d
    - docker exec -it security_bank psql -U alejandro -d security_bank 
  - Configurar conexion DataSource de la base de datos : En application.properties
    - spring.datasource.url=jdbc:postgresql://localhost:5432/security_bank
    - spring.datasource.username=alejandro
    - spring.datasource.password=debuggeandoideas
    - spring.datasource.hikari.connection-timeout=20000
    - spring.datasource.hikari.maximum-pool-size=5
  - Agregar metodo en SecurityConfig :
      - @Bean
      - UserDetailsService userDetailsService(DataSource dataSource){...}
  


  - Definicion de un esquema personalizado(Postgres), Datos y tablas agregar en init.sql :
      create table customers(
                                id bigserial primary key,
                                email varchar(50) not null,
                                pwd varchar(500) not null,
                                rol varchar(20) not null);
      insert into customers (email, pwd, rol) VALUES
        ('super_user@debuggeandoieas.com', 'to_be_encoded', 'admin'),
        ('basic_user@debuggeandoieas.com', 'to_be_encoded', 'user');

  - Configurar conexion DataSource de la base de datos : En application.properties
    - spring.datasource.url=jdbc:postgresql://localhost:5432/security_bank
    - spring.datasource.username=alejandro
    - spring.datasource.password=debuggeandoideas
    - spring.datasource.hikari.connection-timeout=20000
    - spring.datasource.hikari.maximum-pool-size=5

  - Implementar JPA para esquema personalizado
    - Agregar dependencias :
      -	spring-boot-starter-data-jpa
	    - lombok

  - Crear class CustomerEntity
  - Crear class CustomerRepository
  - Crear class CustomerUserDetails implements UserDetailsService
  - En SecurityService, solo dejar filtro y passwordEncoder
    - @Bean
    - SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {...}

    - @Bean
    - PasswordEncoder passwordEncoder(){...}
  
  - Para agregar codigo en rama :
    - git checkout -b "02/custom_users-inMemory_and_jdbc"  (SE REALIZÓ AL INICIO)
    - git status
    - git add .
    - git commit -m "02/custom_users-inMemory_and_jdbc"
    - git push -u origin 02/custom_users-inMemory_and_jdbc 
