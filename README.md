## Spring Security 04
- Crear una nueva rama 
  - git checkout -b "04/authentication_provider"

- Implementacion Authentication Provider
  - Es una alternativa al uso de UserDetails
  - Eliminar MyPasswordEncoder, CustomerUserDetails
  - En SecurityConfig Regresar a :  NoOpPasswordEncoder
  - Agregar :
    - @Component
    - public class MyAuthenticationProvider implements AuthenticationProvider{...}
  - Es opcional deshabilitar CORS y CSRF
    - http.cors(AbstractHttpConfigurer::disable);
    - http.csrf(AbstractHttpConfigurer::disable);

  - Para agregar codigo en rama :
    - git checkout -b "04/authentication_provider"  (SE REALIZÓ AL INICIO)
    - git status
    - git add .
    - git commit -m "04/authentication_provider"
    - git push -u origin 04/authentication_provider
