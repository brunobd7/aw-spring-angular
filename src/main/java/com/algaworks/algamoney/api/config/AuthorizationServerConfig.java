package com.algaworks.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

//OATUH2 AUTHORIZATION SERVER TO COMUNICATION AND ENABLE ACCESS TO CLIENT INTO RESOURCE SERVER(MODELS, METHODS, FUNCTIONS)
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    //GERENCIA A AUTENTICACAO ACESSANDO OS DADOS PARA ISSO: USER , PASSWORD
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /**
         * CONFIGURA A AUTORIZACAO DO CLIENTE(PLATAFORMA, SISTEMA , MEIO DE ACESSO) QUE
         *  O USUÁRIO FINAL USA PARA ACESSAR A API:
         * @withCliente identificador do cliente utilizado pelo usuario final
         * @secret senha de acesso
         * @scopes limitacoes de acessos , operacoes e/ou autorizacoes em recursos dentro da api
        * */
        clients.inMemory()
                .withClient("angular")
                .secret("@angul@r")
                .scopes("write","read");
    }
}
