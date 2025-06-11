package com.abner.financialtracker.config;

// Importa a anotação @Bean, que marca um método como produtor de um bean gerenciado pelo Spring.
import org.springframework.context.annotation.Bean;

// Importa a anotação @Configuration, que indica que a classe é uma fonte de definições de beans (configuração do Spring).
import org.springframework.context.annotation.Configuration;

// Importa a classe HttpSecurity, usada para configurar as regras de segurança HTTP da aplicação (ex: autenticação, autorização).
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Importa a interface SecurityFilterChain, que representa a cadeia de filtros do Spring Security para proteger requisições HTTP.
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
