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
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
                .secret("@ngul@r")
                .scopes("write","read") // SCOPE OBRIGATORIO
                .authorizedGrantTypes("password","refresh_token") //PASSWORD FLOW DOCUMENTACAO OAUTH2
                .accessTokenValiditySeconds(30) // % por 60 = 30 minutos - validade do token
                .refreshTokenValiditySeconds(3600*24); //VALIDADE DO REFRESH TOKEN
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenStore(tokenStore()) //ARMAZENA O TOKEN GERADO PELO  AUTHORIZATION SERVER PARA OS DEVIDOS ACESSOS
                .accessTokenConverter(accessTokenConverter()) //GERANDO ACCESS TOKEN CONVERTER
                .reuseRefreshTokens(false) // DESABILITA O REUSO DO REFRESH TOKEN SEMPRE GERANDO UM NOVO
                .authenticationManager(authenticationManager); // INTERFACE INJETADA VALIDA A AUTENTICACAO CONFORME PARAMETRIZADO NO RESOURCE SERVER
    }

    /**GERANDO ACCESS TOKEN CONVERTER
     *@Bean e public para ser visualizado e acessivel no escopo do projeto para sempre que necessario
     * usar um token converter estar disponivel
     * @setSingKey chave/senha que valida o TOKEN o na sessao SIGNATURE no decode do JWT*/
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("algaworks"); //CHAVE/SENHA que valida o token na sessao SIGNATURE
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**SALVANDO O TOKEN EM MEMORIA*/
//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }

}
