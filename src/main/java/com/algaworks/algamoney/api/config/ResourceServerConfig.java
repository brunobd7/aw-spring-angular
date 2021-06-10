package com.algaworks.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

//SERVIDOR DE RECURSOS
@Configuration
@Profile("oauth2-security")
//@EnableWebSecurity // NAO NECESSARIO PARA CONFIG DE USERS NO BANCO NA VERSAO 2.5.1+ SPRING
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) //TRATAR ACESSOS / SEGURANÇA NOS METODOS (get , post ,delete mapping)
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


    /**SEGURANCA DOS METODOS COM OAUTH2*/
    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler(){
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
