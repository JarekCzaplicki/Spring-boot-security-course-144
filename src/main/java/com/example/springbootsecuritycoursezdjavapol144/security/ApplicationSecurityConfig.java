package com.example.springbootsecuritycoursezdjavapol144.security;

import com.example.springbootsecuritycoursezdjavapol144.auth.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.springbootsecuritycoursezdjavapol144.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity // adnotacja, która określa, że klasa będzie będzie zawierała konfiguracje dla "Security"
@EnableGlobalMethodSecurity(prePostEnabled = true) // dzięki temu działają adnotacje nad endpointami
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() // deklarujemy że zadania muszą byc autoryzowane
                .antMatchers("/", "index") // część naszej białej listy
                .permitAll()// kolejna część białej listy
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest() // deklarujemy że każde zadanie
                .authenticated() // musi przejść autentykację (klient podaje użytkownika i hasło)
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/courses", true)
                .passwordParameter("password1")
                .usernameParameter("username")// jeśli chcemy użyć innej nazwy niz pliku html
                .and()
                .rememberMe() // domyślnie działa przez 30 minut braku aktywności
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("jakiskluczdoszyfrowania") // klucz do szyfrowania przez MD5 dla zawartości, czyli 'username', 'expirationDate'
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
    }

//    @Override
//    @Bean // zwraca beana i będzie zarządzana przez kontekst springa
//    protected UserDetailsService userDetailsService() {
//        UserDetails jarekUser = User.builder()
//                .username("jarek")
//                .password(passwordEncoder.encode("123456"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//        UserDetails marekUser = User.builder()
//                .username("marek")
//                .password(passwordEncoder.encode("123456"))
////                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())// ROLE_STUDENT
//                .build();
//        UserDetails danielUser = User.builder()
//                .username("daniel")
//                .password(passwordEncoder.encode("123456"))
////                .roles(STUDENT.name())
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//        UserDetails kasiaUser = User.builder()
//                .username("kasia")
//                .password(passwordEncoder.encode("123456"))
////                .roles(STUDENT.name())
//                .authorities(GUEST.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(jarekUser, marekUser, danielUser, kasiaUser);
//    }

}
