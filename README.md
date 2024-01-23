## Spring Security 06
- Crear una nueva rama 
  - git checkout -b "06/roles"

  - Authetication
    - Authentication valida lo que un usuario dice ser
    - Primero se Authentifica luego se autoriza
    - Requiere crendenciales
    - En caso de falla, Devuelve  HTTP 401
  - Authorization
    - Otorga acceso a un usuario especifico
    - Ocurre despues de la Authenticacion
    - Necesita roles o provilegios
    - En caso de falla, Devuelve HTTP 403

-Para Authorities utilizar el prefijo VIEW, el esquema se mantiene igual cambian los datos
  - insert into roles(role_name, description, id_customer) values
  - ('VIEW_ACCOUNT', 'Can view account endpoint', 1),
  - ('VIEW_CARDS', 'Can view cards endpoint', 2),
  - ('VIEW_LOANS', 'Can view loans endpoint', 3),
  - ('VIEW_BALANCE', 'Can view balance endpoint', 4);

  - Modificacion del esquema de la base de datos, en el archivo init
    - Ver docker-compose.yml, .\db\sql\init.sql
    - Verificar que no este levantado otro contendedor de postgres
    - Borrar y levantar nuevo container para refrescar cambios en el script: init.sql
    - docker-compose down, docker ps -a, docker rm <id-container>, docker rmi <id-imagen>
    - docker-compose up -d Ó docker compose up -d
    - docker exec -it security_bank psql -U alejandro -d security_bank
    
  - Modificación de MyAuthenticationProvider class para Identificar al customer con sus roles
  - Modificación de SecurityConfig para Autorizar
    - .requestMatchers("/loans").hasAuthority("VIEW_LOANS")
    - .requestMatchers("/balance").hasAuthority("VIEW_BALANCE")
    - .requestMatchers("/cards").hasAuthority("VIEW_CARDS")
    - .requestMatchers("/accounts").hasAnyAuthority("VIEW_ACCOUNT", "VIEW_CARDS")
  
  - Desde postman: 
    - localhost:8080/cards -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
    - localhost:8080/cards -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK
    - localhost:8080/loans -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
    - localhost:8080/loans -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK
    - localhost:8080/accounts -> Basic Auth -> 'account@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
    - localhost:8080/accounts -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
    - localhost:8080/accounts -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK


  
-Para Roles utilizar el prefijo ROLE solo en la DB, el esquema se mantiene igual cambian los datos
  - insert into roles(role_name, description, id_customer) values
  - ('ROLE_ADMIN', 'Can view account endpoint', 1),
  - ('ROLE_ADMIN', 'Can view cards endpoint', 2),
  - ('ROLE_USER', 'Can view loans endpoint', 3),
  - ('ROLE_USER', 'Can view balance endpoint', 4);
- Modificacion del esquema de la base de datos, en el archivo init
  - Ver docker-compose.yml, .\db\sql\init.sql
  - Verificar que no este levantado otro contendedor de postgres
  - Borrar y levantar nuevo container para refrescar cambios en el script: init.sql
  - docker-compose down, docker ps -a, docker rm <id-container>, docker rmi <id-imagen>
  - docker-compose up -d Ó docker compose up -d
  - docker exec -it security_bank psql -U alejandro -d security_bank

- Modificación de MyAuthenticationProvider class para Identificar al customer con sus roles
- Modificación de SecurityConfig para Autorizar
  - .requestMatchers("/loans", "/balance").hasRole("USER")
  - .requestMatchers("/accounts", "/cards").hasRole("ADMIN")

- Se puede realizar la configuracion a nivel de endponit o de servicio
  - Comentar : //.requestMatchers("/loans", "/balance").hasRole("USER")
  - Agregar en SecurityConfig @EnableMethodSecurity
  - Agregar en Controller Loans y Controller Balance  @PreAuthorize("hasAnyRole('USER')")

- Desde postman:
  - localhost:8080/cards -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
  - localhost:8080/cards -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK
  - localhost:8080/loans -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
  - localhost:8080/loans -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK
  - localhost:8080/accounts -> Basic Auth -> 'account@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
  - localhost:8080/accounts -> Basic Auth -> 'cards@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 200 OK
  - localhost:8080/accounts -> Basic Auth -> 'loans@debuggeandoieas.com', 'to_be_encoded'  Devuelve : HTTP 403 OK

    
- Para agregar codigo en rama :
  - git checkout -b "06/roles"  (SE REALIZÓ AL INICIO)
  - git status
  - git add .
  - git commit -m "06/roles"
  - git push -u origin 06/roles
