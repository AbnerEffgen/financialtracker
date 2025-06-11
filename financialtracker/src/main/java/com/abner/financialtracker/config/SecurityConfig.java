package com.abner.financialtracker.config;

// Importa a anotação @Bean, que marca um método como produtor de um bean gerenciado pelo Spring.
import org.springframework.context.annotation.Bean;

// Importa a anotação @Configuration, que indica que a classe é uma fonte de definições de beans (configuração do Spring).
import org.springframework.context.annotation.Configuration;

// Importa a classe HttpSecurity, usada para configurar as regras de segurança HTTP da aplicação (ex: autenticação, autorização).
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Anotação que habilita a configuração de segurança da web no Spring Security.
// Permite que você personalize regras de autenticação/autorização criando um bean do tipo SecurityFilterChain.
// Substitui a necessidade de estender WebSecurityConfigurerAdapter (deprecated nas versões mais novas).
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// Importa a interface SecurityFilterChain, que representa a cadeia de filtros do Spring Security para proteger requisições HTTP.
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // novo formato
                .authorizeHttpRequests(auth -> auth // novo formato com lambda
                        .anyRequest().permitAll());

        return http.build();
    }
}
