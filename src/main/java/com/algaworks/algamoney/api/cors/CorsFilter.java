package com.algaworks.algamoney.api.cors;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component //DEFINEI COMPONENTE COMO DO SPRING
@Order(Ordered.HIGHEST_PRECEDENCE) // ORDEM DE PRIORIDADE MAIS ALTA DE EXECUCAO LOGO DE INICIO
public class CorsFilter implements Filter {

    /**CLASSE DE CONFIGURACAO CRIADA*/
    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

//    private String allowedOrigins = "http://localhost:8000"; //TODO TRATAR POSTERIOMENTE PARA DIFERENTES AMBIENTES

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        //APLICA ORIGENS PERMITIDAS E CRENDENCIAS
        response.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOrigemPermitida());
        response.setHeader("Access-Control-Allow-Credentials","true");

        //PERMITI PRE-FLIGHT REQUEST DE DETERMINADA ORIGEM PARA PERMITIR O CORS
        if(request.getMethod().equals("OPTIONS")
                && request.getHeader("Origin").equals(algamoneyApiProperty.getOrigemPermitida())){

            /**HEADERS PARA PERMISSAO DO CORS
             * METODOS
             * HEADERS
             * TEMPO ATE PROXIMA REQUISICAO PARA VERIFICAR AUTORIZACAO CORS
             * */

            response.setHeader("Access-Control-Allow-Methods","POST, GET, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers","Authorization, Content-Type, Accept");
            response.setHeader("Access-Control-Max-Age","3600"); //1HR


            response.setStatus(HttpServletResponse.SC_OK);

        }else{
            filterChain.doFilter(request,response);
        }

    }

}
