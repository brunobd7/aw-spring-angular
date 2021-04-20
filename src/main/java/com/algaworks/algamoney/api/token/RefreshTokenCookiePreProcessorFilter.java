package com.algaworks.algamoney.api.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        //Verifica se request e direcionada a oauth/token
        // e se o gran_type  é referente ao refresh_token
        //e se possui o refresh token no cookie
        if("/oauth/token".equalsIgnoreCase(req.getRequestURI())
                && "refresh_token".equals(req.getParameter("grant_type"))
                && req.getCookies() !=null){

            /*for (Cookie cookie : req.getCookies()) {

                if(cookie.getName().equals("refreshToken")){

                    String refreshToken = cookie.getValue();

                    req = new MyServeletRequestWrapper(req , refreshToken);
                }
            }*/

            //JAVA 8 IMPLEMENTACAO
            String refreshToken = Stream.of(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            // SOBRESCREVENDO REQ E ADD O REFRESH TOKEN AOS PARAMETROS DA REQUISCAO
            req = new MyServeletRequestWrapper(req, refreshToken);

        }

        //CONCLUINDO A CADEIA DO FILTRO
        filterChain.doFilter(req,servletResponse);
    }

    //CLASSE PARA ADICIONAR O REFRESH TOKEN NO MAP DE PARAMETROS DE RETORNO
    static class MyServeletRequestWrapper extends HttpServletRequestWrapper{

        private String refreshToken;

        public MyServeletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        /**
         * SOBRESCREVEMOS O MAP de parametros da requisicao passando os enviados e adicionamos o refreshToken
        */
        @Override
        public Map<String, String[]> getParameterMap() {
//            ALTERANDO O RETORNO DO MAPA DE PARAMETROS DA REQUISICAO PARA APLICACAO
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());

            //ADD O REFRESH TOKEN QUE PASSAMOS NO CONSTRUTOR PARA DENTRO DO "NOVO" MAP PARAMETER
            map.put("refresh_token", new String[] {this.refreshToken} );
            map.setLocked(true); //trava map

            return map;
        }
    }

}
