package com.algaworks.algamoney.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//CRIADA PARA CORRIGIR PROBLEMA DE INJECAO DA PROPRIEDADE  "AuthenticationManager" NO "AuthorizationServerConfig"
//QUE IMPEDIA O SPRING DE INICIAR
@Configuration
@EnableWebSecurity
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //TEMPORARIO SERA SUBSTITUIDO POIS ESTA DEPRECIADO
        return NoOpPasswordEncoder.getInstance();
    }
}
