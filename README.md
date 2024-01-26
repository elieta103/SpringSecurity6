## Spring Security 07
- Crear una nueva rama 
  - git checkout -b "07/filters"

- Basado en los filtros de servlets.
- Los filtros dentro de las app java se usan para interceptar cada solicitud y respuesta
- y hacer un trabajo previo antes de nuestra lógica comercial.
- Spring Security aplica la seguridad en función de nuestra configuración dentro de nuestra
- aplicacion web.

- Ejemplos de filters
- CorsFilter <-> CsrfFilter <-> RequestValidationFilter <-> BasicAuthFilter

- Debbug para saber cuantos filtros tenemos activos
  - Desde el main :  @EnableWebSecurity(debug=true)
  - Se modificó la versión a : 3.1.1,  
  - Las versiones 3.2.1 y 3.2.2: Marcan excepción.

- Implementar el filtro :  ApiKeyFilter extends OncePerRequestFilter
- Desde postman lanzar un request sin Header:  localhost:8080/cards (cards@debuggeandoieas.com, to_be_encoded)
  - org.springframework.security.authentication.BadCredentialsException: Invalid api key 
- Desde postman lanzar un request con Header (api_key, myKey):  localhost:8080/cards (cards@debuggeandoieas.com, to_be_encoded)
  - Desplegará el resultado : { "msj": "cards" }
  
    
- Para agregar codigo en rama :
  - git checkout -b "07/filters"  (SE REALIZÓ AL INICIO)
  - git status
  - git add .
  - git commit -m "07/filters"
  - git push -u origin 06/filters
