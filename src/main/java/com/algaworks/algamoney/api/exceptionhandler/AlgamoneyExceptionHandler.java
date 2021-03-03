package com.algaworks.algamoney.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<ErroAux> trackedErros = Arrays.asList(new ErroAux(userMessage,devMessage));

        return handleExceptionInternal(ex, trackedErros, headers, HttpStatus.BAD_REQUEST, request);
    }

    //TRATA EXCECOES DE REQUESTS DE ARGUMENTOS/PARAMETROS NAO VALIDOS EX. VALOR NULL EM CAMPOS ANOTADOS COM @NOTNULL.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    //      DEFAULT
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);

        //BINDING RESULT RETURN ALL ERROS ENVOLVED INTO EXCEPTION
        List<ErroAux> erros = createErrosList(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    //DEFININDO CAPTURA DE EXCEPTION ANOTANDO COM EXCEPTIONHANDLER E PASSAR ARRAY COM CLASSES COMO PARAMETROS DA ANOTATION
    @ExceptionHandler({EmptyResultDataAccessException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND) //retorna not found para solicitacao de um recurso que nao existe,
//    caso nao queira retorno no body deixar somente anotacao e retorno do metodo void
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){

        //BUSCA MESSAGE PERSONLIZADA NO ARQUIVO messages.properties
        String userMessage = messageSource.getMessage("recurso.nao-encontrado",null, LocaleContextHolder.getLocale());
        String devMessage = ex.toString(); //exception direta , .getCause() nao necessaria
        List<ErroAux> trackedErros = Arrays.asList(new ErroAux(userMessage,devMessage));

        return handleExceptionInternal(ex, trackedErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    //MOUNT LIST OF ERROS ENVOLVED INTO EXCEPTIONS
    private List<ErroAux> createErrosList(BindingResult bindingResult){

        List<ErroAux> erros = new ArrayList<>();

        //ERROS CAUSED BY BAD FILLING IN FILEDS
        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            String userMessage = messageSource.getMessage(fieldError,LocaleContextHolder.getLocale());
            String devMessage = fieldError.toString();

            erros.add(new ErroAux(userMessage, devMessage));
        }

        return erros;
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
