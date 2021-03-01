package com.algaworks.algamoney.api.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

//EXTENDS APPLICATION EVENT DO SPRING PARA CRIAR UM EVENTO DA APLICACAO
public class ResourceCreatedEvent extends ApplicationEvent {

    private  HttpServletResponse response;
    private Integer id;

    public ResourceCreatedEvent(Object source , HttpServletResponse response , Integer id) {
        super(source);
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Integer getId() {
        return id;
    }
}
