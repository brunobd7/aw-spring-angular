package com.algaworks.algamoney.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** CLASSE CRIADA PARA CORRIGIR PROBLEMA DE INJECAO DA PROPRIEDADE  "AuthenticationManager" NO "AuthorizationServerConfig"
 * Com as atualizações do Spring Security, não há um Bean para AuthenticationManager que é fornecido por padrão pelo Spring,
 * para isso precisamos definir esse Bean por conta própria.
 * */
@Configuration
@EnableWebSecurity
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    /**Para isso, podemos sobrescrever o método authenticationManager() da classe WebSecurityConfigurerAdapter:*/
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**Bean do nosso passwordEncoder,
     * que será usado para fazer o decode da senha do usuário e da secret do cliente(web / mobile): */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance(); //PARA UTILIZACAO SEM ENCODER
    }
}
