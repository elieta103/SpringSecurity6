## Spring Security 09
### Crear una nueva rama 
  - git checkout -b "09/OAuth2"

### Ajustes iniciales
  - Borrar carpeta y contenido /components
  - Borrar AuthenticationController.java
  - Borrar JWTRequest.java JWTResponse.java
  - Borrar contenido /security
  - Borrar contenido /services
  - Borrar @EnableWebSecurity en AppSecurityApplication
  - Borrar en pom.xml (3 librerias de JWT, jjwt-jackson, jjwt-impl, jjwt-api)
  - Borrar en pom.xml spring-boot-starter-security
  - Eliminar imports de librerias que ya no existen
  - Levantar application
  - Agregar librerias OAuth2
    - spring parent                                3.1.2  ó   3.2.2
    - spring-security-oauth2-resource-server       6.1.2  ó  6.2.1
    - spring-security-oauth2-authorization-server  1.1.1  ó  1.1.1

### Creando RegisteredClientRepository
  - Desde SecurityConfig, se puede crear un cliente en memoria, es mejor crearlo en BD 
    - `@Bean
RegisteredClientRepository clientRepository(){
var client = RegisteredClient
.withId(UUID.randomUUID().toString())
.clientId("debugeando ideas")
.clientSecret("secret")
.scope("read")
.redirectUri("http://localhost:8080")
.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
.build();
return new InMemoryRegisteredClientRepository(client);
}`
    - 
### Cambios en la BD
- Agregar tabla de socios/clientes/partners, son los que se pueden conectar a la API
- Agregar el script db/sql/init, borrar el container y volver a lanzar
- DB postgres -> Master password : elieta103, password security : debuggeandoideas

### Agregar entity : PartnerEntity y Repository : PartnerRepository
-  BD : client_id  Entity : clientId,  No es necesario agregar @Column, Spring hace el mapping

### Service para recuperar RegisteredClient (Table Partner)
- Busqueda en tabla partners (son los que se pueden conectar a la API)
- Crear service : public class PartnerRegisteredClientService implements RegisteredClientRepository {...}
- Se implementan : findByClientId, save, findById.  Para la authenticacion solo utilizamos : findByClientId
- Básicamente va a la BD, consulta y recupera el client, mapea : scopes, authorizationGrantTypes, clientAuthenticationMethods
- y construye y devuelve un RegisteredClient.
- Agrega un metodo para la duración del Token : private TokenSettings tokenSettings() {...}

### Service para recuperar Usuario, creando UserDetailsService(Table User)
- Busqueda en tabla customers (son los usuarios, con roles)
- Crear service : public class CustomerUserDetails implements UserDetailsService {...}

### Configuracion OAuth2, mediante encadenamiento de filtros
- Desde SecurityConfig
  - Configuracion default OAuth2, es el primer filtro
    - SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http)throws Exception {..}
  - Configuracion del cliente/partners, es el segundo filtro
    - SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {...} 
    - Que la applicacion tenga el permiso
    - Antes de loguearnos con usuario, nos logueamos con el cliente
  - Configuracion del usuario, es el tercer filtro
    - SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {...} 
    - Que el usuario tenga los roles necesarios
  - No se puede authetificar por Authority(permisos) y Role(role), se debe elegir alguno, POR LO TANTO
  - Se elimina el filtro: userSecurityFilterChain
  - En el filtro de : clientSecurityFilterChain, se agrega
  - `//.requestMatchers(ADMIN_RESOURCES).hasAuthority(AUTH_WRITE)
     //.requestMatchers(USER_RESOURCES).hasAuthority(AUTH_READ)
     .requestMatchers(ADMIN_RESOURCES).hasRole(ROLE_ADMIN)
     .requestMatchers(USER_RESOURCES).hasRole(ROLE_USER)`

### Hasheando password
- Desde SecurityConfig
  -  @Bean PasswordEncoder passwordEncoder(){...}
- Desde el main obtenemos los passwords Hasheados
  - Agregar : implements CommandLineRunner, y mandar a imprimir : this.passwordEncoder.encode("to_be_encoded")
- Levantar la Application
  - User : $2a$10$JqGzpopBBEGqo13BKBsSxeKEBbMd8BL/1wyT4QuGF1n0IG4y2w0Xq
  - Client secret : $2a$10$1kNUbcQWgRLIEQTAdd4exOewc3JVgAmQZhUwbCNy3h7C1VHx8Q.Ne
- Actualizar en el script de la BD los passwords hasheados (passwords users y secret client)
- Eliminar container y reiniciar

### Configurar AuthenticationProvider & AuthenticationServer
- Desde SecurityConfig
  - @Bean AuthenticationProvider authenticationProvider(PasswordEncoder encoder, CustomerUserDetails userDetails){...}
  - @Bean AuthorizationServerSettings authorizationServerSettings(){...}

### Configurar JWT
- Desde SecurityConfig
  - @Bean JwtAuthenticationConverter jwtAuthenticationConverter() {...}

### Generar llaves RSA para JWT
- Desde SecurityConfig
  - Setear datos para generar llaves privada y publica 
    - private static KeyPair generateRSA() {...}
  - Generar llaves 
    - private static RSAKey generateKeys() {...}
    - Para RSAKey se debe importar : import com.nimbusds.jose.jwk.RSAKey;

