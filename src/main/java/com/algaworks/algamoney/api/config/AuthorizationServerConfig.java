package com.algaworks.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

//OATUH2 AUTHORIZATION SERVER TO COMUNICATION AND ENABLE ACCESS TO CLIENT INTO RESOURCE SERVER(MODELS, METHODS, FUNCTIONS)
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    //GERENCIA A AUTENTICACAO ACESSANDO OS DADOS PARA ISSO: USER , PASSWORD
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * CONFIGURA A AUTORIZACAO DO CLIENTE(PLATAFORMA, SISTEMA , MEIO DE ACESSO) QUE
     *  O USUÁRIO FINAL USA PARA ACESSAR A API:
     * @withCliente identificador do cliente utilizado pelo usuario final
     * @secret senha de acesso
     * @scopes limitacoes de acessos , operacoes e/ou autorizacoes em recursos dentro da api.Necessita de implement
     * @authorizationGrantTypes  tipo de fluxo(analisar especificacoes oauth2)
     * @accessTokenValiditySeconds tempo de validade do token passado em segundos
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("angular")
                .secret("@angul@r")
                .scopes("write","read")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(1800);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenStore(tokenStore()) //ARMAZENA O TOKEN GERADO PELO  AUTHORIZATION SERVER PARA OS DEVIDOS ACESSOS
                .authenticationManager(authenticationManager); // INTERFACE INJETADA VALIDA A AUTENTICACAO CONFORME PARAMETRIZADO NO RESOURCE SERVER
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore(); // SALVANDO O TOKE EM MEMORIA
    }
}
