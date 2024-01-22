## Spring Security 03
- Crear una nueva rama 
  - git checkout -b "03/password_encoders"

- Implementacion propia de PasswordEncoder
  - Solo puede haber un passwordEncoder 
    - Eliminar en SecurityConfig :
      - @Bean
      - PasswordEncoder passwordEncoder()
    - Crear :
      - public class MyPasswordEncoder implements PasswordEncoder{...}
    - Obtener el password de super_user@debuggeandoideas.com en HasCode y actualizar en bd
      - update customers set pwd ='-635289258' where id = 1;
    - Reingresar con : super_user@debuggeandoideas.com, to_be_encoded   Deberia de permitir el acceso.

- Implementacion de BCrypPasswordEncoder
  - Solo puede haber un passwordEncoder
    - Eliminar MyPasswordEncoder
      - Comentar @Component, implements PasswordEncoder y @Override
    - Agregar :
      - @Bean
      - PasswordEncoder passwordEncoder(){...}
    - Agregar CommandLineRunner en Application , para ver el nuevo Hash del pwd.
    - Actualizar en la BD.
      - update customers set pwd ='$2a$10$VEBpZIOzTwolMvSnuLHIzufGWatekpoQK0DaZ/grDW2WERYty02l2' where id = 1;
      
  - Para agregar codigo en rama :
    - git checkout -b "03/password_encoders"  (SE REALIZÓ AL INICIO)
    - git status
    - git add .
    - git commit -m "03/password_encoders"
    - git push -u origin 03/password_encoders 
