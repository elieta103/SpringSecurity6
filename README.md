## Spring Security 05
- Crear una nueva rama 
  - git checkout -b "05/cors-csrf"

- CORS
- Cross Origin Resources Sharing
- Intercambio de recursos de origen cruzado
	- Basado Headers Http
	- Permite indicar dominios, esquemas puerto distinto al suyo.
	- Es un politica que implemntan los navegadores

- CSRF
- Cross-Site Request Forgery
- Falsificacion de peticion en sitios cruzados
	- Tipo de vulnerabilidad de un sitio web, en el que comandos no autorizados son 
	  enviados por un usuario en el cual el sitio confia.
	- Usuario legitimo manda peticiones, sin saberlo
	- Para que ocurra CSRF deben tener 3 cosas 
		- (acciones relevantes, manejo de session basado en cookies, parametros predecible)
	- Spring boot usa JSESSIONID, por lo tanto puede ser vulnerable.

- Levantando el front y back manda el error :
  - Desde postman funciona.
  - Desde el browser no funciona.
    - Access to XMLHttpRequest at 'http://localhost:8080/welcome' from origin 'http://localhost:4200' 
    - has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
    
  - Implementar las politicas de CORS en :
    - @Bean
    - CorsConfigurationSource corsConfigurationSource(){...}
    - Agregar en SecurityConfig :
      - http.cors(cors-> corsConfigurationSource());
  
  - Implementar las politicas de CSRF en :
    - Crear filtro para validar cookie de CSRF, Genera el token y lo setea en los headers.
    - public class CsrfCookieFilter extends OncePerRequestFilter {...} 
    - Agregar en SecurityConfig :
      - var requestHandler = new CsrfTokenRequestAttributeHandler();
      - requestHandler.setCsrfRequestAttributeName("_csrf");
      - 
      - // En todas las url se envía el token, pero se ignora en /welcome y /about_us
      - http.csrf(csrf-> csrf.csrfTokenRequestHandler(requestHandler)
      -  .ignoringRequestMatchers("/welcome","/about_us")
      -  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
      -  .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
  - En los Headers del response se agregan :
    - XSRF-TOKEN=0eaf31b6-c409-41e3-b144-113f2c9373b1; 
    - X-Xsrf-Token:0eaf31b6-c409-41e3-b144-113f2c9373b1

  - Para agregar codigo en rama :
    - git checkout -b "05/cors-csrf"  (SE REALIZÓ AL INICIO)
    - git status
    - git add .
    - git commit -m "05/cors-csrf"
    - git push -u origin 05/cors-csrf
