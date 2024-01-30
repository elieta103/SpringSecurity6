## Spring Security 08
- Crear una nueva rama 
  - git checkout -b "08/jwt"

- JWT esta codificado Base64, tiene reversa.
- Tiene un Header, Payload, Signature.

- Para esta sección eliminar :
  - La clase ApiKeyFilter.class
  - En SecurityConfig : http.addFilterBefore(new ApiKeyFilter(), BasicAuthenticationFilter.class);
  
- Agregar package services

- Agregar dependencias de JWT
  - jjwt-jackson  0.11.5
  - jjwt-impl     0.11.5
  - jjwt-api      0.11.5
  - Crear un public class JwtUserDetailsService implements UserDetailsService{...}
  - Crear un public class JwtService {...}
  - Crear en package components : public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{...} 
    - Es avisar a spring que tenemos un endpoint de authenticacion
    - AuthenticationEntryPoint is used to send an HTTP response that requests credentials from a client.
    - Sometimes a client will proactively include credentials such as a username/password to request a resource. 
    - In these cases, Spring Security does not need to provide an HTTP response that requests credentials 
    - from the client since they are already included. In other cases, a client will make an unauthenticated request
    - to a resource that they are not authorized to access. In this case, an implementation of AuthenticationEntryPoint 
    - is used to request credentials from the client.
    - The AuthenticationEntryPoint implementation might perform a redirect to a log in page, 
    - respond with an WWW-Authenticate header, etc.
    - AuthenticationEntryPoint is used in Spring Web Security to configure an application to perform 
    - certain actions whenever an unauthenticated client tries to access private resources.
    
  - Crear Entities JWTRequest, JWTResponse, para las credenciales.
  
  - Crear controller para JWT public class AuthenticationController {...}
    - Tiene un metodo public, la que realiza spring por medio del token
    - Tiene un metodo privado, para authenticar es un basic authentication
  
  - En SecurityConfig agregar : 
    - @Bean
    - AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{...}
  - En SecurityFilterChain agregar la url del authenticate
    - .ignoringRequestMatchers("/welcome","/about_us", "/authenticate") 
  - En SecurityFilterChain agregar un sessionManagment
    - http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 

  - Probando Creacion del JWT, desde postman
  - POST        http://localhost:8080/authenticate
  -  {  "username":"account@debuggeandoieas.com",
  -     "password":"to_be_encoded"
  -  }
  - https://jwt.io,  Validar JWT
  - Para quitar el mensaje : "Invalid Signature", Checked la opcion "secret base64 encoded"

  - Una vez que funciona la generacion del token, se debe agregar un filtro
  - public class JWTValidationFilter extends OncePerRequestFilter {...}
  - En el SecurityConfig :
  - Cuando el filtro no tiene dependencias se puede agregar como :
  - .addFilterAfter(new CsrfCookieFilter(),....
  - En este caso como el filtro de JWTValidationFilter tiene dependencias se inyecta asi :
  - SecurityFilterChain securityFilterChain(HttpSecurity http, JWTValidationFilter jwtValidationFilter) throws Exception {...}
  - http.addFilterAfter(jwtValidationFilter,BasicAuthenticationFilter.class);
  
  - Se puede eliminar el MyAuthenticationProvider (user y pwd), ya que ahora el login es por JWT.
  
  - Probando todo:
  - Login/Obtener JWT
  - POST        http://localhost:8080/authenticate
  -  {  "username":"account@debuggeandoieas.com",
  -     "password":"to_be_encoded"
  -  }
  - Postman -> Authorization -> Bearer Token
  - Si generó el token con username account que es ADMIN, solo puedo acceder a: .requestMatchers("/accounts", "/cards").hasRole("ADMIN")
  - Se mantienen los roles.

- Para agregar codigo en rama :
  - git checkout -b "08/jwt"  (SE REALIZÓ AL INICIO)
  - git status
  - git add .
  - git commit -m "08/jwt"
  - git push -u origin 08/jwt
