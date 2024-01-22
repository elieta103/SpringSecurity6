package com.elhg.security.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
public class MyPasswordEncoder /*implements PasswordEncoder*/ {
    //@Override
    public String encode(CharSequence rawPassword) {
        return String.valueOf(rawPassword.toString().hashCode());
    }

    //@Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        var passwordAsString = String.valueOf(rawPassword.toString().hashCode());
        //log.info("HashCode passwordAsStringPWD : {} ",passwordAsString);
        return passwordAsString.equals(encodedPassword);
    }
}
