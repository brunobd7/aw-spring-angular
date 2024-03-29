package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

    /**CLASSE DE CONFIGURACAO CRIADA*/
    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @DeleteMapping("/revoke") /**REVOGANDO O REFRESH TOKEN REMOVENDO O VALOR DO COOKIE PARA "LOGOUT" */
    public void revoke(HttpServletRequest request , HttpServletResponse response){

        //SUBSTIUINDO O REFRESHTOKEN PARA NAO RENOVAR O ACCESS TOKEN E MANTER EM LOGOFF
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);//TODO EM PRODUCAO IMPLEMENTAR TRUE
        cookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps());
        cookie.setPath(request.getContextPath()+"/oauth/token");
        cookie.setMaxAge(0);/**EXPIRANDO/INVALIDANDO O COOKIE*/

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());

    }
}
