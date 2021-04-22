package com.algaworks.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//SERVIDOR DE RECURSOS
@Configuration
//@EnableWebSecurity // NAO NECESSARIO PARA CONFIG DE USERS NO BANCO NA VERSAO 2.5.1+ SPRING
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // NAO NECESSARIO PARA CONFIG DE USERS NO BANCO NA VERSAO 2.5.1+ SPRING
    //@Autowired
    //private UserDetailsService userDatailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/categories/").permitAll() //PERMITE QUALQUER REQUEST NESSE RECURSO
                .anyRequest().authenticated() // QUALQUER OUTRA PRECISA DE AUTH
                .and()
//            .httpBasic().and() // TIPO DE AUTENTICACAO COM HTTP BASIC - REMOVIDO APÓS IMPLEMENTACAO OAUTH2
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //NAO MANTERA ESTADO , CRIARA SESSOES
            .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.stateless(true); //NAO MANTERA ESTADO , CRIARA SESSOES
    }
}
