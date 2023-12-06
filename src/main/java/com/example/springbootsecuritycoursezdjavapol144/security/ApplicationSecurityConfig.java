package com.example.springbootsecuritycoursezdjavapol144.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // adnotacja, która określa, że klasa będzie będzie zawierała konfiguracje dla "Security"
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // deklarujemy że zadania muszą byc autoryzowane
                .antMatchers("/", "index") // część naszej białej listy
                .permitAll()// kolejna część białej listy
                .anyRequest() // deklarujemy że każde zadanie
                .authenticated() // musi przejść autentykację (klient podaje użytkownika i hasło)
                .and()
                .httpBasic(); // używamy podstawowej autentykacji
    }
}