### Configurar Firma de JWT y decodificar JWT
- Desde SecurityConfig
  - @Bean JWKSource<SecurityContext> jwkSource() {...}
  - @Bean JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {...}
  
### Añadiendo payload al JWT
- Desde SecurityConfig
  - OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {...} 
  - Solo en caso de que sea un : OAuth2TokenType.ACCESS_TOKEN, se agrega payload

### Configurando postman para autenticar
- Desde postman, crear una nueva collection -> security
- Variables -> Crear una nueva variable -> beare_token   Debe quedar activa
- new Request POST :
  - http://localhost:8080/oauth2/token
  - Basic Auth (client, secret) : debuggeandoideas, secret
- body -> x-www-form-urlencoded agregar variables :
  - grant_type=authorization_code
  - code=bc3hDEGFko  -> SE GENERARÁ CON https://oauthdebugger.com/
  - redirect_uri=https://oauthdebugger.com/debug
- tests agregar :
  - var response = JSON.parse(responseBody);
  - pm.collectionVariables.set("beare_token", response.access_token);
- Para obtener el authorization-code
  - Ir a la pagina https://oauthdebugger.com/  simula un frontend 
  - Authorize URI : http://localhost:8080/oauth2/authorize
  - Redirect URI : https://oauthdebugger.com/debug
  - ClientId : debuggeandoideas
  - Scope : write
  - State/Nonce : No son obligados se pueden dejar los default
  - Response type(required)  : code
  - Response mode (required) : form_post

- Si todo es correcto debe abrir una form con user y pwd
  - (account@debuggeandoieas.com, to_be_encoded)
  - Manda un Authorization Code
    - Code : PKSwvxFhtgpnKQOJiv0CzMLgJnhnJtNPdwfEl6f6kF2-Xqv93RUgjF7A0njhcE_yf7VHgJiQY4QR8-qPijWNKT9-MZ-3NCVcWi8HkjTMOH_Sn3ng7_fefKk3YyK2v3iW
  - El code se pega en postman en la variable code del request.

- Desde postman enviar el request si todo es correcto , debe devolver :
  - {
  - "access_token": "eyJraWQiOiI4MWIxNjNmMC1hNTM2LTRlYWUtYWZmMy02ODlkMGRkNDRiMGQiLCJhbGciOiJSUzI1NiJ9.eyJvd25lciI6IkRlYnVnZ2VhbmRvIGlkZWFzIiwic3ViIjoiYWNjb3VudEBkZWJ1Z2dlYW5kb2llYXMuY29tIiwiYXVkIjoiZGVidWdnZWFuZG9pZGVhcyIsIm5iZiI6MTcwNjgzMjI4MCwic2NvcGUiOlsid3JpdGUiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzA2ODYxMDgwLCJpYXQiOjE3MDY4MzIyODAsImRhdGVfcmVxdWVzdCI6IjIwMjQtMDItMDFUMTg6MDQ6NDAuMTQyNjUzIn0.bxkr0dVo56lqscxmh-8G6LzyMwgrjLvGjTU63ee0Le41Hj2WRar7310T2OxZDf1ibJSO00fAFDXFmE91wMeTDydEhHoPYNAtcZZWYst5MGG6zPNtSnAigK_7KGZdrTOvWWk57B8UOA-le5_qN5cPN-lU7GNpKTo9LlllA8I3xCCRdPOeAjqIMvmQQ-78Pow6rknkycq2xO-0L_VO-P1pGrUH2Spthk_3gYZT-7MryOvLgD17BZ7NU91KyKrZgGmuw8Fzwii2357lLiZowmQCu396msNRQm6bewBylUSVm9AjS5fqtcanu_e0ddKuEVnPyCWlrDgw-MC3xegrT_orcw",
  - "refresh_token": "ltqV3aWqFlvEE1AzVFZQVJySmUbWsXSAgTduSO1yefsPp3dDUc6klM172rQvrle8ITPTkMbEMSUz2wJ20kp-mEI7Ahvjl5x3owTUmXcqgey6Gc4ETBSQfalSv75Oaf8H",
  - "scope": "write",
  - "token_type": "Bearer",
  - "expires_in": 28799
  - }

- NO SE PUEDE TRABAJAR CON ROLES Y AUTHORITIES

- Probando recursos ADMIN
  - Generando un token para 'account@debuggeandoieas.com' y considerando que es un ADMIN
  - Authorization -> Bearer Token
  - requestMatchers(ADMIN_RESOURCES).hasRole(ROLE_ADMIN)
  - /accounts  /cards     RESPONSE 200 OK
  - /loans     /balance   RESPONSE 403 FORBIDDEN
  
- Probando recursos USER
  - Generando un token para 'loans@debuggeandoieas.com' y considerando que es un USER
  - Authorization -> Bearer Token
  - requestMatchers(USER_RESOURCES).hasRole(ROLE_USER)
  - /accounts  /cards     RESPONSE 403 FORBIDDEN
  - /loans     /balance   RESPONSE 200 OK

### Para agregar codigo en rama :
  - git checkout -b "09/OAuth2"  (SE REALIZÓ AL INICIO)
  - git status
  - git add .
  - git commit -m "09/OAuth2"
  - git push -u origin 09/OAuth2
