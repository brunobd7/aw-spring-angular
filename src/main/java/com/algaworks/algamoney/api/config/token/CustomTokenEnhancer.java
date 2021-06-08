package com.algaworks.algamoney.api.config.token;

import com.algaworks.algamoney.api.security.SystemUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**CRIADA PARA ADICIONAR E MAPEAR INFO ADICIONAIS AO ACESS TOKEN*/
public class CustomTokenEnhancer implements TokenEnhancer {

    /**INCREMENTADNDO DADOS NO TOKEN JWT COM ORIGEM NO BANCO VIA JPA MODEL USERS*/
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        SystemUser systemUser = (SystemUser) oAuth2Authentication.getPrincipal();

        Map<String,Object> addInfo = new HashMap<>();
        addInfo.put("nome", systemUser.getUsers().getName()); /**ADICIONANDO NOME AO MAP **/

        /**ADD INFO ADICIONAL AO ACCESS TOKEN*/
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(addInfo);
        return oAuth2AccessToken;
    }
}
