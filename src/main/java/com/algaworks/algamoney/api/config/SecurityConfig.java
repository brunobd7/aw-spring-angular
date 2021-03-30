package com.algaworks.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //AUTENTICACAO EM MEMORIA PARA TESTE
        //ROLES PARA AUTORIZACAO
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ROLE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/categories/").permitAll() //PERMITE QUALQUER REQUEST NESSE RECURSO
                .anyRequest().authenticated() // QUALQUER OUTRA PRECISA DE AUTH
                .and()
            .httpBasic().and() // TIPO DE AUTENTICACAO COM HTTP BASIC
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //NAO MANTERA ESTADO , CRIARA SESSOES
            .and().csrf().disable();
    }
}
