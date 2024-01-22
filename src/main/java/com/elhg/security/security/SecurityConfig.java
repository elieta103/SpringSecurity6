package com.elhg.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/loans","/balance", "/accounts", "/cards").authenticated()
                        .anyRequest().permitAll())
                        //.requestMatchers("/welcome", "/about_us").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /*@Bean
    UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

    /*@Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        var admin = User.withUsername("admin")
                .password( passwordEncoder().encode("to_be_encoded"))
                .authorities("ADMIN")
                .build();
        var user = User.withUsername("user")
                .password(passwordEncoder().encode( "to_be_encoded"))
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }*/


    /*@Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
