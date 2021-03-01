package com.algaworks.algamoney.api.event.listener;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

//LISTENER DO EVENTO CRIADO NO DIRETORIO ACIMA
//IMPLEMENTA A INTERFACE E PASSA O EVENTO POR PARAMETRO
//ANATACAO PARA DEFINIR A CLASSE COMO UM COMPONENTE DO SPRING
@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {

        HttpServletResponse response = resourceCreatedEvent.getResponse();
        Integer id = resourceCreatedEvent.getId();

        addLocationHeaderIntoHttpResponse(response, id);
    }

    private void addLocationHeaderIntoHttpResponse(HttpServletResponse response, Integer id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
