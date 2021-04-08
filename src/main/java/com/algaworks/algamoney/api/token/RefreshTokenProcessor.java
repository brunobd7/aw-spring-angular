package com.algaworks.algamoney.api.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//APLICANDO UMA INTERCEPETAcAO/PÓS PROCESSAMENTO AO MONTAR OS REPONSE BODY
// DE ACORDO COM O OBJETO GENERICO PASSADO (OAuth2AccessToken)
// NO PARAMETRO DA INTERFACE RESPONSEBODYADVICE<T>
@ControllerAdvice
public class RefreshTokenProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    /**
     * APLICANDO CONDICAO PARA INTERCEPETACAO SOMENTE QUANDO UM POST FOR EXECUTADO PARA RETORNAR UM ACCESS TOKEN
     * @return igual a TRUE
     * */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken oAuth2AccessToken, MethodParameter methodParameter,
                                             MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                             ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        //cast servlet server resquest to servlet request
        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        //CAST CORPO COM INFO DO TOKEN PARA REMOVER A INFORMACAO DO REFRESH TOKEN DO BODY
        DefaultOAuth2AccessToken tokenBody = (DefaultOAuth2AccessToken) oAuth2AccessToken;

        //BODY COM DADOS DO ACCESS TOKEN RETORNADO NO POST COM TODAS INFOS COMO TOKEN , REFRES TOKEN , JTI E ETC.
        String refreshToken = oAuth2AccessToken.getRefreshToken().getValue();

        //REMOVENDO INFORMACAO DO REFRESH TOKEN DO RESPONSE BODY DO OAUTH
        removeRefreshTokenBody(tokenBody);

        //GERA COOKIE E ADD AO RESPONSE AO CLIENT
        addRefreshTokenIntoCookie(request, response, refreshToken);

        //body
        return oAuth2AccessToken;
    }

    //REMOVENDO INFORMACAO DO REFRESH TOKEN DO RESPONSE BODY DO OAUTH
    private void removeRefreshTokenBody(DefaultOAuth2AccessToken tokenIntoBody) {
       tokenIntoBody.setRefreshToken(null);
    }

    private void addRefreshTokenIntoCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);//TODO MUDAR PARA TRUE QUANDO EM PRODUCAO HTTPS
        refreshTokenCookie.setPath(request.getContextPath()+"/oauth/token"); //PEGA CONTEXT DA REQUISICAO
        refreshTokenCookie.setMaxAge(259200); //QUANTO TEMPO PARA EXPIRACAO -> EM exemplo 30 dias
        response.addCookie(refreshTokenCookie); // add cookie no response

    }
}
