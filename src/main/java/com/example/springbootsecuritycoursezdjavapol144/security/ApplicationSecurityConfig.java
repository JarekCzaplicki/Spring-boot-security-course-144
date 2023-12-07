package com.example.springbootsecuritycoursezdjavapol144.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.springbootsecuritycoursezdjavapol144.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity // adnotacja, która określa, że klasa będzie będzie zawierała konfiguracje dla "Security"
@EnableGlobalMethodSecurity(prePostEnabled = true) // dzięki temu działają adnotacje nad endpointami
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// spring domyślnie zabezpiecza api przed dostępem, teraz musimy wyłączyć
                .authorizeRequests() // deklarujemy że zadania muszą byc autoryzowane
                .antMatchers("/", "index") // część naszej białej listy
                .permitAll()// kolejna część białej listy
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers("management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
//                .antMatchers(HttpMethod.GET, "management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_READ.getPermission())
//                .antMatchers(HttpMethod.PUT, "management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "management/api/**").hasAnyRole(ADMIN.name())
                .anyRequest() // deklarujemy że każde zadanie
                .authenticated() // musi przejść autentykację (klient podaje użytkownika i hasło)
                .and()
                .httpBasic(); // używamy podstawowej autentykacji
    }

    @Override
    @Bean // zwraca beana i będzie zarządzana przez kontekst springa
    protected UserDetailsService userDetailsService() {
        UserDetails jarekUser = User.builder()
                .username("jarek")
                .password(passwordEncoder.encode("123456"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails marekUser = User.builder()
                .username("marek")
                .password(passwordEncoder.encode("123456"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails danielUser = User.builder()
                .username("daniel")
                .password(passwordEncoder.encode("123456"))
//                .roles(STUDENT.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(jarekUser, marekUser, danielUser);
    }

}
