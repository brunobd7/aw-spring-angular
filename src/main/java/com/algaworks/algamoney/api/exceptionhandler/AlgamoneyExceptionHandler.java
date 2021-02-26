package com.algaworks.algamoney.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//CONTROLLER QUE 'OBSERVA/ESCUTA' toda a aplicacao para tratamento das excecoes
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    //TRATAMENTOS DE EXCECOES

    @Autowired
    private MessageSource messageSource;

    //TRATA EXCECOES DE REQUESTS QUE NAO PODEM SER LIDAS EX.CAMPO DESCONHECIDO
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        //DEFAULT RETURN
//        return super.handleHttpMessageNotReadable(ex, headers, status, request);

        //ADD ARQUIVO PARA MENSAGENS PADROES E CHAMANDO PELO CODIGO DESIGNADO NO MESSAGE.PROPERTIES
        //LOCALE PODE SER CONFIGURADO PARA CADA LOCALIDADE EX.BR ,USA ,CHINA
        String userMessage = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
        String devMessage = ex.getCause().toString(); //CAUSA TECNICA DA EXCEPTION

        return handleExceptionInternal(ex, new ErroAux(userMessage,devMessage),headers,HttpStatus.BAD_REQUEST,request);
    }

    public static class ErroAux {

        private String userMessage;
        private String devMessage;

        public ErroAux(String userMessage, String devMessage) {
            this.userMessage = userMessage;
            this.devMessage = devMessage;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public String getDevMessage() {
            return devMessage;
        }
    }
}
